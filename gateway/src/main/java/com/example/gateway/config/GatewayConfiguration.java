package com.example.gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    private final String HOSTNAME_ONE = "http://localhost:8082/";
    private final String HOSTNAME_TWO = "http://localhost:8083/";

    @Bean
    public RouteLocator routing(RouteLocatorBuilder routeBuilder){
        return routeBuilder.routes()
                .route(r -> r.path("/product/**")
                        .uri(HOSTNAME_ONE))
                .route(r -> r.path("/order/**")
                        .uri(HOSTNAME_ONE))
                .route(r-> r.path("/merchant/**")
                        .uri(HOSTNAME_ONE))
                .route(r -> r.path("/user/**")
                        .uri(HOSTNAME_ONE))

                .route(r -> r.path("/notif/**")
                        .uri(HOSTNAME_TWO))

                .build();
    }
}
