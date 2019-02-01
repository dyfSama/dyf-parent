package com.dyf.system.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.dyf.common.controller.BaseController;
import com.dyf.common.msg.MsgInfo;
import com.dyf.common.page.TableDataInfo;
import com.dyf.system.role.entity.SysRole;
import com.dyf.system.role.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author dyfSama
 * @since 2019-01-31
 */
@Slf4j
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    /**
     * 跳转到list
     */
    @GetMapping("/toList")
    public String toList() {
        return "system/role/roleList";
    }

    /**
     * 跳转到表单页面
     */
    @GetMapping("/toForm")
    public String toForm(Model model, SysRole entity) {
        if (StringUtils.isNotBlank(entity.getId())) {
            model.addAttribute("entityId", entity.getId());
            return "system/role/roleEdit";
        } else {
            return "system/role/roleAdd";
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
    public TableDataInfo list(HttpServletRequest request, SysRole entity) {
        startPage(request);
        //QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>();
        LambdaQueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>().lambda();
        List<SysRole> dataList = roleService.list(queryWrapper);
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
    public MsgInfo save(SysRole entity) {
        return getMsgInfo(roleService.saveOrUpdate(entity), MsgInfo.OPT_SAVE);
    }

    /**
     * 删除单条(逻辑删除)
     *
     * @param role
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public MsgInfo delete(SysRole entity) {
        return getMsgInfo(roleService.removeById(entity.getId()), MsgInfo.OPT_DEL);
    }

    /**
     * 获取单条记录
     *
     * @param role
     * @return
     */
    @ResponseBody
    @PostMapping("/get")
    public SysRole get(SysRole entity) {
        return roleService.getById(entity.getId());
    }
}
