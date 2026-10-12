package com.airline.playload.request;

import com.airline.enums.MealType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealRequest {

    @NotNull
    private Long ancillaryId;

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotNull
    private Long airlineId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    private MealType mealType;

    private Boolean available;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal price;

    private boolean vegetarian;

    private boolean vegan;

    private boolean glutenFree;

    private boolean lactoseFree;

    private boolean containsNuts;

    @Size(max = 100)
    private String cuisine;

    private Boolean requiresAdvanceBooking;

    @Min(0)
    private Integer advanceBookingHours;

    @Size(max = 500)
    private String imageUrl;

    @Min(0)
    private Integer displayOrder;

    private Boolean active;
}