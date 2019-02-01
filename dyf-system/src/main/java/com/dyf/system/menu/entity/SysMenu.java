package com.dyf.system.menu.entity;

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
public class SysMenu extends DataEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父级菜单ID
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单排序
     */
    private Integer menuSort;

    /**
     * 菜单类型(C:目录; B:按钮,;M:菜单)
     */
    private String menuType;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 是否显示（0显示 1隐藏）
     */
    private String isShow;


}
