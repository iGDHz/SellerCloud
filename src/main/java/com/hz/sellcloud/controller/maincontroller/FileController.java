package com.hz.sellcloud.controller.maincontroller;

import com.hz.sellcloud.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.hadoop.shaded.javax.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@Api(tags = "用户管理")
@RequestMapping("/file")
public class FileController {

    @Value("${sellcloud.file.path}")
    private String FILE_PATH;

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/download/{year}/{month}/{filename}",method = RequestMethod.GET)
    @ApiModelProperty("获取文件")
    public void downloadFile(@PathVariable String year,
                             @PathVariable String month,
                             @PathVariable String filename,
                             HttpServletResponse response) throws IOException {
        fileService.downloadFile(year,month,filename,response);
    }

    @RequestMapping(value = "/picture/{year}/{month}/{filename}",method = RequestMethod.GET)
    @ApiModelProperty("获取图片")
    public void downloadPicture(@PathVariable String year,
                             @PathVariable String month,
                             @PathVariable String filename,
                             HttpServletResponse response) throws IOException {
        String extension = StringUtils.getFilenameExtension(filename);
        String contentType;
        switch (extension.toLowerCase()) {
            case "png":
                contentType = MediaType.IMAGE_PNG_VALUE;
                break;
            case "jpeg":
            case "jpg":
                contentType = MediaType.IMAGE_JPEG_VALUE;
                break;
            default:
                throw new IllegalArgumentException("Unsupported image format: " + extension);
        }
        fileService.downloadFile(year,month,filename,response,contentType);
    }


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ApiModelProperty("上传文件")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        return fileService.uploadFile(file);
    }
}
