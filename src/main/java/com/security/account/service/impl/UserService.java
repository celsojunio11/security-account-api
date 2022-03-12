package com.security.account.service.impl;

import com.security.account.controller.request.user.UserRequest;
import com.security.account.entity.User;
import com.security.account.exceptions.UserExistException;
import com.security.account.repository.UserRepository;
import com.security.account.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.security.account.config.AccountMessage.VALIDATION_EMAIL_ALREADY_EXISTS;

@AllArgsConstructor
@Service
@Slf4j
public class UserService implements IUserService {
    private final UserRepository repository;

    public User create(UserRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            log.error("[USER] - Not create, email user = {} exists", request.getEmail());

            throw new UserExistException(VALIDATION_EMAIL_ALREADY_EXISTS);
        }

        return User.create(request, repository);
    }
}
