package com.patika.ticketing.trip_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean validateToken(String token) {
        String url = "http://localhost:8080/api/auth/validate-token"; // user-service'in URL'si
        ResponseEntity<String> response = restTemplate.postForEntity(url, token, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }
}
