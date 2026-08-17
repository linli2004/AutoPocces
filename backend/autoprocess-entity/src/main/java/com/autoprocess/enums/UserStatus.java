package com.autoprocess.enums;

/**
 * 用户状态，用于控制账号能否登录和使用平台。
 */
public enum UserStatus {
    /**
     * 启用：账号可以正常登录。
     */
    ENABLED,

    /**
     * 停用：账号禁止登录，保留历史数据。
     */
    DISABLED
}
