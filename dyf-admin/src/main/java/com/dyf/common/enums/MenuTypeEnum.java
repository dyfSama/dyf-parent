package com.dyf.common.enums;

/**
 * <p>
 * 菜单类型
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/31 09:58
 */
public enum MenuTypeEnum {

    CONTENTS("C", "目录"),
    MENU("M", "菜单"),
    BUTTON("B", "按钮");

    private final String code;
    private final String info;

    MenuTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }
}
