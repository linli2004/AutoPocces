package com.autoprocess.engine;

/**
 * 节点执行时的运行态信息，避免执行器直接依赖完整流程实例。
 */
public record WorkflowRuntime(String instanceId, boolean resumeFromApproval) {
}
