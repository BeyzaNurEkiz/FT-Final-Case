package com.patika.ticketing.userservice.controller;

import com.patika.ticketing.userservice.entity.dto.request.LoginRequest;
import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.entity.dto.response.JwtResponse;
import com.patika.ticketing.userservice.service.AuthService;
import com.patika.ticketing.userservice.service.CorporateUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService, CorporateUserService corporateUserService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<DataResult<CorporateResponse>> register(@RequestBody SignUpCorporateRequest signUpRequest) {
        DataResult<CorporateResponse> result = authService.register(signUpRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest).getData();
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // JWT ile logout işlemi, genellikle istemci tarafında yapılır.
        return ResponseEntity.ok("Logout successful!");
    }

    @GetMapping("/test")
    public String hello() {
        return "Hello World!";
    }
}
