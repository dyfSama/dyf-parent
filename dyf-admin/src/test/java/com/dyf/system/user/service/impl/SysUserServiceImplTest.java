package com.dyf.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyf.DYFApplication;
import com.dyf.system.user.entity.SysUser;
import com.dyf.system.user.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DYFApplication.class)
public class SysUserServiceImplTest {

    @Autowired
    private SysUserService userService;

    @Test
    public void getOne() {
        /*SysUser  user = userService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserName, "admin"));
        System.out.println(user);*/
    }
}