package com.patika.ticketing.userservice.controller;

import com.patika.ticketing.userservice.entity.dto.request.SignUpCorporateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.service.CorporateUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/corporateUsers")
public class CorporateUserController {

    private final CorporateUserService corporateUserService;

    @Autowired
    public CorporateUserController(CorporateUserService corporateUserService) {
        this.corporateUserService = corporateUserService;
    }

    @PostMapping("/register")
    @Operation(
            description = "Register new user",
            summary = "Register a new user",
            requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User Infos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = SignUpCorporateRequest.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "New User to Register",
                                            summary = "New Register",
                                            description = "Complete the request with all available fields to register a new user",
                                            value = "{\n"
                                                    + "  \"firstName\": \"BeyzaNur\",\n"
                                                    + "  \"lastName\": \"Ekiz\",\n"
                                                    + "  \"username\": \"beyzanur\",\n"
                                                    + "  \"email\": \"beyzanur@gmail.com\",\n"
                                                    + "  \"password\": \"beyza123.\",\n"
                                                    + "  \"phoneNumber\": \"+5078711190\",\n"
                                                    + "  \"roles\": [\"user\", \"admin\"]\n"
                                                    + "}"
                                    )
                            }
                    )
            )
    )
    public ResponseEntity<DataResult<CorporateResponse>> register(@Valid @RequestBody SignUpCorporateRequest signUpRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(corporateUserService.register(signUpRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<String> handleTest() {
        return ResponseEntity.ok("Hello World");
    }
}
