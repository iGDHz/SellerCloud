package com.hz.sellcloud.controller.maincontroller;


import com.hz.sellcloud.entity.Category;
import com.hz.sellcloud.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@Controller
@Api(tags = "分类管理")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @RequestMapping(value = "/getcategory",method = RequestMethod.GET)
    @ApiOperation("获取分类信息")
    public List<Category> getCategory(){
        return categoryService.list();
    }

}
