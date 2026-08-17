package com.autoprocess.engine;

import com.autoprocess.entity.WorkflowNode;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 开始事件节点执行器，用于把外部事件正式转成流程运行。
 */
@Component
public class StartNodeExecutor implements NodeExecutor {
    @Override
    public boolean supports(String nodeType) {
        return "START_EVENT".equals(nodeType);
    }

    @Override
    public NodeResult execute(WorkflowNode node, WorkflowRuntime runtime, Map<String, Object> context) {
        return NodeResult.next(Map.of("started", true, "eventKey", node.config().getOrDefault("eventKey", "")));
    }
}
