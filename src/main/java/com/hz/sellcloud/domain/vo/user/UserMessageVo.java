package com.hz.sellcloud.domain.vo.user;

import com.hz.sellcloud.entity.Companies;
import com.hz.sellcloud.entity.Supermarkets;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.service.FileService;
import com.hz.sellcloud.service.impl.FileServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
public class UserMessageVo {


    @Data
    class user{
        String username;
        String password;
        String email;
        String phone;
    }
    @Data
    class company{
        int companyId;
        String companyName;
    }
    @Data
    class supermark{
        String sname;
        String detail;
        String address;
        license license;
    }
    @Data
    class license{
        String name;
        String url;
    }

    Map<Object,Object> form;

    public UserMessageVo(){}
    public UserMessageVo(Users user,  Supermarkets supermarkets,Companies companies){
        form = new HashMap<>();
        // 1.将user转化
        UserMessageVo.user uservo = new user();
        uservo.setUsername(user.getUserName());
        uservo.setPassword(user.getUserPassword());
        uservo.setEmail(user.getUserMail());
        uservo.setPhone(user.getUserPhone());
        form.put("user",uservo);

        // 2.将supermarket转化
        supermark supermarkvo = new supermark();
        supermarkvo.setAddress(supermarkets.getSupermarkRegionid());
        supermarkvo.setDetail(supermarkets.getSupermarkDetail());
        supermarkvo.setSname(supermarkets.getSupermarkName());
        license license = new license();
        String supermarkLicense = "/sellcloud/file/download/"+supermarkets.getSupermarkLicense();
        supermarkLicense = supermarkLicense.replace("\\", "/");
        license.setName(supermarkLicense.substring(supermarkLicense.lastIndexOf("/")+1));
        license.setUrl(supermarkLicense);
        supermarkvo.setLicense(license);
        form.put("supermark",supermarkvo);

        //3.将companies转化
        company companyvo = new company();
        companyvo.setCompanyId(companies.getCompanyId());
        companyvo.setCompanyName(companies.getCompanyName());
        form.put("company",companyvo);
    }
}
