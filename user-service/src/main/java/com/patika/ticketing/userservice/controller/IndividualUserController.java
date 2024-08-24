package com.patika.ticketing.userservice.controller;

import com.patika.ticketing.userservice.entity.dto.request.SignUpIndividualRequest;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import com.patika.ticketing.userservice.service.IndividualUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/individual")
@PreAuthorize("hasRole('ROLE_USER')")
public class IndividualUserController {

    private final IndividualUserService individualUserService;

    public IndividualUserController(IndividualUserService individualUserService) {
        this.individualUserService = individualUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<DataResult<IndividualResponse>> register(@RequestBody SignUpIndividualRequest signUpRequest) {
        DataResult<IndividualResponse> result = individualUserService.register(signUpRequest);
        return ResponseEntity.ok(result);
    }

}
