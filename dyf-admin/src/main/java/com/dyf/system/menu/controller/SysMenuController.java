package com.dyf.system.menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.dyf.common.controller.BaseController;
import com.dyf.common.msg.MsgInfo;
import com.dyf.common.page.TableDataInfo;
import com.dyf.system.menu.entity.SysMenu;
import com.dyf.system.menu.service.ISysMenuService;
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
 * 菜单表 前端控制器
 * </p>
 *
 * @author dyfSama
 * @since 2019-01-31
 */
@Slf4j
@Controller
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private ISysMenuService menuService;

    /**
     * 跳转到list
     */
    @GetMapping("/toList")
    public String toList() {
        return "system/menu/menuList";
    }

    /**
     * 跳转到表单页面
     */
    @GetMapping("/toForm")
    public String toForm(Model model, SysMenu entity) {
        if (StringUtils.isNotBlank(entity.getId())) {
            model.addAttribute("entityId", entity.getId());
            return "system/menu/menuEdit";
        } else {
            return "system/menu/menuAdd";
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
    public TableDataInfo list(HttpServletRequest request, SysMenu entity) {
        startPage(request);
        //QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>();
        LambdaQueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>().lambda();
        List<SysMenu> dataList = menuService.list(queryWrapper);
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
    public MsgInfo save(SysMenu entity) {
        return getMsgInfo(menuService.saveOrUpdate(entity), MsgInfo.OPT_SAVE);
    }

    /**
     * 删除单条(逻辑删除)
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public MsgInfo delete(SysMenu entity) {
        return getMsgInfo(menuService.removeById(entity.getId()), MsgInfo.OPT_DEL);
    }

    /**
     * 获取单条记录
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @PostMapping("/get")
    public SysMenu get(SysMenu entity) {
        return menuService.getById(entity.getId());
    }
}
