package com.patika.ticketing.userservice.controller;

import com.patika.ticketing.userservice.entity.dto.request.UserUpdateRequest;
import com.patika.ticketing.userservice.entity.dto.response.IndividualResponse;
import com.patika.ticketing.userservice.service.IndividualUserService;
import com.patika.ticketing.userservice.utils.result.DataResult;
import com.patika.ticketing.userservice.utils.result.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/individualUser")
public class IndividualUserController {

    private final IndividualUserService individualUserService;

    public IndividualUserController(IndividualUserService individualUserService) {
        this.individualUserService = individualUserService;
    }

    @PutMapping
    public ResponseEntity<DataResult<IndividualResponse>> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(individualUserService.update(userUpdateRequest));
    }

    @GetMapping
    public ResponseEntity<DataResult<List<IndividualResponse>>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(individualUserService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DataResult<IndividualResponse>> findById(@PathVariable("userId") Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(individualUserService.findById(userId));
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<DataResult<IndividualResponse>> findByUsername(@PathVariable("username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(individualUserService.findByUsername(username));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Result> deleteById(@PathVariable("userId") Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(individualUserService.deleteById(userId));
    }
}
