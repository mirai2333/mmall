package com.mmall.service;

import com.mmall.common.FtpUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.*;

@Service
@Slf4j
public class UploadService {
    private final FtpUtil ftpUtil;

    @Autowired
    public UploadService(FtpUtil ftpUtil) {
        this.ftpUtil = ftpUtil;
    }

    public String saveFile(MultipartFile file) {
        String fileName = "保存出错";
        File tempFile;
        try {
            //1、缓存到本机
            ClassPathResource resource = new ClassPathResource("/static/temp/anchor");
            File anchorFile = new File(resource.getURL().getPath());
            fileName = UUID.randomUUID().toString();
            //这里的parentPath后面不带斜杠，要添加斜杠
            String tempFilePath = anchorFile.getParent() + File.separator;
            //添加扩展名
            String originalFilename = file.getOriginalFilename();
            String ext = StringUtils.isEmpty(originalFilename) ?
                    "" : originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName += ext;
            tempFile = new File(tempFilePath+fileName);
            file.transferTo(tempFile);
            //2、上传到FTP服务器
            ftpUtil.uploadFile(Collections.singletonList(tempFile));
            //3、删除本地缓存
            boolean delete = tempFile.delete();
            log.info("删除文件缓存是否成功，{}",delete);
        } catch (IOException e) {
            e.printStackTrace();
            return fileName;
        }
        return fileName;
    }
}
