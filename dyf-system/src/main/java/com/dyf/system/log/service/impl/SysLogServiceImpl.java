package com.dyf.system.log.service.impl;

import com.dyf.system.log.entity.SysLog;
import com.dyf.system.log.mapper.SysLogMapper;
import com.dyf.system.log.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author dyfSama
 * @since 2019-01-31
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

}
