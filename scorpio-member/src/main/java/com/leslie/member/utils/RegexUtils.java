package com.leslie.member.utils;


/**
 * @author 20110
 */
public class RegexUtils {

    /**
     * 手机号正则表达式
     */
    public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

    /**
     * 是否是无效手机格式
     *
     * @param phone 要校验的手机号
     * @return true:符合，false：不符合
     */
    public static boolean isPhoneInvalid(String phone) {
        if (phone.isEmpty()) {
            return false;
        }
        return !phone.matches(PHONE_REGEX);
    }
}
