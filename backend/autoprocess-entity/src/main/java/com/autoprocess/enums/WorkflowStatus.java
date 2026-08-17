package com.autoprocess.enums;

/**
 * 流程定义状态，用于控制流程能否被发布、修改或触发运行。
 */
public enum WorkflowStatus {
    /**
     * 草稿状态：流程还在设计中，可以继续编辑，不能作为正式流程触发运行。
     */
    DRAFT,

    /**
     * 已发布状态：流程定义已经确认，可以被手动事件、Webhook 等方式触发运行。
     */
    PUBLISHED,

    /**
     * 已停用状态：流程保留历史定义和运行记录，但不允许继续触发新的实例。
     */
    DISABLED
}
