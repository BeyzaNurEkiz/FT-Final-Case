package com.patika.ticketing.userservice.controller;

import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.CorporateResponse;
import com.patika.ticketing.userservice.service.CorporateUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/corporateUser")
public class CorporateUserController {

    private final CorporateUserService corporateUserService;

    public CorporateUserController(CorporateUserService corporateUserService) {
        this.corporateUserService = corporateUserService;
    }

    @PutMapping
    public ResponseEntity<DataResult<CorporateResponse>> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(corporateUserService.update(userUpdateRequest));
    }

    @GetMapping
    public ResponseEntity<DataResult<List<CorporateResponse>>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(corporateUserService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DataResult<CorporateResponse>> findById(@PathVariable("userId") Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(corporateUserService.findById(userId));
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<DataResult<CorporateResponse>> findByUsername(@PathVariable("username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(corporateUserService.findByUsername(username));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Result> deleteById(@PathVariable("userId") Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(corporateUserService.deleteById(userId));
    }
}
