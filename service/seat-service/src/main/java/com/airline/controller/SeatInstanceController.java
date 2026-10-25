package com.airline.controller;


import com.airline.service.SeatInstanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seat")
@RequiredArgsConstructor
@Tag(
        name = "Seat Instances",
        description = "APIs for Calculate Seat Price"
)
public class SeatInstanceController {

    private final SeatInstanceService seatInstanceService;

    @Operation(
            summary = "Generate seats fare",
            description = "Calculate Seat Price"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Calculate Seat Price"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seat map not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Seats already exist or seat map configuration is invalid"
            )
    })
    @PostMapping("/price")
    public BigDecimal calculatePrice(
            @RequestBody List<Long> seatInstanceIds
            ){
            return seatInstanceService.calculateSeatPrice(seatInstanceIds);
    }
}
