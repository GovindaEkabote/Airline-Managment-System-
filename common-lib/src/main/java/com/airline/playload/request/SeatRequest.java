    package com.airline.playload.request;

    import jakarta.validation.constraints.*;
    import lombok.*;

    import java.math.BigDecimal;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class SeatRequest {

        @NotBlank
        @Size(max = 5)
        private String seatNumber;

        @Min(1)
        private int seatRow;

        @NotNull
        private Character columnLetter;

        @NotBlank
        private String seatType;

        @PositiveOrZero
        private BigDecimal basePrice;

        @PositiveOrZero
        private BigDecimal premiumSurcharge;

        private Boolean isAvailable;

        private Boolean isBlocked;

        private Boolean isActive;

        private Boolean isEmergencyExit;

        private Boolean isExtraLegroom;

        private Boolean isPowerOutlet;

        private Boolean isTvScreen;

        private Boolean isExtraWidth;

        @Positive
        private Integer seatPitch;

        @Positive
        private Integer seatWidth;

        @NotNull
        private Long seatMapId;

        @NotNull
        private Long cabinClassId;
    }
