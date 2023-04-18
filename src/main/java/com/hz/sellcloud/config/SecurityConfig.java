package com.hz.sellcloud.config;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.RedisService;
import com.hz.sellcloud.service.impl.RedisServiceImpl;
import com.hz.sellcloud.service.impl.UsersServiceImpl;
import com.hz.sellcloud.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sun.net.www.protocol.http.AuthenticationHeader;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.SelectionKey;
import java.util.Date;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UsersServiceImpl usersService;

    @Resource(name = "redisService")
    RedisServiceImpl redisService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();
//                .userDetailsService(new SellUsersDeatilService(usersService))
//                .authorizeRequests()
//                .antMatchers("/users/authorize/**").hasRole("0")
////                .antMatchers("/admin/**").hasRole("1")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
////                .failureHandler(new SellAuthenticationFailureHandler())
//                .successHandler(new SellAuthenticationSuccessHandler(usersService,redisService))
//                .and()
//                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }

}


//配置用户登录功能
class SellUsersDeatilService implements UserDetailsService{

    UsersServiceImpl usersService;

    RedisServiceImpl redisService;

    public SellUsersDeatilService(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    public SellUsersDeatilService(UsersServiceImpl usersService, RedisServiceImpl redisService) {
        this.usersService = usersService;
        this.redisService = redisService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        SecurityContext context = SecurityContextHolder.getContext();
//        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_name",username);
//        Users user = usersService.getOne(queryWrapper);
//        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(String.valueOf(user.getUserLevel()));
//        if(user == null) {
//            user = new Users();
//            user.setUserName("admin");
//            user.setUserPassword("errorpassword");
//            authorities = AuthorityUtils.createAuthorityList(user.getUserLevel().toString());
//        }
//        return new User(user.getUserName(),new BCryptPasswordEncoder().encode(user.getUserPassword()),authorities);
        return null;
    }
}

class SellAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    UsersServiceImpl usersService;

    RedisServiceImpl redisService;

    public SellAuthenticationSuccessHandler(RedisServiceImpl redisService) {
        this.redisService = redisService;
    }

    public SellAuthenticationSuccessHandler(UsersServiceImpl usersService, RedisServiceImpl redisService) {
        this.usersService = usersService;
        this.redisService = redisService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject res = new JSONObject();
        String username = authentication.getName();
        res.put("authorization", JSONObject.toJSONString(authentication));
        String token = Md5Utils.Token(username, new Date());
        redisService.set(token,JSONObject.toJSONString(usersService.getByName(username)),60*60);
        res.put("token",token);
        response.getWriter().write(res.toJSONString());
    }
}

class SellAuthenticationFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        if(e instanceof BadCredentialsException){
            response.sendRedirect("http://localhost:8080/sellcloud/login?error");
        }
    }
}