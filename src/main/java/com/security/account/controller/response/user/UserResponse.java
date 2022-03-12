package com.security.account.controller.response.user;

import com.security.account.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String clientId;
    private String nameClient;
    private String email;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getClientId(),
                user.getNameClient(),
                user.getUsername());
    }
}
