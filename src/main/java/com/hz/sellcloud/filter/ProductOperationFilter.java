package com.hz.sellcloud.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.entity.Authorization;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.impl.AuthorizationServiceImpl;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "productPermissionFilter",urlPatterns = {"/products"})
@Order(1)
public class ProductOperationFilter implements Filter {
    @Autowired
    RedisServiceImpl redisService;

    @Autowired
    AuthorizationServiceImpl authorizationService;
    protected Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response ,FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        //如果是(登录界面,登录态失效界面)，直接放行
        logger.info("检查管理员权限");
        String token = (String) servletRequest.getSession().getAttribute("token");
        Integer sid =(Integer) servletRequest.getSession().getAttribute("sid");
        Users user = redisService.getUser(token);
        Authorization auth = authorizationService.getOne(new QueryWrapper<Authorization>().eq("user_id", user.getUserId())
                .eq("supermark_id", sid));
        if(user == null || auth == null){
            logger.info("无管理权限，返回管理员登陆页");
            request.getRequestDispatcher("/users").forward(request, response);
        } else {
            logger.info("权限验证成功，用户：{}",user);
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
