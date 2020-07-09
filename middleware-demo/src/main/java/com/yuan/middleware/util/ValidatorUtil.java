package com.yuan.middleware.util;

/**
 * 判断字符串是否为合法手机号 11位 13 14 15 18开头
 * @author yuanjm
 * @date 2020/6/16 11:49 上午
 */

public class ValidatorUtil {

    /**
     * 禁止实例化
     */
    private ValidatorUtil() {
    }

    /**
     * 判断是否为正确的邮件格式
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
    }

    /**
     * 判断字符串是否为合法手机号 11位 13 14 15 18开头
     *
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches("^(13|14|15|18)\\d{9}$");
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * 判断字符串是否为非空(包含null与"")
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为非空(包含null与"","    ")
     *
     * @param str
     * @return
     */
    public static boolean isNotEmptyIgnoreBlank(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim())) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为空(包含null与"")
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空(包含null与"","    ")
     *
     * @param str
     * @return
     */
    public static boolean isEmptyIgnoreBlank(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim())) {
            return true;
        }
        return false;
    }


}