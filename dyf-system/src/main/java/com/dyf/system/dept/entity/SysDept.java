package com.dyf.system.dept.entity;

import com.dyf.common.persistence.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author dyfSama
 * @since 2019-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysDept extends DataEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父级机构ID
     */
    private String parentId;

    /**
     * 所有父辈Id
     */
    private String parentIds;

    /**
     * 机构名称
     */
    private String deptName;

    /**
     * 排序
     */
    private Integer deptSort;

    /**
     * 机构图标
     */
    private String icon;

    /**
     * 是否显示（0显示 1隐藏）
     */
    private String isShow;


}
