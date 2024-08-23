package com.patika.ticketing.userservice.entity.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

}

