package com.dyf.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * <p>
 * todo
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/30 16:26
 */
public class entryptPassword {

    public static void main(String[] args) {
        System.out.println(entryptPassword("admin", "admin"));
    }

    /**
     * @param algorithmName 加密算法
     * @param todoPassword  明文密码
     * @param username      盐值(用户名)
     * @return
     */
    public static String entryptPassword(String todoPassword, String username) {
        //加密算法
        String algorithmName = "MD5";
        //密码
        Object credentials = todoPassword;
        //盐值:用户名
        Object salt = username;
        //加密次数
        int hashIterations = 1024;
        Object result = new SimpleHash(algorithmName, credentials, salt, hashIterations);
        return result.toString();
    }
}
