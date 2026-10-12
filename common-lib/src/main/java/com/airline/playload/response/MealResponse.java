package com.airline.playload.response;

import com.airline.enums.MealType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealResponse {

    private Long id;

    private AncillaryResponse ancillary;

    private String code;

    private AirlineResponse airlineResponse;

    private String name;

    private String description;

    private MealType mealType;

    private Boolean available;

    private BigDecimal price;

    private boolean vegetarian;

    private boolean vegan;

    private boolean glutenFree;

    private boolean lactoseFree;

    private boolean containsNuts;

    private String cuisine;

    private Boolean requiresAdvanceBooking;

    private Integer advanceBookingHours;

    private String imageUrl;

    private Integer displayOrder;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;
}