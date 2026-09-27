package com.airline.mapper;

import com.airline.model.AircraftType;
import com.airline.playload.request.AircraftTypeRequest;
import com.airline.playload.response.AircraftTypeResponse;

public class AircraftTypeMapper {

    public static AircraftType toEntity(
            AircraftTypeRequest request
    ){
        if (request == null){
            return null;
        }

        AircraftType aircraftType = AircraftType
                .builder()
                .manufacturer(request.getManufacturer())
                .model(request.getModel())
                .icaoTypeCode(request.getIcaoTypeCode())
                .iataTypeCode(request.getIataTypeCode())
                .engineCount(request.getEngineCount())
                .passengerCapacity(request.getPassengerCapacity())
                .cargoCapacityKg(request.getCargoCapacityKg())
                .maxRangeKm(request.getMaxRangeKm())
                .cruiseSpeedKmh(request.getCruiseSpeedKmh())
                .maxTakeoffWeightKg(request.getMaxTakeoffWeightKg())
                .status(request.getStatus())
                .build();


        return aircraftType;
    }

    public static AircraftTypeResponse toResponse(AircraftType aircraftType){
        if (aircraftType == null){
            return  null;
        }

        return AircraftTypeResponse
                .builder()
                .id(aircraftType.getId())
                .manufacturer(aircraftType.getManufacturer())
                .iataTypeCode(aircraftType.getIataTypeCode())
                .icaoTypeCode(aircraftType.getIcaoTypeCode())
                .engineCount(aircraftType.getEngineCount())
                .engineType(aircraftType.getEngineType())
                .passengerCapacity(aircraftType.getPassengerCapacity())
                .cargoCapacityKg(aircraftType.getCargoCapacityKg())
                .maxRangeKm(aircraftType.getMaxRangeKm())
                .cruiseSpeedKmh(aircraftType.getCruiseSpeedKmh())
                .maxTakeoffWeightKg(aircraftType.getMaxTakeoffWeightKg())
                .status(aircraftType.getStatus())
                .createdAt(aircraftType.getCreatedAt())
                .updatedAt(aircraftType.getUpdatedAt())
                .build();
    }

    public static AircraftType updateEntity(
            AircraftType aircraftType,
            AircraftTypeRequest request) {

        if (aircraftType == null || request == null) {
            return aircraftType;
        }

        if (request.getManufacturer() != null) {
            aircraftType.setManufacturer(request.getManufacturer());
        }

        if (request.getModel() != null) {
            aircraftType.setModel(request.getModel());
        }

        if (request.getIcaoTypeCode() != null) {
            aircraftType.setIcaoTypeCode(request.getIcaoTypeCode());
        }

        if (request.getIataTypeCode() != null) {
            aircraftType.setIataTypeCode(request.getIataTypeCode());
        }

        if (request.getEngineCount() != null) {
            aircraftType.setEngineCount(request.getEngineCount());
        }

        if (request.getEngineType() != null) {
            aircraftType.setEngineType(request.getEngineType());
        }

        if (request.getPassengerCapacity() != null) {
            aircraftType.setPassengerCapacity(request.getPassengerCapacity());
        }

        if (request.getCargoCapacityKg() != null) {
            aircraftType.setCargoCapacityKg(request.getCargoCapacityKg());
        }

        if (request.getMaxRangeKm() != null) {
            aircraftType.setMaxRangeKm(request.getMaxRangeKm());
        }

        if (request.getCruiseSpeedKmh() != null) {
            aircraftType.setCruiseSpeedKmh(request.getCruiseSpeedKmh());
        }

        if (request.getMaxTakeoffWeightKg() != null) {
            aircraftType.setMaxTakeoffWeightKg(request.getMaxTakeoffWeightKg());
        }

        if (request.getStatus() != null) {
            aircraftType.setStatus(request.getStatus());
        }

        return aircraftType;
    }
}
