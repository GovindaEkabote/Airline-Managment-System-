package com.airline.service;

import com.airline.playload.request.CityRequest;
import com.airline.playload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {

    CityResponse createCity(CityRequest request);

    List<CityResponse> bulkCreateCity(List<CityRequest> requests);

    CityResponse updateCity(Long id, CityRequest request) throws Exception;

    CityResponse getCityId(Long id)throws Exception;

    Page<CityResponse> getAllCities(Pageable pageable) throws Exception;

    Page<CityResponse> searchCities(String name, Pageable pageable) throws Exception;

    Page<CityResponse> getCitiesByCountryCode(
            String countryCode,
            Pageable pageable
    ) ;

    Page<CityResponse> getCitiesByActive(
            Boolean active,
            Pageable pageable
    );

    boolean cityExists(String cityCode);


    void deleteCity(Long id);

    void activateCity(Long id);

    void deactivateCity(Long id);
}
