package com.dyf.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @className: TableDataInfo
 * @description: TODO
 * @auther: duyafei
 * @date: 2018/10/5 11:05
 */
@Data
public class TableDataInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 消息状态码
     */
    private int code;

    /**
     * 提示文本
     */
    private String msg;

    /**
     * 总记录数layui
     */
    private long count;

    /**
     * 列表数据layui
     */
    private List<?> data;

    /**
     * 总记录数bootstrap-table
     */
    private long total;

    /**
     * 列表数据bootstrap-table
     */
    private List<?> rows;


}
