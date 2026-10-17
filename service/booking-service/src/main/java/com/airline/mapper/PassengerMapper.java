package com.airline.mapper;

import com.airline.model.Passenger;
import com.airline.playload.request.PassengerRequest;
import com.airline.playload.response.PassengerResponse;

public class PassengerMapper {

    private void PassengerMapper(){

    }

    public static Passenger toEntity(PassengerRequest request){

        if (request == null){
            return null;
        }
        return Passenger.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .nationality(request.getNationality())
                .build();
    }

    public static PassengerResponse toResponse(Passenger passenger){

        if (passenger == null){
            return null;
        }

        return PassengerResponse.builder()
                .id(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .email(passenger.getEmail())
                .phone(passenger.getPhone())
                .dateOfBirth(passenger.getDateOfBirth())
                .gender(passenger.getGender())
                .nationality(passenger.getNationality())
                .primaryUserId(passenger.getPrimaryUserId())
                .isActive(passenger.getIsActive())
                .age(passenger.getAge())
                .isAdult(passenger.isAdult())
                .fullName(passenger.getFullName())
                .createdAt(passenger.getCreatedAt())
                .updatedAt(passenger.getUpdatedAt())
                .build();
    }

    public static PassengerResponse toUpdate(){
        return null;
    }
}
