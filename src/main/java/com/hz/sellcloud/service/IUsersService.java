package com.hz.sellcloud.service;

import com.hz.sellcloud.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
public interface IUsersService extends IService<Users> {
    Users getByName(String name);
    boolean signUser(Users users);

    boolean updateUserState(int id);

}
