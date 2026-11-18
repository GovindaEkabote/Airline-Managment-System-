package com.airline.service.impl;

import com.airline.config.feignclient.AirlineClient;
import com.airline.config.feignclient.LocationClient;
import com.airline.config.feignclient.PricingClient;
import com.airline.config.feignclient.SeatClient;
import com.airline.enums.CabinClassType;
import com.airline.mapper.FlightInstanceMapper;
import com.airline.model.FlightInstance;
import com.airline.playload.request.FlightSearchRequest;
import com.airline.playload.response.*;
import com.airline.repository.FlightInstanceRepository;
import com.airline.repository.FlightRepository;
import com.airline.service.FlightSearchService;
import com.airline.service.specification.FlightInstanceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final  PricingClient pricingClient;
    private final SeatClient seatClient;
    private final AirlineClient airlineClient;
    private final LocationClient locationClient;
    private final FlightRepository flightRepository;

    @Override
    public Page<FlightInstanceResponse> searchFlight(
            FlightSearchRequest request,
            Pageable pageable
    ) {
        Pageable sortedPagable = applySort(
                pageable,
                request.getSortBy(),
                request.getSortOrder()
                );
        Specification<FlightInstance> specification =
                FlightInstanceSpecification.buildSearchSpecification(request);
        Page<FlightInstance> dbPage=flightInstanceRepository.findAll(
                specification,
                sortedPagable
        );
        if (dbPage.isEmpty()){
            return Page.empty(sortedPagable);
        }
        List<FlightInstance> instances = new ArrayList<>(dbPage.getContent());
        Map<Long, FareResponse> fareResponseMap=Collections.emptyMap();
        if (request.getCabinClassType() != null){
            final boolean hasPriceFilter=
                    request.getMinPrice() != null && request.getMaxPrice() != null;

            Map<Long, FareResponse> mergedFareMap = new HashMap<>();
            List<FlightInstance> filtered=new ArrayList<>();
            for (FlightInstance flightInstance: instances){
                CabinClassResponse cabinClassResponse =
                        seatClient.getCabinClassByAircraftIdAndName(
                                request.getCabinClassType(),
                                flightInstance.getAirlineId());

                Long cabinClassId = cabinClassResponse.getId();

                if (cabinClassId == null){
                    continue;
                }

                FareResponse fareResponse= pricingClient.getLowestFareForFlightAndCabinClass(
                        flightInstance.getFlight().getId(),
                        cabinClassId
                );
                if (fareResponse == null){
                    continue;
                }
                if (hasPriceFilter){
                    BigDecimal price = fareResponse.getTotalPrice();
                    if (price==null) continue;
                    if (price.compareTo(request.getMinPrice()) < 0){
                        continue;
                    }
                    if (price.compareTo(request.getMaxPrice()) > 0) {
                        continue;
                    }
                    mergedFareMap.put(flightInstance.getFlight().getId(), fareResponse);
                    filtered.add(flightInstance);
                }
                fareResponseMap=mergedFareMap;
                instances=filtered;

                if (instances.isEmpty()){
                    return Page.empty(sortedPagable);
                }

            }
        }
        List<FlightInstanceResponse> flightInstanceResponses =
                enrichWithExternalData(instances,fareResponseMap);
        return new PageImpl<>(flightInstanceResponses, sortedPagable,dbPage.getTotalElements());
    }

    private Long resolveCabinClassId(CabinClassType cabinClassType, Long aircraftId){

        return null;
    }

    private List<FlightInstanceResponse> enrichWithExternalData(List<FlightInstance> instances, Map<Long, FareResponse> fareResponseMap){
            Map<Long, AirlineResponse> airlineCashe =
                    new HashMap<>();
            Map<Long, AirportResponse > airportCashe =
                    new HashMap<>();
            Map<Long, AircraftResponse> aircraftCache=
                    new HashMap<>();

            List<FlightInstanceResponse> result=
                    new ArrayList<>(instances.size());

            for (FlightInstance fi: instances){
                AircraftResponse aircraftResponse = aircraftCache.computeIfAbsent(
                        fi.getFlight().getAircraftId(),
                        airlineClient::getAircraftById
                );
                AirlineResponse airlineResponse = airlineCashe.computeIfAbsent(
                        fi.getAirlineId(), airlineClient::getAirlineById
                );
                AirportResponse depAirportResponse = airportCashe.computeIfAbsent(
                        fi.getDepartureAirportId(), locationClient::getAirport
                );
                AirportResponse arrAirportResponse = airportCashe.computeIfAbsent(
                        fi.getArrivalAirportId(), locationClient::getAirport
                );

                FlightInstanceResponse flightInstanceResponse
                        = FlightInstanceMapper.toResponse2(
                                fi,aircraftResponse,airlineResponse,depAirportResponse,arrAirportResponse
                );


                flightInstanceResponse.setFareResponse(fareResponseMap.get(fi.getFlight().getId()));
                result.add(flightInstanceResponse);
            }
            return result;
    }


    private Pageable applySort(
            Pageable pageable,
            String sortBy,
            String sortOrder
    ){
        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder) ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = (sortBy == null || sortBy.isBlank())
                ? Sort.by(direction, "departureDateTime")
                : switch (sortBy.toLowerCase()){
            case "arrival" -> Sort.by(direction, "arrivalDateTime");
            case "duration" -> JpaSort.unsafe(direction, "TIMESTAMPDIFF(MINUTE, departureDateTime, arrivalDateTime)");
            default -> Sort.by(direction, "departureDateTime");
        };
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
