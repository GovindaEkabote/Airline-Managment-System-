package com.airline.service;

import com.airline.model.Passenger;
import com.airline.playload.request.PassengerRequest;

public interface PassengerService {

    Passenger createPassgener(PassengerRequest request, Long userId);

}
