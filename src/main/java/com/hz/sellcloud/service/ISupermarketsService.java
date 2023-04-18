package com.hz.sellcloud.service;

import com.hz.sellcloud.entity.Supermarkets;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    List<Supermarkets> searchByCompanyId(Integer userId);

    List<Supermarkets> searchByUserId(Integer userId);

    Supermarkets getByUserId(int userId);
}
