package com.autoprocess.enums;

/**
 * 用户角色，用于控制平台功能的访问权限。
 */
public enum UserRole {
    /**
     * 管理员：可管理用户、连接器、流程定义和运行实例。
     */
    ADMIN,

    /**
     * 操作员：可设计流程、配置连接器和动作并触发运行。
     */
    OPERATOR,

    /**
     * 只读用户：只能查看运行实例、节点日志和审批任务。
     */
    VIEWER
}
