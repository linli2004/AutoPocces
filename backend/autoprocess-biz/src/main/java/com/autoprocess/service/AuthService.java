package com.autoprocess.service;

import com.autoprocess.entity.SysUser;

public interface AuthService {
    record LoginResult(SysUser user,String token){}

    /**
     * 用户名 + 密码登录。
     */
    LoginResult login(String username,String password);

    /**
     * 根据用户 ID 查询用户。
     * 主要供 JWT 认证过滤器重新加载真实用户信息。
     */
    SysUser findById(String userId);

    SysUser currentUser();
}
