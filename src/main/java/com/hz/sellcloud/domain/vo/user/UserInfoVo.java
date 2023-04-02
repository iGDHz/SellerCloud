package com.hz.sellcloud.domain.vo.user;

import com.alibaba.fastjson.JSON;
import com.hz.sellcloud.entity.Users;
import lombok.Data;

@Data
public class UserInfoVo {
    String avatar;
    String name;
    String introduction;
    String roles;

    public UserInfoVo generate(String userinfo){
        Users user = JSON.parseObject(userinfo,Users.class);
        this.avatar = user.getUserAvatar();
        this.name = user.getUserName();
        this.introduction = user.getUserIntroduction();
        this.roles = user.getUserRole();
        return this;
    }
}
