package com.dyf.system.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.dyf.common.controller.BaseController;
import com.dyf.common.msg.MsgInfo;
import com.dyf.common.page.TableDataInfo;
import com.dyf.system.log.entity.SysLog;
import com.dyf.system.log.service.ISysLogService;
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
 * 系统日志表 前端控制器
 * </p>
 *
 * @author dyfSama
 * @since 2019-01-31
 */
@Slf4j
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {

    @Autowired
    private ISysLogService logService;

    /**
     * 跳转到list
     */
    @GetMapping("/toList")
    public String toList() {
        return "system/log/logList";
    }

    /**
     * 跳转到表单页面
     */
    @GetMapping("/toForm")
    public String toForm(Model model, SysLog entity) {
        if (StringUtils.isNotBlank(entity.getId())) {
            model.addAttribute("entityId", entity.getId());
            return "system/log/logEdit";
        } else {
            return "system/log/logAdd";
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
    public TableDataInfo list(HttpServletRequest request, SysLog entity) {
        startPage(request);
        //QueryWrapper<SysLog> queryWrapper = new QueryWrapper<SysLog>();
        LambdaQueryWrapper<SysLog> queryWrapper = new QueryWrapper<SysLog>().lambda();
        List<SysLog> dataList = logService.list(queryWrapper);
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
    public MsgInfo save(SysLog entity) {
        return getMsgInfo(logService.saveOrUpdate(entity), MsgInfo.OPT_SAVE);
    }

    /**
     * 删除单条(逻辑删除)
     *
     * @param log
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public MsgInfo delete(SysLog entity) {
        return getMsgInfo(logService.removeById(entity.getId()), MsgInfo.OPT_DEL);
    }

    /**
     * 获取单条记录
     *
     * @param log
     * @return
     */
    @ResponseBody
    @PostMapping("/get")
    public SysLog get(SysLog entity) {
        return logService.getById(entity.getId());
    }
}
