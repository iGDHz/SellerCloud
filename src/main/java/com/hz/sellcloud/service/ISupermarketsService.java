package com.hz.sellcloud.service;

import com.hz.sellcloud.entity.Supermarkets;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
public interface ISupermarketsService extends IService<Supermarkets> {
    boolean updateSupermarketState(int id);
}
