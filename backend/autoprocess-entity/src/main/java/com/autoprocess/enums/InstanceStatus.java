package com.autoprocess.enums;

/**
 * 流程实例运行状态，用于运行监控、审批恢复和异常追踪。
 */
public enum InstanceStatus {
    /**
     * 运行中：流程实例正在执行节点，或者等待下一步调度。
     */
    RUNNING,

    /**
     * 等待审批：流程执行到人工审批节点，实例暂停，等待审批任务处理。
     */
    WAITING_APPROVAL,

    /**
     * 已完成：流程已经执行到结束节点或没有后续节点。
     */
    COMPLETED,

    /**
     * 执行失败：流程执行过程中发生异常，需要查看节点日志定位原因。
     */
    FAILED,

    /**
     * 已拒绝：人工审批拒绝后，流程实例被终止。
     */
    REJECTED
}
