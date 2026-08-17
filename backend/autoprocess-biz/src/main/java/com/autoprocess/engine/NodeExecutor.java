package com.autoprocess.engine;

import com.autoprocess.entity.WorkflowNode;

import java.util.Map;

/**
 * 流程节点执行器接口，每一种节点类型通过一个实现类完成自己的执行逻辑。
 */
public interface NodeExecutor {
    boolean supports(String nodeType);

    NodeResult execute(WorkflowNode node, WorkflowRuntime runtime, Map<String, Object> context);
}
