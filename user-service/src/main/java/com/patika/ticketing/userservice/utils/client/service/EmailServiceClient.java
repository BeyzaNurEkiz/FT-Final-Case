package com.patika.ticketing.userservice.utils.client.service;

import com.patika.ticketing.userservice.utils.client.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service", url = "http://localhost:8082")
public interface EmailServiceClient {

    @PostMapping("/api/v1/email/send")
    void sendEmail(@RequestBody EmailRequest emailRequest);
}
