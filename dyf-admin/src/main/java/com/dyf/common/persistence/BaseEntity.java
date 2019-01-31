package com.dyf.common.persistence;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: BaseEntity
 * @description: 实体基类
 * @auther: duyafei
 * @date: 2018/10/8 16:44
 */
@Data
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = -7193151794191221710L;

    /**
     * 实体编号(唯一标识)
     */
    protected String id;

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
//    protected boolean isNewRecord = false;

    public BaseEntity() {

    }

    public BaseEntity(String id) {
        this();
        this.id = id;
    }

    /**
     * 插入之前执行方法，子类实现
     */
    public abstract void preInsert();

    /**
     * 更新之前执行方法，子类实现
     */
    public abstract void preUpdate();


}
