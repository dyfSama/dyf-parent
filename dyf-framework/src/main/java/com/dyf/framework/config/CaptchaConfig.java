package com.dyf.framework.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * <p>
 * 验证码工具配置
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/30 19:06
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha getCaptchaProducer() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");//不要边框
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "35");
        properties.setProperty("kaptcha.textproducer.font.size", "28");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");//不要干扰线
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");//图片样式:阴影[水纹,鱼眼,阴影]
        properties.setProperty("kaptcha.session.key", "imgCode");//sessionkey
        properties.setProperty("kaptcha.session.date", "30");//session有效时间
        properties.setProperty("kaptcha.textproducer.char.string", "23456789ABCEFGHJKLMNPRSTUVWXY");//验证码的来源
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
