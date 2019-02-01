package com.dyf.common.msg;

import lombok.Data;

/**
 * @className: MsgInfo
 * @description: TODO
 * @auther: duyafei
 * @date: 2018/10/12 16:04
 */
@Data
public class MsgInfo {

    public static final String SAVE_SUCCESS = "保存成功!";
    public static final String SAVE_FAIl = "保存失败!";
    public static final String UPDATE_SUCCESS = "更新成功!";
    public static final String UPDATE_FAIl = "更新失败!";
    public static final String DEL_SUCCESS = "删除成功!";
    public static final String DEL_FAIl = "删除失败!";
    public static final String UPLOAD_SUCCESS = "上传成功!";
    public static final String UPLOAD_FAIL = "上传失败!";


    public static final int OPT_SAVE = 0;
    public static final int OPT_UPDATE = 1;
    public static final int OPT_DEL = 2;
    public static final int OPT_UPLOAD = 3;

    private static final String OPT_SUCCESS = "操作成功!";
    private static final String OPT_FAIl = "操作失败!";


    private static final String SUCCESS = "0";
    private static final String ERROR = "1";
    private static final String NO_PERM = "2";

    /**
     * 消息状态码
     */
    private String status;

    /**
     * 消息文本
     */
    private String message;

    public MsgInfo() {
    }

    public MsgInfo(String status, String message) {
        this.status = status;
        this.message = message;
    }


    /**
     * 成功的消息
     *
     * @return
     */
    public static MsgInfo success() {
        return new MsgInfo(SUCCESS, OPT_SUCCESS);
    }

    /**
     * 成功的消息 自定义消息文本
     *
     * @return
     */
    public static MsgInfo success(String message) {
        return new MsgInfo(SUCCESS, message);
    }

    /**
     * 失败的消息
     *
     * @return
     */
    public static MsgInfo error() {
        return new MsgInfo(ERROR, OPT_FAIl);
    }

    /**
     * 失败的消息,自定义消息文本
     *
     * @return
     */
    public static MsgInfo error(String message) {
        return new MsgInfo(ERROR, message);
    }


    /**
     * 权限不足的消息,自定义消息文本
     *
     * @return
     */
    public static MsgInfo noPerm(String message) {
        return new MsgInfo(NO_PERM, "权限不足! [ " + message + " ]");
    }

}
