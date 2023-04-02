package com.hz.sellcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hz.sellcloud.entity.Companies;
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
    public Users getByName(String username){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        return getOne(queryWrapper);
    }

    /*
        @params: user DTO层user对象
        @description: 用户注册
        @return : 插入更新结果
     */

    public boolean signUser(Users user) {
        return super.saveOrUpdate(user);
    }

    /*
        @params: id 用户id
        @description: 更新用户状态为已注册状态
        @retrun 返回更新结果
     */
    @Override
    public boolean updateUserState(int id) {
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<Users>().eq("user_id", id).set("user_state", 1);
        return update(updateWrapper);
    }
}
