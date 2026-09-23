package com.airline.service.impl;

import com.airline.exception.DuplicateResourceException;
import com.airline.mapper.CityMapper;
import com.airline.model.City;
import com.airline.playload.request.CityRequest;
import com.airline.playload.response.CityResponse;
import com.airline.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    private City city;
    private CityRequest cityRequest;
    private CityResponse cityResponse;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("Mumbai");
        city.setCityCode("BOM");           // fixed to match assertions
        city.setCountry("India");
        city.setCountryCode("IN");
        city.setRegionCode("MH");
        city.setTimeZoneId("Asia/Kolkata");
        city.setActive(true);

        cityRequest = new CityRequest();
        cityRequest.setName("Mumbai");
        cityRequest.setCityCode("BOM");    // fixed
        cityRequest.setCountry("India");
        cityRequest.setCountryCode("IN");
        cityRequest.setRegionCode("MH");
        cityRequest.setTimeZoneId("Asia/Kolkata");
        cityRequest.setActive(true);

        cityResponse = CityResponse.builder()
                .id(1L)
                .name("Mumbai")
                .cityCode("BOM")           // fixed
                .country("India")
                .countryCode("IN")
                .regionCode("MH")
                .timeZoneId("Asia/Kolkata")
                .active(true)
                .build();
    }

    @Test
    void createCity_shouldCreateCitySuccessfully() {
        when(cityRepository.existsByCityCode("BOM")).thenReturn(false);
        when(cityRepository.save(any(City.class))).thenReturn(city);

        try (MockedStatic<CityMapper> mapper = mockStatic(CityMapper.class)) {
            mapper.when(() -> CityMapper.toEntity(cityRequest)).thenReturn(city);
            mapper.when(() -> CityMapper.toResonse(city)).thenReturn(cityResponse);

            CityResponse result = cityService.createCity(cityRequest);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getCityCode()).isEqualTo("BOM");

            verify(cityRepository).existsByCityCode("BOM");
            verify(cityRepository).save(city);
        }
    }

    @Test
    void createCity_shouldThrowExceptionWhenCityCodeExists() {
        // Arrange
        when(cityRepository.existsByCityCode("BOM")).thenReturn(true);
        // Act + Assert
        assertThatThrownBy(() -> cityService.createCity(cityRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("City already exists")
                .hasMessageContaining("BOM");

        // Verify: existence checked, but save never reached
        verify(cityRepository).existsByCityCode("BOM");
        verify(cityRepository, never()).save(any(City.class));
    }
}