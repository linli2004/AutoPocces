package com.autoprocess.enums;

/**
 * 审批任务状态，用于区分待办任务是否已经被处理。
 */
public enum ApprovalStatus {
    /**
     * 待审批：审批任务已生成，但审批人尚未处理。
     */
    PENDING,

    /**
     * 已通过：审批人同意，流程可以继续向后执行。
     */
    APPROVED,

    /**
     * 已拒绝：审批人拒绝，流程通常进入拒绝或终止状态。
     */
    REJECTED
}
