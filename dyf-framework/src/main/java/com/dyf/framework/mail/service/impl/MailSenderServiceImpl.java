package com.dyf.framework.mail.service.impl;

import com.dyf.framework.mail.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

/**
 * <p>
 * 邮件服务: 1.简单文本邮件;2.附件邮件;3.密送抄送邮件
 * </p>
 *
 * @auther: duyafei
 * @date: 2018/10/31 22:07
 */
@Service
@Slf4j
public class MailSenderServiceImpl implements MailSenderService {



    @Value("${spring.mail.username}")
    private String from;

    /**
     * 注入javaMailSender
     */
    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 简单纯文本邮件
     *
     * @param message
     */
    @Override
    public void sendSimpleMail(SimpleMailMessage simpleMailMessage) {

        //设置发件人
        simpleMailMessage.setFrom(from);
        StringBuffer to = new StringBuffer();
        String[] tos = simpleMailMessage.getTo();
        if (tos != null && tos.length > 0) {
            Arrays.stream(tos).forEach(x -> to.append(x).append(" "));
        }
        log.info("Sending mail...: from <" + from + "> to <" + to + ">");
        try {
            javaMailSender.send(simpleMailMessage);
            log.info("Send mail successfully!");
        } catch (MailException e) {
            log.info("Send mail fail!");
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 带附件的邮件
     *
     * @param message
     * @param file
     */
    @Override
    public void sendAttachmentMail(SimpleMailMessage simpleMailMessage, File file) {

        //可以发送附件的
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            //multipart 为true
            helper = new MimeMessageHelper(mimeMessage, true);

            //解析发件地址
            helper.setFrom(new InternetAddress("du_yafei@163.com"));
            //解析收件地址,群发
            //InternetAddress[] toAddrs = parseToAddrs(simpleMailMessage.getTo());
//            helper.setTo(toAddrs);
            helper.setTo(simpleMailMessage.getTo());
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(simpleMailMessage.getText());
            //添加附件
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

        //发送邮件
        try {
            log.info("Sending mail.....");
            javaMailSender.send(mimeMessage);
            log.info("Send mail successfully!");
        } catch (MailException e) {
            log.info("Send mail fail!");
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 解析收件地址
     *
     * @param to 最大10
     * @return
     * @throws AddressException
     */
    private InternetAddress[] parseToAddrs(String[] to) throws AddressException {
        InternetAddress[] toAddrs = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            toAddrs[i] = new InternetAddress(to[i]);
        }
        return toAddrs;
    }


}
