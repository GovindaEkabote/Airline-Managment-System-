package com.airline.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Set;
/**
 * Request body for POST /api/v1/seat-maps/{id}/generate-seats
 *
 * The SeatMap already knows totalRows, leftSeatsPerRow and rightSeatsPerRow.
 * This request only carries what the SeatMap does NOT know:
 * where numbering starts, which rows are special, and default price/features.
 *
 * Service rules (validate in SeatMapServiceImpl, not here):
 *  - leftColumnLetters.length()  must equal seatMap.leftSeatsPerRow  (if provided)
 *  - rightColumnLetters.length() must equal seatMap.rightSeatsPerRow (if provided)
 *  - if letters are omitted, defaults are used: left = A,B,C...; right continues after left
 *  - rows are numbered from startRow, skipping skipRows, until totalRows rows exist
 *  - emergencyExitRows / extraLegroomRows must be rows that actually get generated
 *  - seat type (WINDOW / MIDDLE / AISLE) is derived automatically from position
 *  - if seats already exist, fail with 409 unless overwriteExisting = true
 */
public record SeatGenerationRequest(
        @Min(value = 1, message = "startRow must be at least 1")
        Integer startRow,

        Set<Integer>skipRows,                  // e.g. {13} - airlines often skip row 13

        @Pattern(regexp = "^[A-Z]*$", message = "leftColumnLetters must be uppercase letters only")
        String leftColumnLetters,               // e.g. "ABC"

        @Pattern(regexp = "^[A-Z]*$", message = "rightColumnLetters must be uppercase letters only")
        String rightColumnLetters,              // e.g. "DEF"

        @PositiveOrZero(message = "basePrice cannot be negative")
        BigDecimal basePrice,                   // default base price for every generated seat

        @PositiveOrZero(message = "premiumSurcharge cannot be negative")
        BigDecimal premiumSurcharge,            // default surcharge for every generated seat

        Set<Integer> emergencyExitRows,         // seats in these rows -> emergencyExit = true
        Set<Integer> extraLegroomRows,          // seats in these rows -> extraLegroom = true

        Boolean powerOutlet,                    // applied to all generated seats
        Boolean tvScreen,                       // applied to all generated seats

        @Positive Integer seatPitch,            // inches
        @Positive Integer seatWidth,            // inches

        Boolean overwriteExisting
) {

    public SeatGenerationRequest{
        if (startRow == null) startRow = 1;
        if (skipRows == null) skipRows = Set.of();
        if (emergencyExitRows == null) emergencyExitRows = Set.of();
        if (extraLegroomRows == null) extraLegroomRows = Set.of();
        if (powerOutlet == null) powerOutlet = false;
        if (tvScreen == null) tvScreen = false;
        if (overwriteExisting == null) overwriteExisting = false;

        // Defensive copies keep the record immutable.
        skipRows = Set.copyOf(skipRows);
        emergencyExitRows = Set.copyOf(emergencyExitRows);
        extraLegroomRows = Set.copyOf(extraLegroomRows);

    }
}
