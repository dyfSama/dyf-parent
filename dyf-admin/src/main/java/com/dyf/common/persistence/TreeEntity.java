package com.dyf.common.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @className: TreeEntity
 * @description: TODO
 * @auther: duyafei
 * @date: 2018/10/17 19:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class TreeEntity<T>  extends DataEntity<T> implements Serializable {

    private static final long serialVersionUID = -2909163295969479276L;

    /**
     * parentId
     */
    protected String parentId;

    /**
     * 是否显示
     */
    private String isShow;


    public TreeEntity() {
    }


}
