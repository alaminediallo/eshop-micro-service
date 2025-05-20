package com.lamine.isi.gatewayservice.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> ignorePaths = List.of(
            "api/auth/register",
            "api/auth/token",
            "api/auth/validate"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> ignorePaths
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
