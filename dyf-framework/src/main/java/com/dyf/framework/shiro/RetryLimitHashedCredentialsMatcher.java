package com.dyf.framework.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyf.common.enums.UserStatusEnum;
import com.dyf.system.user.entity.SysUser;
import com.dyf.system.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * todo
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/30 23:04
 */
@Slf4j
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private SysUserService userService;
    private Cache<String, AtomicInteger> passwordRetryCache;


    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        //获取用户名
        String username = (String) token.getPrincipal();
        //获取用户登录次数
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        log.info("失败次数" + retryCount);
        if (retryCount.incrementAndGet() > 5) {
            // 登录失败次数大于5次
            SysUser user = userService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserName, username));

            if (user != null && UserStatusEnum.isNormal(user.getStatus())) {
//                userService.update(user, new QueryWrapper<SysUser>().lambda())
                user.setStatus(UserStatusEnum.LOCKED.getCode());
                userService.updateById(user);

                log.info("错误5次, 锁定账户:" + user.getUserName());
            }
            throw new LockedAccountException();
        }

        //判断用户和密码是否正确
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //如果正确,从缓存中将用户登录计数 清除
            passwordRetryCache.remove(username);
        }
        return matches;
    }

    /**
     * 根据用户名 解锁用户
     *
     * @param username
     * @return
     */
    public void unlockAccount(SysUser entity) {
        SysUser user = userService.getById(entity);
        if (user != null) {
            //修改数据库的状态字段为锁定
            user.setStatus(UserStatusEnum.NORMAL.getCode());
            userService.updateById(user);
            passwordRetryCache.remove(user.getUserName());
        }
    }

}