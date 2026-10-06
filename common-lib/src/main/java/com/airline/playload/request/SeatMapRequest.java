package com.airline.playload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatMapRequest {

    @NotBlank(message = "name is required")
    @Size(max = 100)
    private String name;

    @Min(value = 1, message = "totalRows must be at least 1")
    private int totalRows;

    @Min(value = 0, message = "leftSeatsPerRow cannot be negative")
    private int leftSeatsPerRow;

    @Min(value = 0, message = "rightSeatsPerRow cannot be negative")
    private int rightSeatsPerRow;

    // Only used on create. A seat map cannot be moved to another cabin class later.
    @NotNull(message = "cabinClassId is required")
    private Long cabinClassId;

}
