package com.mmall.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
public class UploadService {
    private final ServletContext servletContext;

    @Autowired
    public UploadService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String saveFile(MultipartFile file) {
        String tempFile = "保存出错";
        try {
            ClassPathResource resource = new ClassPathResource("/static/temp/anchor");
            File anchorFile = new File(resource.getURL().getPath());
            //这里的parentPath后面不带斜杠

            tempFile = anchorFile.getParent() + File.separator +
                    UUID.randomUUID();
            //添加扩展名
            String originalFilename = file.getOriginalFilename();
            String ext = StringUtils.isEmpty(originalFilename) ?
                    "" : originalFilename.substring(originalFilename.lastIndexOf("."));
            tempFile += ext;

            file.transferTo(new File(tempFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}
