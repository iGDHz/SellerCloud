package com.hz.sellcloud.domain.request.user;

import lombok.Data;

@Data
public class UserUpdatePasswordRequest {
    String token;
    String password;
}
