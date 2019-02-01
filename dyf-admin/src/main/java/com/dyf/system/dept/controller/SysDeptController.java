package com.dyf.system.dept.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.dyf.common.controller.BaseController;
import com.dyf.common.msg.MsgInfo;
import com.dyf.common.page.TableDataInfo;
import com.dyf.system.dept.entity.SysDept;
import com.dyf.system.dept.service.ISysDeptService;
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
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private ISysDeptService deptService;

    /**
     * 跳转到list
     */
    @GetMapping("/toList")
    public String toList() {
        return "system/dept/deptList";
    }

    /**
     * 跳转到表单页面
     */
    @GetMapping("/toForm")
    public String toForm(Model model, SysDept entity) {
        if (StringUtils.isNotBlank(entity.getId())) {
            model.addAttribute("entityId", entity.getId());
            return "system/dept/deptEdit";
        } else {
            return "system/dept/deptAdd";
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
    public TableDataInfo list(HttpServletRequest request, SysDept entity) {
        startPage(request);
        //QueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>();
        LambdaQueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>().lambda();
        List<SysDept> dataList = deptService.list(queryWrapper);
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
    public MsgInfo save(SysDept entity) {
        return getMsgInfo(deptService.saveOrUpdate(entity), MsgInfo.OPT_SAVE);
    }

    /**
     * 删除单条(逻辑删除)
     *
     * @param dept
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public MsgInfo delete(SysDept entity) {
        return getMsgInfo(deptService.removeById(entity.getId()), MsgInfo.OPT_DEL);
    }

    /**
     * 获取单条记录
     *
     * @param dept
     * @return
     */
    @ResponseBody
    @PostMapping("/get")
    public SysDept get(SysDept entity) {
        return deptService.getById(entity.getId());
    }
}
