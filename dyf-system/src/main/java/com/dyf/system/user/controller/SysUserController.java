package com.dyf.system.user.controller;

import com.dyf.common.controller.BaseController;
import com.dyf.common.msg.MsgInfo;
import com.dyf.common.page.TableDataInfo;
import com.dyf.system.user.entity.SysUser;
import com.dyf.system.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 登录注册
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/17 13:00
 */
@Slf4j
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService userService;

//    @Autowired
//    private RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher;

    /**
     * 跳转到list
     */
    @GetMapping("/toList")
    public String toList(HttpServletRequest request, Model model) {
        model.addAttribute("user", userService.getById("0"));
        model.addAttribute("entityId", "sdfsdf");
        startPage(request);
        List<SysUser> dataList = userService.list();
        model.addAttribute("userList", dataList);
        return "system/user/userList";
    }

    /**
     * 跳转到表单页面
     */
    @GetMapping("/toForm")
    public String toForm(Model model, SysUser entity) {
        if (StringUtils.isNotBlank(entity.getId())) {
            model.addAttribute("entityId", entity.getId());
            return "system/user/userEdit";
        } else {
            return "system/user/userAdd";
        }
    }

    /**
     * 数据list
     *
     * @param request
     * @param entity
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public TableDataInfo getList(HttpServletRequest request, SysUser entity) {
        startPage(request);
        List<SysUser> dataList = userService.list();
        return getTableInfo(dataList);
    }

    /**
     * 保存更新
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public MsgInfo save(SysUser entity) {
        return getMsgInfo(userService.saveOrUpdate(entity), MsgInfo.OPT_SAVE);
    }

    /**
     * 删除单条
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public MsgInfo delete(SysUser entity) {
        return getMsgInfo(userService.removeById(entity.getId()), MsgInfo.OPT_DEL);
    }

    /**
     * 获取单条记录
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("getById")
    public SysUser getById(SysUser entity) {
        return userService.getById(entity.getId());
    }

    /**
     * 根据用户名解除锁定
     *
     * @param user
     * @return
     */
//    @ResponseBody
//    @PostMapping("unlock")
//    public MsgInfo unlock(SysUser entity) {
//
//        retryLimitHashedCredentialsMatcher.unlockAccount(entity);
//        return MsgInfo.success();
//    }
}
