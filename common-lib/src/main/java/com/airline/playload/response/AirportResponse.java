package com.airline.playload.response;

import com.airline.embeddable.Address;
import com.airline.embeddable.GeoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TimeZone;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {

    private Long id;
    private String iataCode;
    private String icaoCode;
    private String name;
    private String detailedName;
    private Address address;
    private GeoCode geoCode;
    private TimeZone timeZone;
    private CityResponse city;
    private Boolean active;
}
