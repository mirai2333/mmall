package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


/**
 * @description: FTP工具类
 * @author: Mirai.Yang
 * @create: 2019-02-14 09:46
 * <pre>
 *       ██████╗   █████╗  ██╗  ██╗ ██╗  ██╗ ██╗
 *      ██╔════╝  ██╔══██╗ ██║ ██╔╝ ██║ ██╔╝ ██║
 *      ██║  ███╗ ███████║ █████╔╝  █████╔╝  ██║
 *      ██║   ██║ ██╔══██║ ██╔═██╗  ██╔═██╗  ██║
 *      ╚██████╔╝ ██║  ██║ ██║  ██╗ ██║  ██╗ ██║
 *       ╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝
 *  </pre>
 */
@Slf4j
@Component
public class FtpUtil {

    @Value("${ftp.server.ip}")
    private String ftpIp;
    @Value("${ftp.server.port}")
    private String ftpPort;
    @Value("${ftp.server.remotePath}")
    private String ftpRemotePath;
    @Value("${ftp.username}")
    private String ftpUsername;
    @Value("${ftp.password}")
    private String ftpPassword;

    /**
     * @param fileList
     * @throws IOException
     */
    public void uploadFile(List<File> fileList) throws IOException {
        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;
        try {
            //连接服务器
            ftpClient.connect(this.ftpIp);
            if (ftpClient.login(this.ftpUsername,this.ftpPassword)){
                boolean directory = ftpClient.changeWorkingDirectory(this.ftpRemotePath);
                log.info("切换目录是否成功，{}",directory);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File file : fileList) {
                    fis = new FileInputStream(file);
                    ftpClient.storeFile(file.getName(),fis);
                }
            }
        } catch (IOException e) {
            log.error("文件上传失败!");
            e.printStackTrace();
        }finally {
            if (fis != null){fis.close();}
            ftpClient.disconnect();
        }
    }
}
