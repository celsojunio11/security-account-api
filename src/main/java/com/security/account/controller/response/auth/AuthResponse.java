package com.security.account.controller.response.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String expireDate;

    public static AuthResponse fromAuth(String token, long expireDate){
        return new AuthResponse(
                token,
                "Sua senha expira em " + expireDate + " dias."
        );
    }
}