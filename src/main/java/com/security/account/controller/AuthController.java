package com.security.account.controller;

import com.security.account.service.impl.AuthService;
import com.security.account.config.security.JwtTokenUtil;
import com.security.account.controller.request.auth.AuthRequest;
import com.security.account.controller.response.auth.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public AuthResponse createAuthenticationToken(@Valid @RequestBody AuthRequest authenticationRequest) {
        userDetailsService.passwordExpirationDate(authenticationRequest.getEmail());

        userDetailsService.validAuthenticate(
                authenticationManager,
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        );

        return AuthResponse.fromAuth(
                jwtTokenUtil
                        .generateToken(userDetailsService
                                .loadUserByUsername(authenticationRequest.getEmail())
                        ),
                userDetailsService.passwordExpirationDate(authenticationRequest.getEmail())
        );
    }


}