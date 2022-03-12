package com.security.account.config;

import com.security.account.exceptions.ExceptionResponse;
import com.security.account.exceptions.UserExistException;
import com.security.account.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
@PropertySource("classpath:messages.properties")
public class GlobalExceptionHandler {

    public static final int STR_FIELD_NAME = 0;

    @Autowired
    private Environment env;

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    ExceptionResponse handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        return new ExceptionResponse(
                Objects.requireNonNull(exception.getBindingResult()
                                .getFieldError())
                        .getDefaultMessage()
        );
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler({UserExistException.class})
    public @ResponseBody
    ExceptionResponse handlerBusinessRules(UserExistException exception) {
        log.info(exception.getMessage());
        return new ExceptionResponse(env.getProperty(exception.getMessage()));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    public @ResponseBody
    ExceptionResponse handlerBusinessRules(UserNotFoundException exception) {
        log.info(exception.getMessage());
        return new ExceptionResponse(env.getProperty(exception.getMessage()));
    }
}
