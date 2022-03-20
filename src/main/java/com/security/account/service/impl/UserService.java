package com.security.account.service.impl;

import com.security.account.config.security.JwtTokenUtil;
import com.security.account.controller.request.user.ChangePassawordRequest;
import com.security.account.controller.request.user.UserRequest;
import com.security.account.entity.User;
import com.security.account.exceptions.PasswordEqualsException;
import com.security.account.exceptions.PasswordIncorrectException;
import com.security.account.exceptions.UserExistException;
import com.security.account.exceptions.UserNotFoundException;
import com.security.account.repository.UserRepository;
import com.security.account.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.security.account.config.AccountMessageConfig.*;

@AllArgsConstructor
@Service
@Slf4j
public class UserService implements IUserService {
    private final UserRepository repository;
    private final JwtTokenUtil util;

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

    public void changePassword(String id, ChangePassawordRequest request) {
        repository.findById(id).ifPresentOrElse(it -> {
                    if (!new BCryptPasswordEncoder().matches(
                            request.getCurrentPassword(),
                            it.getPassword()
                    )) {
                        log.error("[USER] - username id = {} password incorret", id);

                        throw new PasswordIncorrectException(PASSWORD_INCORRECT);
                    }

                    if (new BCryptPasswordEncoder().matches(
                            request.getNewPassword(),
                            it.getPassword()
                    )) {
                        log.error("[USER] - username id = {} password equals", id);

                        throw new PasswordEqualsException(PASSWORD_EQUALS);
                    }

                    it.updatePassword(request, repository);
                },
                () -> {
                    log.error("[USER] - username id = {} not found", id);

                    throw new UserNotFoundException(USER_NOT_FOUND);
                });
    }

}
