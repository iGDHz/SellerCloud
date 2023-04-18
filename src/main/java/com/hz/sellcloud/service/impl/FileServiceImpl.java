package com.hz.sellcloud.service.impl;

import com.hz.sellcloud.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${sellcloud.file.path}")
    public String FILE_PATH;

    @Value("${sellcloud.domain}")
    public String Domain;

    @Value("${server.port}")
    public String port;

    @Override
    public void downloadFile(String year, String month, String filename, HttpServletResponse response) throws IOException {
        String path = FILE_PATH+ File.separator+year+File.separator+month+File.separator+filename;
        InputStream inputStream = new FileInputStream(path);// 文件的存放路径
        response.reset();
        response.setContentType("application/octet-stream");
        String fileoringname = new File(path).getName();
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }

    @Override
    public void downloadFile(String year, String month, String filename, HttpServletResponse response, String mediaType) throws IOException {
        String path = FILE_PATH+ File.separator+year+File.separator+month+File.separator+filename;
        InputStream inputStream = new FileInputStream(path);// 文件的存放路径
        response.reset();
        response.setContentType(mediaType);
        IOUtils.copy(new FileInputStream(new File(path)),response.getOutputStream());
    }
    @Override
    public String uploadFile(MultipartFile file) {
        String fileType = file.getContentType().split("/")[1]; //检查文件类型
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf(".")); //获取文件后缀名
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy" + File.separator + "MM");
        String date = dateFormat.format(new Date());

        String path = FILE_PATH + File.separator + date;
        fileName = UUID.randomUUID()+suffixName; //为文件生成UUID
        String filePath = path+File.separator+fileName;
        File targetfile = new File(filePath);
        if(!targetfile.getParentFile().exists()){
            //目录不存在时，创建父目录
            targetfile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(targetfile);
        } catch (IOException e) {
            return null;
        }
        return date+File.separator+fileName;
    }

    @Override
    public boolean removeFile(String filename) {
        String path = FILE_PATH+File.separator+filename;
        File file = new File(path);
        return file.delete();
    }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public String getFileUrl(String relpath){
        return "http://"+Domain+":"+port+"/file/"+relpath;
    }


}
