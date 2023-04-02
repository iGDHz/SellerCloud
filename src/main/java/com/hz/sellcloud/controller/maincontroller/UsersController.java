package com.hz.sellcloud.controller.maincontroller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.domain.request.user.UserLoginRequest;
import com.hz.sellcloud.domain.request.user.UserLogoutRequest;
import com.hz.sellcloud.domain.request.user.UserSignRequest;
import com.hz.sellcloud.domain.response.CommonResponse;
import com.hz.sellcloud.domain.vo.user.UserInfoVo;
import com.hz.sellcloud.domain.vo.user.UserLoginVo;
import com.hz.sellcloud.entity.Authorization;
import com.hz.sellcloud.entity.Companies;
import com.hz.sellcloud.entity.Supermarkets;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.exceptions.UserSignUpException;
import com.hz.sellcloud.service.*;
import com.hz.sellcloud.service.impl.AuthorizationServiceImpl;
import com.hz.sellcloud.service.impl.EmailServiceImpl;
import com.hz.sellcloud.service.impl.FileServiceImpl;
import com.hz.sellcloud.service.impl.UsersServiceImpl;
import com.hz.sellcloud.utils.MailTemplate;
import com.hz.sellcloud.utils.Md5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@Controller
@Api(tags = "用户管理")
@RequestMapping("/user")
public class UsersController {

    @Autowired
    ISupermarketsService supermarketsService;
    @Autowired
    IUsersService usersService;

    @Autowired
    AuthorizationServiceImpl authorizationService;

    @Autowired
    ICompaniesService companiesService;

    @Autowired
    FileService fileService;
    @Resource(name = "redisService")
    RedisService redisService;

