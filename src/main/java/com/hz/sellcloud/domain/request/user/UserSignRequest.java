package com.hz.sellcloud.domain.request.user;

import com.hz.sellcloud.utils.RegexValidate;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class UserSignRequest {
    String username;
    String userpwd;
    String useremail;
    String userPhone;
    String sname;
    String saddress_areaId;
    String saddress_detail;
    MultipartFile license;
    int companyId;

    public boolean isVaild(){
        //1.用户名长度不能少于6
        if(username.length() < 6) return false;
        //2.用户密码长度不能少于6
        if(userpwd.length() < 6) return false;
        //3.验证邮箱格格式
        if(!RegexValidate.isPhone(userPhone)) return false;
        //4.验证地址信息
        if(!RegexValidate.isEmail(useremail)) return false;
        return true;
    }
}
