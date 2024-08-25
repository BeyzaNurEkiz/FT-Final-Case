package com.patika.ticketing.trip_service.client;

import com.patika.ticketing.trip_service.entity.dto.responce.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:8080") // URL'yi kendi yapınıza göre ayarlayın
public interface UserServiceClient {

    @GetMapping("/api/auth/user-info")
    UserInfoResponse getUserInfo(@RequestHeader("Authorization") String token);
}
