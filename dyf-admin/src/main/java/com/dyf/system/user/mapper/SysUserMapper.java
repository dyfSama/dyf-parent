package com.dyf.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyf.system.user.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * todo
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/17 12:50
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
