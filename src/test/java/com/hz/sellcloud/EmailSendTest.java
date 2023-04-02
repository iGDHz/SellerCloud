package com.hz.sellcloud;

import com.hz.sellcloud.service.impl.EmailServiceImpl;
import com.hz.sellcloud.service.impl.UsersServiceImpl;
import com.hz.sellcloud.utils.MailTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import java.io.File;

@SpringBootTest
public class EmailSendTest {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    UsersServiceImpl usersService;
    @Test
    public void mailSend(){
        emailService.sendSimpleMessage("1165929365@qq.com","Test Mail","测试邮件");
    }

    @Test
    public void httpmailSend() throws MessagingException {
        String format = String.format(MailTemplate.VERIFY_EAMIL, "Hz", "有个人", "https://www.bing.com/?mkt=zh-CN", "https://www.bing.com/?mkt=zh-CN");
        emailService.sendHttpMessage("1165929365@qq.com","测试邮件", format,new File("D:\\Hz_Java\\sellcloud\\src\\main\\resources\\application.yaml"));
    }
}
