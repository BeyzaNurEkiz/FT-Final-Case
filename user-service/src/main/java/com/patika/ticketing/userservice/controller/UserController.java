package com.patika.ticketing.userservice.controller;

import com.patika.ticketing.userservice.entity.dto.request.LoginRequest;
import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.request.SignUpIndividualRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import com.patika.ticketing.userservice.entity.dto.response.JwtResponse;
import com.patika.ticketing.userservice.service.AuthService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registerCorporate")
    public ResponseEntity<DataResult<CorporateResponse>> registerCorporate(@RequestBody SignUpCorporateRequest signUpRequest) {
        DataResult<CorporateResponse> result = authService.registerCorporate(signUpRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/registerIndividual")
    public ResponseEntity<DataResult<IndividualResponse>> registerIndividual(@RequestBody SignUpIndividualRequest signUpRequest) {
        DataResult<IndividualResponse> result = authService.registerIndividula(signUpRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest).getData();
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Result> logout() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.logout());
    }

    @GetMapping("/test")
    public String hello() {
        return "Hello World!";
    }
}
