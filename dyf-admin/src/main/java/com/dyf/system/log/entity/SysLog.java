package com.dyf.system.log.entity;

import com.dyf.common.persistence.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author dyfSama
 * @since 2019-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysLog extends DataEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 执行方法
     */
    private String execMethod;

    /**
     * 请求url
     */
    private String remoteUrl;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * ip地址
     */
    private String remoteAddr;

    /**
     * 用时(ms)
     */
    private Integer execTime;

    /**
     * 是否异常(0,正常,;1异常)
     */
    private Integer isException;

    /**
     * 异常信息
     */
    private String exceptionInfo;


}