    @Autowired
    EmailServiceImpl emailService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("用户登录")
    public CommonResponse LoginUser(@RequestBody UserLoginRequest request){
        String username = request.getUsername();
        String password = request.getPassword();
        //1.获取请求当中的用户名和密码
        Users user = usersService.getByName(username);

        //2.进行用户验证
        if(user == null){
            return new CommonResponse<String>().error(403,"用户不存在");
        }else if(!password.equals(user.getUserPassword())){
            return new CommonResponse<String>().error(403,"用户名或密码错误");
        }else if(user.getuserState() == 0){
            return new CommonResponse().error(403,"用户注册信息待验证");
        }

        //3.为用户生成专属token
        String token = Md5Utils.Token(username);
        redisService.set(token,JSON.toJSONString(user),60*60*24);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setToken(token);

        //4.将生成的token返回
        return new CommonResponse(userLoginVo).sucess();
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取用户信息")
    public CommonResponse InfoUser(@RequestParam("token") String Token){
        //1.判断token是否存在
        String jsonObj =  (String) redisService.get(Token);
        if(jsonObj == null){
            return new CommonResponse("token不存在,请重新登陆").error();
        }
        UserInfoVo userInfoVo = new UserInfoVo().generate(jsonObj);
        return new CommonResponse(userInfoVo).sucess();
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("用户注销")
    public CommonResponse LogoutUser(@RequestBody UserLogoutRequest request){
        String token = request.getToken();
        //2.验证用户token是否存在
        String jsonObj = (String) redisService.get(token);
        if(jsonObj == null){
            return new CommonResponse("用户token不存在").error();
        }
        //3.返回成功信息
        redisService.del(token);
        return new CommonResponse("sucess").sucess();
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("用户注册")
    @Transactional
    public CommonResponse SignupUser(HttpServletRequest httprequest) throws UserSignUpException {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) httprequest;
        UserSignRequest userSignRequest = new UserSignRequest();
        try {
            userSignRequest.setUsername(request.getParameter("username"));
            userSignRequest.setUserpwd(request.getParameter("userpwd"));
            userSignRequest.setUseremail(request.getParameter("useremail"));
            userSignRequest.setUserPhone(request.getParameter("userphone"));
            userSignRequest.setSname(request.getParameter("sname"));
            userSignRequest.setSaddress_areaId(request.getParameter("saddress_areaId"));
            userSignRequest.setSaddress_detail(request.getParameter("saddress_detail"));
            userSignRequest.setLicense(request.getFile("license"));
            userSignRequest.setCompanyId(Integer.parseInt(request.getParameter("companyId")));
        }catch (Exception e){
            return new CommonResponse().error(403,"请求表单错误");
        }
        //1.验证用户是否存在
        Users username = usersService.getByName(userSignRequest.getUsername());
        if(username != null){
            return new CommonResponse().error(403,"用户已经存在(若无法登录请耐心等待管理员审核)");
        }
        //2.验证用户信息是否符合格式
        if(!userSignRequest.isVaild()){
            return new CommonResponse().error(404,"请求格式不正确");
        }
        //3.验证用户公司是否存在
        if(!companiesService.isExist(userSignRequest.getCompanyId())){
            return new CommonResponse().error(404,"请求公司不存在");
        }
        //4.将用户信息暂存
        Users user = new Users();
        user.setUserName(userSignRequest.getUsername());
        user.setUserPassword(userSignRequest.getUserpwd());
        user.setUserRole("SubUser");
        user.setUserMail(userSignRequest.getUseremail());
        user.setUserAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        user.setuserState((byte) 0);
        usersService.signUser(user);
        //5.注册超市信息
        Supermarkets supermarkets = new Supermarkets();
        supermarkets.setSupermarkBelonged(userSignRequest.getCompanyId());
        supermarkets.setSupermarkName(userSignRequest.getSname());
        supermarkets.setSupermarkDetail(userSignRequest.getSaddress_detail());
        supermarkets.setSupermarkState((byte) 0);
        String fileUrl = fileService.uploadFile(userSignRequest.getLicense());
        supermarkets.setSupermarkLicense(fileUrl);
        supermarkets.setSupermarkRegionid(userSignRequest.getSaddress_areaId());
        supermarketsService.saveOrUpdate(supermarkets);
        //6.将请求信息发送邮箱给对应公司邮箱进行验证注册
        Companies company = companiesService.getById(userSignRequest.getCompanyId());
        try{
            if(company == null){
                throw new UserSignUpException(); //抛出异常，执行事务回滚
            }
        }catch (UserSignUpException exception){
            return new CommonResponse<>().error(403,"公司id错误");
        }
        //7.将数据缓存到redis当中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",user.getUserId());
        jsonObject.put("sid",supermarkets.getSupermarkId());
        jsonObject.put("license",fileUrl);
        String signtoken = Md5Utils.md5(String.valueOf(user.getUserId()));
        redisService.hSet("usersign",signtoken,jsonObject.toJSONString());
        //8.查找注册的用户邮箱
        String mail = companiesService.GetMail(userSignRequest.getCompanyId());
        if(mail == null){
            return new CommonResponse().error(403,"用户邮箱不存在");
        }
        //9.将验证注册信息发送到邮箱进行验证
        String domain = request.getServerName();
        int serverPort = request.getServerPort();
        try {
            emailService.sendHttpMessage(user.getUserMail(),"用户请求注册邮件",
                    String.format(MailTemplate.VERIFY_EAMIL,
                            user.getUserName()+"("+supermarkets.getSupermarkName()+")",
                           "http://"+domain+":"+serverPort+"/sellcloud/user/signok/"+signtoken,
                            "http://"+domain+":"+serverPort+"/sellcloud/user/signdeny/"+signtoken),new File(fileService.getFilePath()+ File.separator+fileUrl));
        } catch (MessagingException e) {
            return new CommonResponse().error(500,"邮件发送失败");
        }
        return new CommonResponse("注册申请已发至管理员邮箱，预计3个工作日内返回申请结果").sucess();
    }

    @RequestMapping( value = "/signok/{userInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    @ApiOperation("用户注册验证")
    @Transactional
    public String AuthorSignOk(@PathVariable String userInfoId){
        //1.查看用户注册id是否存在
        JSONObject userinfo = JSONObject.parseObject((String) redisService.hGet("usersign", userInfoId));
        String filepath = (String) userinfo.get("license");
        Integer userid = (Integer) userinfo.get("userid");
        Integer sid = (Integer) userinfo.get("sid");
        if(StringUtils.isEmpty(filepath) ||
            userid == null ||
            sid == null){
            return String.format(MailTemplate.FINAL_CHECK,"请求异常");
        }
        //2.更新用户、超市注册状态
        usersService.updateUserState(userid);
        supermarketsService.updateSupermarketState(sid);
        fileService.removeFile(filepath);
        //3.发送结果邮件给原用户
        Users users = usersService.getById(userid);
        String userMail = users.getUserMail();
        emailService.sendSimpleMessage(userMail,"注册成功提醒","尊敬的用户，你的注册请求已经通过");
        //4.从redis当中删除对应key
        redisService.hDel("usersign",userInfoId);
        return String.format(MailTemplate.FINAL_CHECK,"注册成功");
    }

    @RequestMapping(value = "/signdeny/{userInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    @Transactional
    @ApiOperation("用户注册验证")
    public String AuthorSignDeny(@PathVariable String userInfoId){
        //1.查看用户注册id是否存在
        JSONObject userinfo = JSONObject.parseObject((String) redisService.hGet("usersign", userInfoId));
        String filepath = (String) userinfo.get("license");
        Integer userid = (Integer) userinfo.get("userid");
        Integer sid = (Integer) userinfo.get("sid");
        if(StringUtils.isEmpty(filepath) ||
                userid == null ||
                sid == null){
            return String.format(MailTemplate.FINAL_CHECK,"请求异常");
        }
        //2.将用户信息全部删除
        Users users = usersService.getById(userid);
        usersService.removeById(userid);
        supermarketsService.removeById(sid);
        fileService.removeFile(filepath);
        //3.发送结果邮件给原用户
        String userMail = users.getUserMail();
        emailService.sendSimpleMessage(userMail,"注册失败提醒","尊敬的用户，你的注册请求未通过");
        return String.format(MailTemplate.FINAL_CHECK,"已拒绝注册申请");
    }

    @RequestMapping(value = "/authorize",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("用户授权")
    public String AuthorUser(@RequestParam("/token") @ApiParam("用户token") String token,
                             @RequestParam("username") @ApiParam("授权用户名") String username,
                             @RequestParam("password") @ApiParam("授权用户密码") String password,
                             @RequestParam("supermarkid") @ApiParam("超市id") Integer supermarkid,
                             @RequestParam("toauthor") @ApiParam("授权/取消授权") boolean toauth){
        JSONObject res = new JSONObject();
        res.put("status",200);
        Object author = redisService.get(token);
        if(author == null){
            res.put("status",403);
            res.put("msg","用户未授权或者长时间未操作需重新登录");
            return JSONObject.toJSONString(res);
        }
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        queryWrapper.eq("user_password",Md5Utils.md5(password));
        Users user = usersService.getOne(queryWrapper);
        if(user == null){
            res.put("staus",404);
            res.put("msg","用户不存在或密码错误");
            return JSONObject.toJSONString(user);
        }
        if(toauth) {authorizationService.saveOrUpdate(new Authorization(user.getUserId(), supermarkid));
        res.put("msg","授权用户"+username+"操作"+supermarkid+"权限");}
        else {
            QueryWrapper<Authorization> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",user.getUserId());
            wrapper.eq("supermark_id",supermarkid);
            authorizationService.remove(wrapper);
            res.put("msg","取消授权用户"+username+"操作"+supermarkid+"权限");
        }
        return JSONObject.toJSONString(res);
    }


}


