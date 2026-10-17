package com.airline.service.impl;

import com.airline.mapper.PassengerMapper;
import com.airline.model.Passenger;
import com.airline.playload.request.PassengerRequest;
import com.airline.repository.PassengerRepository;
import com.airline.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;;

    @Override
    public Passenger createPassgener(PassengerRequest request, Long userId) {

        Passenger passenger = PassengerMapper.toEntity(request);
        passenger.setPrimaryUserId(userId);
        Passenger saved = passengerRepository.save(passenger);
        return saved;
    }

}
