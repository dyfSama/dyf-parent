package com.dyf.framework.mail.service;

import org.springframework.mail.SimpleMailMessage;

import java.io.File;

/**
 * <p>
 * 邮件接口
 * </p>
 *
 * @auther: duyafei
 * @date: 2018/10/31 22:13
 */
public interface MailSenderService {
    /**
     * 简单纯文本邮件
     *
     * @param message
     */
    void sendSimpleMail(SimpleMailMessage simpleMailMessage);


    /**
     * 带附件的邮件
     *
     * @param message
     * @param file
     */
    void sendAttachmentMail(SimpleMailMessage simpleMailMessage, File file);

}
