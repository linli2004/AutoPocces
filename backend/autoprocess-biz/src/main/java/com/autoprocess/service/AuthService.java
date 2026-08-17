package com.autoprocess.service;

import com.autoprocess.entity.SysUser;

public interface AuthService {
    record LoginResult(SysUser user,String token){}

    LoginResult login(String username,String password);

    SysUser currentUser();
}
