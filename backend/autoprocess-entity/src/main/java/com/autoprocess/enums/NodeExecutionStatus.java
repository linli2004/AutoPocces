package com.autoprocess.enums;

/**
 * 节点执行状态，用于记录单个节点在一次流程实例中的执行结果。
 */
public enum NodeExecutionStatus {
    /**
     * 执行成功：节点逻辑已经完成，并产生了可供后续节点使用的输出。
     */
    SUCCESS,

    /**
     * 等待中：节点需要外部动作才能继续，例如等待人工审批。
     */
    WAITING,

    /**
     * 执行失败：节点执行时发生异常，错误原因应写入节点执行日志。
     */
    FAILED
}
