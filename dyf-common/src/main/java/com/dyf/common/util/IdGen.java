package com.dyf.common.util;

import java.util.UUID;

/**
 * @className: IdGen
 * @description: TODO
 * @auther: duyafei
 * @date: 2018/10/8 17:11
 */
public class IdGen {
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
