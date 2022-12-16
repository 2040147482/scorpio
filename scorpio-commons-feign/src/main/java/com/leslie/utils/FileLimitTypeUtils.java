package com.leslie.utils;

/**
 * @author 20110
 * @description 限制文件上传类型
 */
public class FileLimitTypeUtils {

    /**
     * 限制图片后缀为ico、jpg、jpeg、png
     *
     * @param fileName 文件名
     * @return true 或 false
     */
    public static boolean isImage(String fileName) {
        String[] imageType = {"ico", "jpg", ".jpeg", ".png"};
        for (String s : imageType) {
            if (fileName.toLowerCase().endsWith(s)) {
                return true;
            }
        }
        return false;
    }
}