package com.bekassyl.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator jaryqLibraryRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/jaryqlibrary/books/**")
                        .filters(f -> f.rewritePath(
                                "/jaryqlibrary/books/(?<segment>.*)",
                                "/books/${segment}"
                        ))
                        .uri("lb://BOOKS")
                )
                .route(p -> p
                        .path("/jaryqlibrary/members/**")
                        .filters(f -> f.rewritePath(
                                "/jaryqlibrary/members/(?<segment>.*)",
                                "/members/${segment}"
                        ))
                        .uri("lb://MEMBERS")
                )
                .route(p -> p
                        .path("/jaryqlibrary/loans/**")
                        .filters(f -> f.rewritePath(
                                "/jaryqlibrary/loans/(?<segment>.*)",
                                "/loans/${segment}"
                        ))
                        .uri("lb://LOANS")
                )
                .build();
    }
}
