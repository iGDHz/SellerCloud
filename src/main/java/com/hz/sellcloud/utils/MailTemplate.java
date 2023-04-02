package com.hz.sellcloud.utils;

public class MailTemplate {
    public static String VERIFY_EAMIL = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <title>邮件标题</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1 style=\"text-align: center\">邮件标题</h1>\n" +
            "    <p style=\"text-align: center\">尊敬的用户：</p>\n" +
            "    <p style=\"text-align: center\"><strong>%s</strong> ，请求注册：</p>\n" +
            "    <p style=\"text-align: center\"><strong>请不要将此邮件转发至任何人，后果有邮件接收人承担</strong></p>\n" +
            "    <p style=\"text-align: center\">请您在收到邮件后在3天之内处理</p>\n" +
            "    <br>\n" +
            "    <div style=\"text-align:center;\">\n" +
            "        <a href=\"%s\">\n" +
            "            <button style=\"background-color:dodgerblue; color:white; padding: 10px 20px; border-radius: 5px;\">同意</button>\n" +
            "        </a>\n" +
            "        <a href=\"%s\">\n" +
            "            <button style=\"background-color:orangered; color:white; padding: 10px 20px; border-radius: 5px;\">拒绝</button>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";

    public static String FINAL_CHECK = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>结果通知</title>\n" +
            "    <style>\n" +
            "        /* 将 body 元素的高度设置为 100%%,以便让子元素垂直居中 */\n" +
            "        body {\n" +
            "            height: 100%%;\n" +
            "            display: flex;\n" +
            "            justify-content: center;\n" +
            "            align-items: center;\n" +
            "        }\n" +
            "        /* 将文字元素设置为居中 */\n" +
            "        h1 {\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>%s</h1>\n" +
            "</body>\n" +
            "</html>";
}
