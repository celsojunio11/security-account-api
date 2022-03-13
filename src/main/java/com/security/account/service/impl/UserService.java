package com.security.account.service.impl;

import com.security.account.controller.request.user.UserRequest;
import com.security.account.entity.User;
import com.security.account.exceptions.UserExistException;
import com.security.account.exceptions.UserNotFoundException;
import com.security.account.repository.UserRepository;
import com.security.account.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.security.account.config.AccountMessageConfig.USER_NOT_FOUND;
import static com.security.account.config.AccountMessageConfig.VALIDATION_EMAIL_ALREADY_EXISTS;

@AllArgsConstructor
@Service
@Slf4j
public class UserService implements IUserService {
    private final UserRepository repository;

    public User create(UserRequest request) {
        if (repository.existsByUsername(request.getEmail())) {
            log.error("[USER] - Not create, email user = {} exists", request.getEmail());

            throw new UserExistException(VALIDATION_EMAIL_ALREADY_EXISTS);
        }

        return User.create(request, repository);
    }

    public User getUsername(String username) {
        Optional<User> user = repository.findByUsername(username);

        if (user.isPresent() && user.get().getUsername().equals(username)) {
            return user.get();
        } else {
            log.error("[USER] - username = {} not found", username);

            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }
}
