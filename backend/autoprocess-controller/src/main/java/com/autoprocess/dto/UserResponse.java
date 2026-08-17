package com.autoprocess.dto;

import com.autoprocess.entity.SysUser;
import com.autoprocess.enums.UserRole;
import com.autoprocess.enums.UserStatus;

/**
 * 返回给前端的用户信息。
 *
 * 不直接返回 passwordHash。
 */
public record UserResponse(
        String id,
        String username,
        String displayName,
        UserRole role,
        UserStatus status
) {

    public static UserResponse from(SysUser user) {

        if (user == null) {
            return null;
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getRole(),
                user.getStatus()
        );
    }
}