package com.leslie.product.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 20110
 */
@Data
public class UploadCategoryIconVo {

    private String name;
    private MultipartFile file;
}
