package com.leslie.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author 20110
 */
@Data
public class UploadUserImgVo implements Serializable {

    private Long userId;
    private MultipartFile file;
}
