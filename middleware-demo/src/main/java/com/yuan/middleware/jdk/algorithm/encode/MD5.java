package com.yuan.middleware.jdk.algorithm.encode;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5是单向加密型也就是只能加密不能解密的算法，但是同一个字符串只有一个MD5加密结果，即在不加盐的md5加密后，同一个字符串加密后的字符集结果是相同的。
 * 所以验证方法为：把用户再次登录的密码进行md5加密后和注册时存在数据库的加密后的密码一对比即可验证。
 * md5加盐加密：所谓的加盐即是随机产生一些字符串，这些字符串即所谓的盐（调料品），把字符串和密码相加后在进行MD5加密，就不可以直接反查询。
 *
 * @author yjm
 * @version 1.0
 * @date 2020/3/1 6:49 下午
 */
public class MD5 {
    private static final String key = "20200301yuan";

    public static void main(String[] args) {
        String s = DigestUtils.md5Hex("yuan" + key);
        String s1 = DigestUtils.md5Hex("yuan" + key);

        System.out.println(s.equals(s1));

    }
}
