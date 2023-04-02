package com.hz.sellcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hz.sellcloud.entity.Supermarkets;
import com.hz.sellcloud.mapper.SupermarketsMapper;
import com.hz.sellcloud.service.ISupermarketsService;
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
public class SupermarketsServiceImpl extends ServiceImpl<SupermarketsMapper, Supermarkets> implements ISupermarketsService {

    /*
        @params: id 超市id
        @description: 更新超市状态为已注册状态
        @retrun 返回更新结果
     */
    @Override
    public boolean updateSupermarketState(int id) {
        UpdateWrapper<Supermarkets> wrapper = new UpdateWrapper<Supermarkets>().eq("supermark_id", id).set("supermark_state", 1);
        return update(wrapper);
    }
}
