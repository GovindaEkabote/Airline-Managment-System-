package com.airline.service.impl;

import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.CityMapper;
import com.airline.model.City;
import com.airline.playload.request.CityRequest;
import com.airline.playload.response.CityResponse;
import com.airline.repository.CityRepository;
import com.airline.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) {

        String cityCode = request.getCityCode()
                .trim()
                .toUpperCase();

        if (cityRepository.existsByCityCode(cityCode)) {
            throw new DuplicateResourceException(
                    "City",
                    "cityCode",
                    cityCode
            );
        }

        City city = CityMapper.toEntity(request);

        City savedCity = cityRepository.save(city);

        return CityMapper.toResonse(savedCity);
    }
    @Override
    public List<CityResponse> bulkCreateCity(
            List<CityRequest> requests) {

        if (requests == null || requests.isEmpty()) {
            return List.of();
        }

        // Check duplicate city codes inside the request
        Set<String> cityCodes = new HashSet<>();

        for (CityRequest request : requests) {

            String cityCode = request.getCityCode()
                    .trim()
                    .toUpperCase();

            if (!cityCodes.add(cityCode)) {
                throw new DuplicateResourceException(
                        "City",
                        "cityCode",
                        cityCode
                );
            }
        }

        // Check duplicate city codes already present in DB
        for (String cityCode : cityCodes) {

            if (cityRepository.existsByCityCode(cityCode)) {
                throw new DuplicateResourceException(
                        "City",
                        "cityCode",
                        cityCode
                );
            }
        }

        List<City> cities = requests.stream()
                .map(CityMapper::toEntity)
                .toList();

        List<City> savedCities =
                cityRepository.saveAll(cities);

        return savedCities.stream()
                .map(CityMapper::toResonse)
                .toList();
    }
    @Override
    public CityResponse updateCity(
            Long id,
            CityRequest request) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City",
                                "id",
                                id
                        )
                );

        if (request.getName() != null) {
            city.setName(
                    request.getName().trim()
            );
        }

        if (request.getCityCode() != null) {

            String cityCode = request.getCityCode()
                    .trim()
                    .toUpperCase();

            if (!cityCode.equals(city.getCityCode())
                    && cityRepository.existsByCityCodeAndIdNot(
                    cityCode,
                    id
            )) {

                throw new DuplicateResourceException(
                        "City",
                        "cityCode",
                        cityCode
                );
            }

            city.setCityCode(cityCode);
        }
        if (request.getCountry() != null) {
            city.setCountry(
                    request.getCountry().trim()
            );
        }

        if (request.getCountryCode() != null) {
            city.setCountryCode(
                    request.getCountryCode()
                            .trim()
                            .toUpperCase()
            );
        }

        if (request.getRegionCode() != null) {
            city.setRegionCode(
                    request.getRegionCode()
                            .trim()
                            .toUpperCase()
            );
        }

        if (request.getTimeZoneId() != null) {
            city.setTimeZoneId(
                    request.getTimeZoneId().trim()
            );
        }

        if (request.getActive() != null) {
            city.setActive(
                    request.getActive()
            );
        }

        City updatedCity =
                cityRepository.save(city);

        return CityMapper.toResonse(updatedCity);
    }
    @Override
    public CityResponse getCityId(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City",
                                "id",
                                id
                        )
                );

        return CityMapper.toResonse(city);
    }

    @Override
    public Page<CityResponse> getAllCities(
            Pageable pageable) {

        return cityRepository
                .findAll(pageable)
                .map(CityMapper::toResonse);
    }

    @Override
    public Page<CityResponse> searchCities(
            String name,
            Pageable pageable) {

        return cityRepository
                .searchByKeyword(name, pageable)
                .map(CityMapper::toResonse);
    }


    @Override
    public Page<CityResponse> getCitiesByCountryCode(
            String countryCode,
            Pageable pageable) {

        return cityRepository
                .findByCountryCodeIgnoreCase(
                        countryCode,
                        pageable
                )
                .map(CityMapper::toResonse);
    }


    @Override
    public Page<CityResponse> getCitiesByActive(
            Boolean active,
            Pageable pageable) {

        return cityRepository
                .findByActive(active, pageable)
                .map(CityMapper::toResonse);
    }


    @Override
    public boolean cityExists(String cityCode) {

        return cityRepository.existsByCityCode(
                cityCode.trim().toUpperCase()
        );
    }


    @Override
    public void deleteCity(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City",
                                "id",
                                id
                        )
                );

        cityRepository.delete(city);
    }

    @Override
    public void activateCity(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City",
                                "id",
                                id
                        )
                );

        city.setActive(true);

        cityRepository.save(city);
    }

    @Override
    public void deactivateCity(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City",
                                "id",
                                id
                        )
                );

        city.setActive(false);

        cityRepository.save(city);
    }
}
