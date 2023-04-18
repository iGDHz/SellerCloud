package com.hz.sellcloud.service;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    void downloadFile(String year, String month, String filename, HttpServletResponse response) throws IOException;

    void downloadFile(String year, String month, String filename, HttpServletResponse response, String mediaType) throws IOException;

    String uploadFile(MultipartFile file);

    boolean removeFile(String filename);

    String getFilePath();

    String getFileUrl(String relpath);
}
