package com.security.account.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;
}
