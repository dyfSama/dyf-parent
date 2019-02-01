package com.dyf.framework.mail;

import com.dyf.common.contant.Contants;
import com.dyf.common.controller.BaseController;
import com.dyf.common.msg.MsgInfo;
import com.dyf.framework.mail.service.MailSenderService;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * todo
 * </p>
 *
 * @auther: duyafei
 * @date: 2018/10/31 22:28
 */
@Slf4j
@Controller
@RequestMapping("/system/mailSender")
public class MailSenderController extends BaseController {

    @Autowired
    private MailSenderService mailSenderService;


    @Autowired
    private Producer producer;


    /**
     * 注册发送邮件验证码
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @PostMapping("/sendMailVerifyCode")
    public MsgInfo sendMailVerifyCode(SimpleMailMessage simpleMailMessage, HttpServletRequest request) {
        //获取验证码
        String verifyCode = producer.createText();
        simpleMailMessage.setSubject("ssm注册验证码");
        simpleMailMessage.setText("验证码:" + verifyCode);
        try {
            mailSenderService.sendSimpleMail(simpleMailMessage);
            HttpSession session =request.getSession();
            session.setAttribute(Contants.MAIL_CODE_SESSION_KEY, verifyCode);
            session.setMaxInactiveInterval(50);
        } catch (Exception e) {
            return MsgInfo.error();
        }
        return MsgInfo.success();
    }
}
