package com.patika.ticketing.userservice.utils.result;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Copyright (c) 2024
 * All rights reserved.
 *
 * @author Emre Ünaldı
 * @since 12.07.2024
 */
@Getter
public class Result {

    private Boolean success;
    private String message;
    private final LocalDateTime responseDateTime;

    public Result() {
        this.responseDateTime = LocalDateTime.now();
    }

    public Result(Boolean success) {
        this.success = success;
        this.responseDateTime = LocalDateTime.now();
    }

    public Result(String message) {
        this.message = message;
        this.responseDateTime = LocalDateTime.now();
    }

    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.responseDateTime = LocalDateTime.now();
    }

}
