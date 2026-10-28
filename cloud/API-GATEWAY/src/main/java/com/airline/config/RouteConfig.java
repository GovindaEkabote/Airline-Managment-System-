
package com.airline.config;

import com.airline.enums.Roles;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfig {

    private final JwtUtil jwtUtil;

    public RouteConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


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
                .before(this::jwtAuthFilter)
                .before(request -> requireRoles(
                        request,
                        Roles.SYSTEM_ADMIN.toString()
                        ))
                .build();
    }

    @Bean
    @Order(1)
    public RouterFunction<ServerResponse> adminAirlineServiceRoute() {
        return GatewayRouterFunctions.route("admin-airline-route")
                .route(RequestPredicates.POST("/api/airlines/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("AIRLINE-SERVICE"))
                .before(this::jwtAuthFilter)
                .before(request -> requireRoles(
                        request,
                        Roles.AIRLINE_ADMIN.name()
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes() {
        return GatewayRouterFunctions.route("user-service-route")
                .route(RequestPredicates.path("/api/v1/users/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("USER-SERVICE"))
                .before(this::jwtAuthFilter)
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
                .before(this::jwtAuthFilter)
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
                .before(this::jwtAuthFilter)
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
                .before(this::jwtAuthFilter)
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
                .before(this::jwtAuthFilter)
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
                .before(this::jwtAuthFilter)
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
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoutes() {
        return GatewayRouterFunctions.route("booking-service-routes")
                .route(RequestPredicates.path("/api/v1/booking/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("BOOKING-SERVICE"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceRoutes() {
        return GatewayRouterFunctions.route("payment-service-routes")
                .route(RequestPredicates.path("/api/v1/payment/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("PAYMENT-SERVICE"))
                .before(this::jwtAuthFilter)
                .build();
    }

    private ServerRequest jwtAuthFilter(ServerRequest request){
        String authHeader = request
                .headers()
                .firstHeader(JwtConstant.JWT_HEADER);

        // 1. Check Auth Header Exist
        if (authHeader==null || !authHeader.startsWith(JwtConstant.TOKEN_PREFIX)){
            throw  new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Missing or Invalid Authorization token.."
                    );
        }
        // step 2 : remove prefix from token

        String token = authHeader.substring(JwtConstant.TOKEN_PREFIX.length());

        if (!jwtUtil.isTokenValid(token)){
            throw  new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid or Expired JWT Token"
            );
        }

        // step 3 :
        String email = jwtUtil.extractEmail(token);
        String authorities = jwtUtil.extractAuthorities(token);
        Long userId = jwtUtil.extractUserId(token);

        return ServerRequest.from(request)
                .header("X-User-Id", String.valueOf(userId))
                .header("X-User-Email", email)
                .header("X-User-Roles", authorities)
                .build();
    }

    private ServerRequest requireRoles(ServerRequest request, String role){
        String roles = request.headers().firstHeader("X-User-Roles");

        if (roles == null || !roles.contains(role)){
            throw  new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Access Denied, required role '" + role + "'"
            );
        }
        return request;
    }
}
