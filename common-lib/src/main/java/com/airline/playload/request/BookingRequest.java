
package com.airline.playload.request;

import com.airline.embeddable.ContactInfo;
import com.airline.enums.CabinClassType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Flight instance ID is required")
    private Long flightInstanceId;

    @NotNull(message = "Fare ID is required")
    @Positive(message = "Fare ID must be positive")
    private Long fareId;

    @NotNull(message = "Cabin class is required")
    private CabinClassType cabinClassType;

    @NotEmpty(message = "At least one passenger is required")
    @Valid
    private List<PassengerRequest> passengers;


//    private List<Long> seatInstanceIds;

    private List<String> seatNumbers;


    private Set<Long> ancillaryIds = Set.of();


    private Set<Long> mealIds = Set.of();


    private Boolean flexibleTicket = false;

    @NotNull(message = "Booking contact information is required")
    @Valid
    private ContactInfo contactInfo;
}
