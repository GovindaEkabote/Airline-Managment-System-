package com.airline.mapper;

import com.airline.enums.AircraftStatus;
import com.airline.model.Aircraft;
import com.airline.playload.request.AircraftRequest;
import com.airline.playload.response.AircraftResponse;

import java.util.Date;

public class AircraftMapper {

    public static Aircraft toEntity(AircraftRequest aircraftRequest) {

        if (aircraftRequest == null){
            return null;
        }
        Aircraft aircraft = Aircraft
                .builder()
                .registrationNumber(aircraftRequest.getRegistrationNumber())
                .serialNumber(aircraftRequest.getSerialNumber())
                .icao24(aircraftRequest.getIcao24())
                .aircraftTypeId(aircraftRequest.getAircraftTypeId())
                .airlineId(aircraftRequest.getAirlineId())
                .manufactureYear(aircraftRequest.getManufactureYear())
                .deliveryDate(aircraftRequest.getDeliveryDate())
                .status(aircraftRequest.getStatus())
                .totalSeats(aircraftRequest.getTotalSeats())
                .economySeats(aircraftRequest.getEconomySeats())
                .premiumEconomySeats(aircraftRequest.getPremiumEconomySeats())
                .businessSeats(aircraftRequest.getBusinessSeats())
                .firstClassSeats(aircraftRequest.getFirstClassSeats())

                .cruisingSpeedKmh(aircraftRequest.getCruisingSpeedKmh())
                .rangeKm(aircraftRequest.getRangeKm())
                .maxAltitudeFt(aircraftRequest.getMaxAltitudeFt())

                .nextMaintenanceDate(aircraftRequest.getNextMaintenanceDate())
                .currentAirportId(aircraftRequest.getCurrentAirportId())
                .available(aircraftRequest.getAvailable())
                .createdAt(new Date().toInstant())
                .build();
        return aircraft;
    }

    public static Aircraft updateEntity(
            Aircraft aircraft,
            AircraftRequest aircraftRequest
    ) {
        if (aircraft == null || aircraftRequest == null) {
            return aircraft;
        }

        if (aircraftRequest.getRegistrationNumber() != null) {
            aircraft.setRegistrationNumber(
                    aircraftRequest.getRegistrationNumber()
            );
        }

        if (aircraftRequest.getSerialNumber() != null) {
            aircraft.setSerialNumber(
                    aircraftRequest.getSerialNumber()
            );
        }

        if (aircraftRequest.getIcao24() != null) {
            aircraft.setIcao24(
                    aircraftRequest.getIcao24()
            );
        }

        if (aircraftRequest.getAircraftTypeId() != null) {
            aircraft.setAircraftTypeId(
                    aircraftRequest.getAircraftTypeId()
            );
        }

        if (aircraftRequest.getAirlineId() != null) {
            aircraft.setAirlineId(
                    aircraftRequest.getAirlineId()
            );
        }

        if (aircraftRequest.getManufactureYear() != null) {
            aircraft.setManufactureYear(
                    aircraftRequest.getManufactureYear()
            );
        }

        if (aircraftRequest.getDeliveryDate() != null) {
            aircraft.setDeliveryDate(
                    aircraftRequest.getDeliveryDate()
            );
        }

        if (aircraftRequest.getStatus() != null) {
            aircraft.setStatus(
                    aircraftRequest.getStatus()
            );
        }

        if (aircraftRequest.getAvailable() != null) {
            aircraft.setAvailable(
                    aircraftRequest.getAvailable()
            );
        }

        return aircraft;
    }


    public static AircraftResponse toResponse(Aircraft aircraft) {
        if (aircraft == null) {
            return null;
        }

        return AircraftResponse.builder()
                .id(aircraft.getId())
                .registrationNumber(aircraft.getRegistrationNumber())
                .serialNumber(aircraft.getSerialNumber())
                .icao24(aircraft.getIcao24())
                .aircraftTypeId(aircraft.getAircraftTypeId())
                .airlineId(aircraft.getAirlineId())
                .manufactureYear(aircraft.getManufactureYear())
                .deliveryDate(aircraft.getDeliveryDate())
                .status(aircraft.getStatus())
                .available(aircraft.getAvailable())
                .updatedById(aircraft.getUpdatedById())
                .totalSeats(aircraft.getTotalSeats())
                .economySeats(aircraft.getEconomySeats())
                .premiumEconomySeats(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .rangeKm(aircraft.getRangeKm())
                .maxAltitudeFt(aircraft.getMaxAltitudeFt())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .currentAirportId(aircraft.getCurrentAirportId())
                .operational(aircraft.getOperational())
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
                .build();
    }

}
