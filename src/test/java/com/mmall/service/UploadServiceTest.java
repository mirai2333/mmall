package com.mmall.service;

import com.mmall.common.FtpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadServiceTest {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private FtpUtil ftpUtil;

    @Test
    public void saveFile() throws IOException {
    }
}