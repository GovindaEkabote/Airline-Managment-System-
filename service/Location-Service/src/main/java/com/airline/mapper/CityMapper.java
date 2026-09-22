package com.airline.mapper;

import com.airline.model.City;
import com.airline.playload.request.CityRequest;
import com.airline.playload.response.CityResponse;

public class CityMapper {

    public static City toEntity(CityRequest cityRequest){
        if (cityRequest == null){
            return  null;
        }
        return City.builder()
                .name(cityRequest.getName())
                .cityCode(cityRequest.getCityCode())
                .country(cityRequest.getCountry())
                .countryCode(cityRequest.getCountryCode())
                .regionCode(cityRequest.getRegionCode())
                .timeZoneId(cityRequest.getTimeZoneId())
                .active(cityRequest.getActive())
                .build();
    }

    public static CityResponse toResonse(City city){
        if (city == null){
            return  null;
        }
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .country(city.getCountry())
                .countryCode(city.getCountryCode())
                .regionCode(city.getRegionCode())
                .timeZoneId(city.getTimeZoneId())
                .active(city.isActive())
                .build();
    }

    public static City updateEntity(City city, CityRequest cityRequest){

        if (cityRequest.getName() != null){
            city.setName(cityRequest.getName());
        }
        if (cityRequest.getCityCode() != null){
            city.setCityCode(cityRequest.getCityCode());
        }
        if (cityRequest.getCountry() != null){
            city.setCountry(cityRequest.getCountry());
        }
        if (cityRequest.getCountryCode() != null){
            city.setCountryCode(cityRequest.getCountryCode());
        }
        if (cityRequest.getRegionCode() != null){
            city.setRegionCode(cityRequest.getRegionCode());
        }
        return city;
    }
}
