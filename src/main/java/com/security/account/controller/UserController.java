package com.security.account.controller;

import com.security.account.controller.request.user.UserRequest;
import com.security.account.controller.response.user.UserResponse;
import com.security.account.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final IUserService service;

    @ResponseStatus(CREATED)
    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return UserResponse.fromUser(service.create(request));
    }
}
