package com.mmall.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadServiceTest {
    @Autowired
    private UploadService uploadService;

    @Test
    public void saveFile() throws IOException {
        ClassPathResource resource = new ClassPathResource("/static/temp/anchor");
        System.out.println();
    }
}