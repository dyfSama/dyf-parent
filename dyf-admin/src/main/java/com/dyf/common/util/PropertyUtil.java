package com.dyf.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>
 * properties配置文件获取(加载的是config.properties)
 * </p>
 *
 * @auther: duyafei
 * @date: 2018/11/12 15:50
 */
@Slf4j
public class PropertyUtil {

    private static Properties properties;

    static {
        loadProperties();
    }

    synchronized private static void loadProperties() {
        properties = new Properties();
        InputStream is = null;
        try {
            is = PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(is);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static String getProperty(String key) {
        if (null == properties) {
            loadProperties();
        }
        return properties.getProperty(key);
    }


}
