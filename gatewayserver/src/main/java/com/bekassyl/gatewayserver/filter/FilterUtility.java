package com.bekassyl.gatewayserver.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {
    public static final String CORRELATION_ID = "jaryqlibrary-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);

        if (requestHeaderList != null) {
            return requestHeaderList.stream().findFirst().get();
        } else {
            return null;
        }
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

    private ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate()
                .request(
                        exchange.getRequest()
                                .mutate()
                                .header(name, value)
                                .build()
                ).build();
    }
}