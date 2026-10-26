
package com.airline.config;

import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfig {

    @Bean
    public RouterFunction<ServerResponse> authRoute() {
        return GatewayRouterFunctions.route("auth-route")
                .route(RequestPredicates.path("/api/v1/auth/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("USER-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> adminLocationServiceRoute() {
        return GatewayRouterFunctions.route("admin-location-route")
                .route(RequestPredicates.POST("/api/cities/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.POST("/api/airports/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("LOCATION-SERVICE"))
                .build();
    }

    @Bean
    @Order(1)
    public RouterFunction<ServerResponse> adminAirlineServiceRoute() {
        return GatewayRouterFunctions.route("admin-airline-route")
                .route(RequestPredicates.POST("/api/airlines/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("AIRLINE-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes() {
        return GatewayRouterFunctions.route("user-service-route")
                .route(RequestPredicates.path("/api/v1/users/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("USER-SERVICE"))
                .build();
    }

    @Bean
    @Order(2)
    public RouterFunction<ServerResponse> airlineCoreServiceRoutes() {
        return GatewayRouterFunctions.route("airline-core-routes")
                .route(RequestPredicates.path("/api/v1/airlines/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/aircraft/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("AIRLINE-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> seatServiceRoutes() {
        return GatewayRouterFunctions.route("seat-service-routes")
                .route(RequestPredicates.path("/api/v1/cabins/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/seat-map/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/seats/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/seat/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight/instance/cabin/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("AIRLINE-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> flightOpsServiceRoutes() {
        return GatewayRouterFunctions.route("flight-ops-routes")
                .route(RequestPredicates.path("/api/v1/flights/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flights-instance/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight-schedules/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("FLIGHT-OPS-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> pricingServiceRoutes() {
        return GatewayRouterFunctions.route("pricing-service-routes")
                .route(RequestPredicates.path("/api/v1/fares/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/fares/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/baggage-policies/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("PRICING-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ancillaryServiceRoutes() {
        return GatewayRouterFunctions.route("ancillary-service-routes")
                .route(RequestPredicates.path("/api/v1/meal/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/ancillaries/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight/meal/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight/cabin/ancillary/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/insurance/coverages/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("ANCILLARY-SERVICE"))
                .build();
    }

    @Bean
    @Order(2)
    public RouterFunction<ServerResponse> locationServiceRoutes() {
        return GatewayRouterFunctions.route("location-service-routes")
                .route(RequestPredicates.path("/api/v1/cities/**"),
                        HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/airports/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("LOCATION-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoutes() {
        return GatewayRouterFunctions.route("booking-service-routes")
                .route(RequestPredicates.path("/api/v1/booking/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("BOOKING-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceRoutes() {
        return GatewayRouterFunctions.route("payment-service-routes")
                .route(RequestPredicates.path("/api/v1/payment/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("PAYMENT-SERVICE"))
                .build();
    }
}
