package com.leslie.fastdfs.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 20110
 * @description fastdfs客户端工具类
 */
@Slf4j
@Component
public class FastDfsClientUtil {

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 文件上传
     *
     * @param file 文件类
     * @return fileId 文件Id,包含group和uri的完整路径 EG=>>MO/22/22/**.jpg
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile((InputStream) file.getInputStream(),
                file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        log.info("成功上传文件，文件路径为: {}", storePath);
        return storePath.getFullPath();
    }

    /**
     * 文件删除
     *
     * @param filePath 文件的fileId 包含分组, EG=>>group1/M00/22/22/***.**
     */
    public void delFile(String filePath) {
        storageClient.deleteFile(filePath);
    }

    /**
     * 文件下载
     *
     * @param groupName 分组id  EG=>> group10
     * @param path      文件uri 分组之后的内容[不含开头的/] EG=>>MO/22/22/kkk.jpg
     * @return 文件的字节数组
     */
    public byte[] download(String groupName, String path) throws IOException {
        InputStream ins = storageClient.downloadFile(groupName, path, new DownloadCallback<InputStream>() {
            @Override
            public InputStream recv(InputStream ins) throws IOException {
                // 将此ins返回给上面的ins
                return ins;
            }
        });
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = ins.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}