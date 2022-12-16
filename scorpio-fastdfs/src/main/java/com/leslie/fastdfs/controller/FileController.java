package com.leslie.fastdfs.controller;

import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.leslie.fastdfs.utils.FastDfsClientUtil;
import com.leslie.utils.FileLimitTypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author 20110
 * @description 文件相关操作API接口
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private FastDfsClientUtil fastDfsClientUtil;

    /**
     * 获取respon稍后向前端页面写入图片
     */
    @Autowired
    private HttpServletResponse response;

    /**
     * 上传文件api接口
     *
     * @param file 文件
     * @return 文件路径
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestPart("file") MultipartFile file) {
        try {
            // 判断文件是否存在
            if (file == null) {
                throw new RuntimeException("文件不存在");
            }
            // 获取文件的完整名称
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)) {
                throw new RuntimeException("文件不存在");
            }
            if (!FileLimitTypeUtils.isImage(originalFilename)) {
                return "不支持该类型文件";
            }
            // 获取到返回的fileId
            String url = fastDfsClientUtil.uploadFile(file);
            // 拼接返回
            return url;
        } catch (Exception e) {
            log.error("文件上传失败！{}", e.getMessage());
        }
        return "文件上传失败";
    }

    /**
     * 删除文件[这里仅作测试用,实际中不会这样直接在controller删除
     * 是会根据业务从数据库中拿到url进行删除同时删除数据库关联数据，注意被占用无法删除]
     *
     * @param fileId 文件的fileId 包含分组, EG=>>group1/M00/22/22/***.**
     * @return 成功或失败
     */
    @DeleteMapping("/del")
    public String delFile(@RequestParam String fileId) {
        try {
            fastDfsClientUtil.delFile(fileId);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除失败";
    }

    /**
     * 文件下载，页面直接访问我们这个服务器，在页面眼里，直接从我们这个服务拿到了文件，而我们去fastdfs服务器获取文件
     *
     * @param groupName
     * @param path
     */
    @GetMapping("/download")
    public void download(@RequestParam String groupName, @RequestParam String path) {
        try {
            // 拆分获取出文件名称,方便一会写入的时候写出正确的文件名(url中的文件名和服务器中是一致的，至少默认是这样的)
            String[] split = path.split("/");
            String imgName = split[split.length - 1];
            // 设置请求头为附件模式
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(imgName, "UTF-8"));
            // 调用客户端获取文件字节码
            byte[] imageByte = fastDfsClientUtil.download(groupName, path);
            // 从response获取响应流
            ServletOutputStream outputStream = response.getOutputStream();
            // 向流写入数据
            outputStream.write(imageByte);
            // 关流
            outputStream.close();
        } catch (FdfsServerException e) {
            log.error("文件不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}