package com.hz.sellcloud.service.impl;

import com.hz.sellcloud.entity.Category;
import com.hz.sellcloud.mapper.CategoryMapper;
import com.hz.sellcloud.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
