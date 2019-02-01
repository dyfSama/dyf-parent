package com.dyf.system.user.entity;


import com.dyf.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends DataEntity<SysUser> {

    /**
     * 实体类实现序列化接口
     */
    private static final long serialVersionUID = 1L;


    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 头像url
     */
//    private String avatar;
    private byte[] avatar;


    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;


    /******** 其他字段 *********

     /**
     * 机构Id
     */
    private String deptId;

//    /**
//     * 机构信息
//     */
//    private Dept dept;
//
//    /**
//     * 角色组(接收前段角色列表的)
//     */
//    private String[] roleIds;
//
//    /**
//     * 角色集合
//     */
//    private List<Role> roles;

    public SysUser() {
        super();
    }


    public SysUser(String id) {
        super(id);
    }
}
