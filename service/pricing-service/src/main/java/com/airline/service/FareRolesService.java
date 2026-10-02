package com.airline.service;


import com.airline.playload.request.FareRolesRequest;
import com.airline.playload.response.FareRuleResponse;

import java.util.List;


public interface FareRolesService {

    FareRuleResponse createFareRoles(FareRolesRequest request);
    FareRuleResponse getFareRolesById(Long id);
    FareRuleResponse updateFareRoles(Long id, FareRolesRequest request);
    void deleteFareRoles(Long id);

    FareRuleResponse getFareRolesByFareId(Long fareId);
    List<FareRuleResponse> getFareRolesByAirlineId(Long airlineId);


}
