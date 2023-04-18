package com.hz.sellcloud.controller.maincontroller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.controller.BaseController;
import com.hz.sellcloud.domain.request.products.ProductListNameRequest;
import com.hz.sellcloud.domain.request.products.ProductListRequest;
import com.hz.sellcloud.domain.response.CommonResponse;
import com.hz.sellcloud.domain.vo.product.ProductListVo;
import com.hz.sellcloud.domain.vo.product.ProductNameVo;
import com.hz.sellcloud.domain.vo.product.ProductVo;
import com.hz.sellcloud.entity.Authorization;
import com.hz.sellcloud.entity.Products;
import com.hz.sellcloud.entity.Supermarkets;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.FileService;
import com.hz.sellcloud.service.IProductsService;
import com.hz.sellcloud.service.impl.AuthorizationServiceImpl;
import com.hz.sellcloud.service.impl.ProductsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.hadoop.shaded.org.eclipse.jetty.util.MultiPartInputStreamParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hz
 * @since 2022-10-31
 */
@Controller
@Api(tags = "商品信息管理")
@RequestMapping("/product")
public class ProductsController extends BaseController {
    @Autowired
    IProductsService productService;

    @Autowired
    AuthorizationServiceImpl authorizationService;

    @Autowired
    FileService fileService;

    @ResponseBody
    @ApiOperation("获取商品信息")
    @RequestMapping(value = "/listbysid",method = RequestMethod.GET)
    public CommonResponse searchBySid(@RequestParam("token") String token,
                                      @RequestParam("sid") int sid){
        //1.验证token
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        //2.根据sid查询
        List<ProductVo> productVos = productService.searchBySid(sid);
        return new CommonResponse(productVos).sucess();
    }

    @ResponseBody
    @ApiOperation("获取商品信息")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public CommonResponse searchProducts(@RequestBody ProductListRequest request) {
        //1.验证用户token
        Users user = TokenToUsers(request.getToken());
        if(user == null){
            return new CommonResponse().error(403,"用户token不存在");
        }
        //2.数据库查询
        ProductListRequest.Conditions conditions = request.getConditions();
        conditions.setUserId(user.getUserId());
        conditions.setPage(conditions.getPage()-1);
        List<ProductVo> productVos = productService.selectByConditon(request.getConditions());
        long total = productService.countByCondition(request.getConditions());
        ProductListVo productListVo = new ProductListVo();
        productListVo.setProducts(productVos);
        productListVo.setTotal(total);
        return new CommonResponse(productListVo).sucess();
    }

    @ResponseBody
    @ApiOperation("更新商品信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public CommonResponse updateProducts(HttpServletRequest httprequest){
        MultipartHttpServletRequest request  =   (MultipartHttpServletRequest) httprequest;
        //1.验证token
        String token = request.getParameter("token");
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        //2.获取product数据
        String id = request.getParameter("id");
        int category_id = Integer.parseInt(request.getParameter("category_id"));
        String name = request.getParameter("name");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        MultipartFile picture = request.getFile("picture");
        int sid = Integer.parseInt(request.getParameter("sid"));
        //3.转化为dao层
        Products product = new Products();
        if(!id.equals("undefined"))product.setProductId(Integer.parseInt(id));
        product.setProductCategory(category_id);
        product.setProductName(name);
        product.setProductPrice(price);
        product.setProductBelonged(sid);
        if(picture != null){
            String filename = fileService.uploadFile(picture);
            String fileurl = "http://localhost:8082/sellcloud/file/picture/"+filename.replace("\\","/");
            product.setProductPicture(fileurl);
        }

        //4.存入数据库
        productService.UpdateProduct(product);

        return new CommonResponse().sucess();
    }

    @ResponseBody
    @ApiOperation("删除商品信息")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public CommonResponse deleteProduct(@RequestParam("token") String token,
                                        @RequestParam("productid") int productid){
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        productService.removeById(productid);
        return new CommonResponse().sucess();
    }

    @ResponseBody
    @ApiOperation("获取商品名称列表")
    @RequestMapping(value = "/namelist",method = RequestMethod.POST)
    public CommonResponse listProductsName(@RequestBody ProductListNameRequest request){
        String token = request.getToken();
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        List<Integer> supermarket = request.getSupermarkets();
        List<String> categorys = request.getCategorys();
        List<ProductNameVo> list = new ArrayList<>();
        if(supermarket.size() != 0 || categorys.size() != 0){
            list = productService.getByConditions(supermarket,categorys);
        }
        return new CommonResponse(list).sucess();
    }

}
