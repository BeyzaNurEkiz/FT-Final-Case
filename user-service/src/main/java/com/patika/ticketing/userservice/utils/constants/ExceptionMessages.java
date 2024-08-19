package com.patika.ticketing.userservice.utils.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "User not found in database";
    public static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found in database";
    public static final String ROLE_NOT_FOUND = "User role not found in database";
    public static final String ACCOUNT_NOT_FOUND = "Account not found in database";
    public static final String USERNAME_ALREADY_TAKEN = "User already exists with this username";
    public static final String EMAIL_ALREADY_TAKEN = "User already exists with this email";
    public static final String BAD_REQUEST = "The request could not be fulfilled";

}
