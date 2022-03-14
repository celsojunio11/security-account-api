package com.security.account.service.impl;

import com.security.account.exceptions.PasswordExpiredException;
import com.security.account.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static com.security.account.config.AccountMessageConfig.CREDENTIAL_INVALID;
import static com.security.account.config.AccountMessageConfig.PASSWORD_EXPIRED;

@AllArgsConstructor
@Service
@Slf4j
public class AuthService implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return new User(
                email,
                userService.getUsername(email).getPassword(),
                new ArrayList<>()
        );
    }

    public void validAuthenticate(AuthenticationManager authenticationManager, String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            log.error("[AUTH] - username = {} and password {} is invalid.", username, password);
            throw new BadCredentialsException(CREDENTIAL_INVALID);
        }
    }

    public long passwordExpirationDate(String email) {
        long dayRemaining = ChronoUnit.DAYS.between(LocalDate.now(), userService.getUsername(email).getUpdatedAt().plusDays(15));

        long EXPIRATION_DAY = 0;

        if (dayRemaining < EXPIRATION_DAY) {
            log.error("[AUTH] - password expired in {} days.", dayRemaining);
            throw new PasswordExpiredException(PASSWORD_EXPIRED);
        }
        return dayRemaining;
    }

}
