package com.security.account.service;

import com.security.account.controller.request.user.UserRequest;
import com.security.account.entity.User;

public interface IUserService {
    User create(UserRequest request);

    User getUsername(String username);
}
