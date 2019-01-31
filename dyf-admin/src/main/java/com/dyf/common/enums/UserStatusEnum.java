package com.dyf.common.enums;

/**
 * <p>
 * 用户状态枚举
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/31 09:33
 */
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL("0", "正常"),
    /**
     * 删除
     */
    DELETE("1", "删除"),

    /**
     * 锁定
     */
    LOCKED("2", "锁定");

    private final String code;
    private final String info;

    UserStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    /**
     * 是否正常
     *
     * @param code
     * @return
     */
    public static boolean isNormal(String code) {
        return UserStatusEnum.NORMAL.getCode().equals(code);
    }

    /**
     * 是否锁定
     *
     * @param code
     * @return
     */
    public static boolean isLocked(String code) {
        return UserStatusEnum.LOCKED.getCode().equals(code);
    }
}
