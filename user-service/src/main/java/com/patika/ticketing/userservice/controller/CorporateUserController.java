package com.patika.ticketing.userservice.controller;

import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.service.CorporateUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/corporate")
@PreAuthorize("hasRole('ROLE_USER')")
public class CorporateUserController {

    private final CorporateUserService corporateUserService;

    public CorporateUserController(CorporateUserService corporateUserService) {
        this.corporateUserService = corporateUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<DataResult<CorporateResponse>> register(@RequestBody SignUpCorporateRequest signUpRequest) {
        DataResult<CorporateResponse> result = corporateUserService.register(signUpRequest);
        return ResponseEntity.ok(result);
    }
}
