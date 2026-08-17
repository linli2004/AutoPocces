package com.autoprocess.service.impl;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.common.util.JwtUtil;
import com.autoprocess.entity.SysUser;
import com.autoprocess.enums.UserStatus;
import com.autoprocess.mapper.SysUserMapper;
import com.autoprocess.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(SysUserMapper sysUserMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.sysUserMapper = sysUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public LoginResult login(String username, String password) {
        SysUser user = sysUserMapper.selectByUsername(username);
        if(user==null || !passwordEncoder.matches(password,user.getPasswordHash())){
            throw new BusinessException("用户名或密码错误");
        }
        if(user.getStatus()!= UserStatus.ENABLED){
            throw new BusinessException("账号已被停用");
        }
        String token = jwtUtil.generate(user.getId(),user.getUsername(),user.getRole().name());
        return new LoginResult(user,token);
    }

    @Override
    public SysUser currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof SysUser sysUser)) {
            throw new BusinessException("未登录");
        }
        return sysUser;
    }
}
