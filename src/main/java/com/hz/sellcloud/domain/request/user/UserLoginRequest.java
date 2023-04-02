package com.hz.sellcloud.domain.request.user;

import lombok.Data;

@Data
public class UserLoginRequest {
    String username;

    String password;
}
