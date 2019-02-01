package com.dyf.framework.shiro;

import com.dyf.common.contant.Contants;
import com.dyf.common.msg.MsgInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 登录注销相关
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/30 12:55
 */
@Controller
@Slf4j
public class LoginController {

    /**
     * 是否开启验证码登录
     */
    @Value("${login.isVerifyCode}")
    private String isVerifyCode;

    @ResponseBody
    @PostMapping("/shiroLogin")
    public MsgInfo shiroLogin(HttpServletRequest request) {
        //获取登录的数据
        String username = WebUtils.getCleanParam(request, "username");
        String password = WebUtils.getCleanParam(request, "password");
        String rememberMe = WebUtils.getCleanParam(request, "rememberMe");
        String verifyCode = WebUtils.getCleanParam(request, "verifyCode");

        //校验验证码
        if (!checkVerifyCode(request, verifyCode)) {
            return MsgInfo.error("验证码不正确!");
        }

        // 获得当前Subject
        Subject currentUser = SecurityUtils.getSubject();
        // 验证用户
        String msg = "";
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            // 执行登录
            currentUser.login(token);
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定";
        } catch (DisabledAccountException e) { //没用到
            msg = "帐号已被禁用";
        } catch (ExpiredCredentialsException e) { //没用到
            msg = "帐号已过期";
        } catch (UnknownAccountException e) {
            msg = "帐号不存在";
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！";
        }
        // 验证是否登录成功!
        if (currentUser.isAuthenticated()) {
            //TODO 初始化各种资源
            return MsgInfo.success();
        } else {
            return MsgInfo.error(msg);
        }
    }

    /**
     * 校验验证码
     *
     * @param request
     * @param verifyCode
     * @return
     */
    private boolean checkVerifyCode(HttpServletRequest request, String verifyCode) {
        //是否进行图像验证码校验
        if ("false".equals(isVerifyCode)) {
            return true;
        }
        //sesiion中的验证码
        String verifyCodeInSesion = request.getSession().getAttribute(Contants.IMG_CODE_SESSIO_KEY) + "";
        return verifyCode != null && verifyCode.equalsIgnoreCase(verifyCodeInSesion);
    }

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 后台首页
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 模版
     *
     * @return
     */
    @GetMapping("/template")
    public String template() {
        return "template/template";
    }
}
