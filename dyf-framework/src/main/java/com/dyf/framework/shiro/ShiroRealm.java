package com.dyf.framework.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyf.common.enums.UserStatusEnum;
import com.dyf.system.user.entity.SysUser;
import com.dyf.system.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 自定义的shiroRealm
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/30 15:49
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        log.info("shiro验证登录................");
        if (username == null) {
            throw new AccountException("用户名不能为空!");
        }
        SysUser user = userService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserName, username));
        if (null == user) {
            throw new UnknownAccountException();
        }
        if (UserStatusEnum.isLocked(user.getStatus())) {
            throw new LockedAccountException();
        }
        /**
         * principal   认证实体信息
         * credentials  密码
         * realmName   当前real对象的name
         * credentialsSalt  盐值
         */
        Object principal = username;
        Object credentials = user.getPassword();
        String realmName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo saInfo = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return saInfo;
    }
}
