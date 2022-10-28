package com.hz.sellcloud.service.impl;

import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.mapper.UsersMapper;
import com.hz.sellcloud.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
