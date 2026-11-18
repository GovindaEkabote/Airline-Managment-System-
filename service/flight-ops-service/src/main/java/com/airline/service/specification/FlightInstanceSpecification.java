package com.airline.service.specification;

import com.airline.enums.FlightStatus;
import com.airline.model.FlightInstance;
import com.airline.playload.request.FlightSearchRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FlightInstanceSpecification {

    private static final Set<FlightStatus> EXCLUDED_STATUS=
            Set.of(
                    FlightStatus.CANCELLED,
                    FlightStatus.COMPLETED,
                    FlightStatus.DIVERTED
            );

    private FlightInstanceSpecification(){}

    public static Specification<FlightInstance> buildSearchSpecification
            (FlightSearchRequest request){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isTrue(root.get("isActive")));
            predicates.add(root.get("status").in(EXCLUDED_STATUS).not());
            predicates.add(cb.greaterThan(
                    root.get("departureDateTime"),
                    LocalDateTime.now()
            ));
         predicates.add(cb.equal(root.get("departureAirportId"), request.getDepartureAirportId()));
         predicates.add(cb.equal(root.get("arrivalAirportId"), request.getArrivalAirportId()));

            LocalDateTime startOfDate=request.getDepartureDateTime().atStartOfDay();
            LocalDateTime endOfDay= request.getDepartureDateTime().atTime(LocalTime.MAX);
            predicates.add(cb.between(root.get("departureDateTime"), startOfDate, endOfDay));

         predicates.add(cb.greaterThanOrEqualTo(root.get("availableSeats"), request.getPassengers()));
         if (request.getAirlines() != null
             && !request.getAirlines().isEmpty()){
             predicates.add(root.get("airlineId").in(request.getAirlines()));
         }

         if (isFilterableTimeRange(request.getDepartureTimeRange())){
             applyTimeRangePredicate(
                     predicates,
                     root,
                     cb,
                     "departureDateTime",
                     request.getDepartureTimeRange()
             );
         }

            if (isFilterableTimeRange(request.getArrivalTimeRange())){
                applyTimeRangePredicate(
                        predicates,
                        root,
                        cb,
                        "arrivalDateTime",
                        request.getArrivalTimeRange()
                );
            }

            if (request.getMaxDuration() != null){
                Expression<Integer> durationMinutes =
                        cb.function(
                          "TIMESTAMPDIFF",
                          Integer.class,
                          cb.literal("MINUTE"),
                          root.get("departureDateTime"),
                          root.get("arrivalDateTime")
                        );

                predicates.add(cb.lessThanOrEqualTo(durationMinutes, request.getMaxDuration()));
            }
            query.distinct(true);
            return  cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean isFilterableTimeRange(String range){
        return range != null
                && !range.isBlank() && !range.equalsIgnoreCase("any");
    }

    private static void applyTimeRangePredicate(
            List<Predicate> predicates,
            Root<FlightInstance> root,
            CriteriaBuilder cb,
            String dateTimeFiled,
            String timeRange
    ){
        Expression<Integer> hours =cb.function("HOUR",Integer.class,root.get(dateTimeFiled));
        switch (timeRange.toLowerCase()){
            case "morning" -> predicates.add(cb.between(hours, 6 , 11));
            case "afternoon" -> predicates.add(cb.between(hours, 12, 17));
            case "evening" -> predicates.add(cb.between(hours, 18, 20));
            case "night" -> predicates.add(cb.or(
                    cb.greaterThanOrEqualTo(hours, 21),
                    cb.lessThanOrEqualTo(hours,5))
            );
            default -> {}
        }
    }
}
