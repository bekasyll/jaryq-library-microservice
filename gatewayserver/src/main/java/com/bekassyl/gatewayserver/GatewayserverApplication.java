package com.bekassyl.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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
                                        )
                                        .addResponseHeader(
                                                "X-Response-Time",
                                                LocalDateTime.now().toString()
                                        )
                                        .circuitBreaker(config -> config.setName("booksCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport"))
                                        .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                                .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://BOOKS")
                )
                .route(p -> p
                        .path("/jaryqlibrary/members/**")
                        .filters(f -> f.rewritePath(
                                                "/jaryqlibrary/members/(?<segment>.*)",
                                                "/members/${segment}"
                                        ).addResponseHeader(
                                                "X-Response-Time",
                                                LocalDateTime.now().toString()
                                        )
                                        .circuitBreaker(config -> config.setName("membersCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport"))
                                        .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                                .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://MEMBERS")
                )
                .route(p -> p
                        .path("/jaryqlibrary/loans/**")
                        .filters(f -> f.rewritePath(
                                                "/jaryqlibrary/loans/(?<segment>.*)",
                                                "/loans/${segment}"
                                        ).addResponseHeader(
                                                "X-Response-Time",
                                                LocalDateTime.now().toString()
                                        )
                                        .circuitBreaker(config -> config.setName("loansCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport"))
                                        .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                                .setKeyResolver(userKeyResolver()))
                        )
                        .uri("lb://LOANS")
                )
                .build();
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 1, 1);
    }

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders()
                .getFirst("user")).defaultIfEmpty("anonymous");
    }
}
