package com.lamine.isi.gatewayservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface IUserClient {
    @GetMapping("/api/auth/validate")
    boolean validateToken(@RequestParam("token") String token);
}