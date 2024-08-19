package com.patika.ticketing.userservice.utils.controllerAdvice.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
    private int httpStatusCode;
    private String httpMethod;
    private String errorType;
    private String requestPath;
}
