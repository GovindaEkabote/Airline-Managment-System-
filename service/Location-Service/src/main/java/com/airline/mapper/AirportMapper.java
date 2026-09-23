package com.airline.mapper;

import com.airline.model.Airport;
import com.airline.model.City;
import com.airline.playload.request.AirportRequest;
import com.airline.playload.response.AirportResponse;
import org.springframework.data.domain.Page;

import java.time.ZoneId;
import java.util.TimeZone;

public class AirportMapper {

    public static Airport toEntity(AirportRequest request, City city){
        if (request == null){
            return null;
        }

        return Airport.builder()
                .iataCode(request.getIataCode())
                .icaoCode(request.getIcaoCode())
                .name(request.getName())
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .timeZone(request.getTimeZone())
                .active(request.getActive() != null ? request.getActive() : true)
                .city(city)
                .build();
    }

    public static AirportResponse toResonse(Airport airport){
        if (airport == null){
            return null;
        }
        return AirportResponse
                .builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .icaoCode(airport.getIcaoCode())
                .name(airport.getName())
                .address(airport.getAddress())
                .geoCode(airport.getGeoCode())
                .timeZone(airport.getTimeZone() != null
                        ? TimeZone.getTimeZone(airport.getTimeZone())
                        : null)
                .active(airport.isActive())
                .detailedName(airport.getDetailedName())
                .city(CityMapper.toResonse(airport.getCity()))
                .build();
    }

    public static Airport updateEntity(Airport airport, AirportRequest airportRequest){
        if (airportRequest.getIataCode() != null){
            airport.setIataCode(airportRequest.getIataCode());
        }
        if (airportRequest.getIcaoCode() != null){
            airport.setIcaoCode(airportRequest.getIcaoCode());
        }
        if (airportRequest.getName() != null){
            airport.setName(airportRequest.getName());
        }
        if (airportRequest.getAddress() != null){
            airport.setAddress(airportRequest.getAddress());
        }
        if (airportRequest.getGeoCode() != null){
            airport.setGeoCode(airportRequest.getGeoCode());
        }
        if (airportRequest.getTimeZone() != null){
            airport.setTimeZone(airportRequest.getTimeZone());
        }
        if (airportRequest.getActive() != null){
            airport.setActive(airportRequest.getActive());
        }
            return airport;
    }
}
