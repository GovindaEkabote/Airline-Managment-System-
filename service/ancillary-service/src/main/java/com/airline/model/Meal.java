package com.airline.model;


import com.airline.enums.MealType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "meal",
        indexes = {
                @Index(
                        name = "idx_meal_ancillary",
                        columnList = "ancillary_id"
                ),
                @Index(
                        name = "idx_meal_type",
                        columnList = "meal_type"
                ),
                @Index(
                        name = "idx_meal_active",
                        columnList = "active"
                )
        }
)
@EntityListeners(AuditingEntityListener.class)
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name =  "ancillary_id", nullable = false)
    private Ancillary ancillary;

    @NotBlank
    @Size(max = 50)
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @NotNull
    @Column(name = "airline_id", nullable = false)
    private Long airlineId;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", nullable = false, length = 40)
    private MealType mealType;

    @NotNull
    @Builder.Default
    @Column(name = "available", nullable = false)
    private Boolean available = true;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "is_vegetarian", nullable = false)
    private boolean vegetarian = false;

    @Column(name = "is_vegan", nullable = false)
    private boolean vegan = false;

    @Column(name = "is_gluten_free", nullable = false)
    private boolean glutenFree = false;

    @Column(name = "is_lactose_free", nullable = false)
    private boolean lactoseFree = false;

    @Column(name = "contains_nuts", nullable = false)
    private boolean containsNuts = false;

    @Size(max = 100)
    @Column(name = "cuisine", length = 100)
    private String cuisine;

    @NotNull
    @Builder.Default
    @Column(name = "requires_advance_booking", nullable = false)
    private Boolean requiresAdvanceBooking = false;

    @Min(0)
    @Column(name = "advance_booking_hours")
    private Integer advanceBookingHours;


    @Size(max = 500)
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @NotNull
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
