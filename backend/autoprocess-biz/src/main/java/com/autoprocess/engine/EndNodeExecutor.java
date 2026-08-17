package com.autoprocess.engine;

import com.autoprocess.entity.WorkflowNode;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 结束节点执行器，用于标记流程运行链路已经到达终点。
 */
@Component
public class EndNodeExecutor implements NodeExecutor {
    @Override
    public boolean supports(String nodeType) {
        return "END".equals(nodeType);
    }

    @Override
    public NodeResult execute(WorkflowNode node, WorkflowRuntime runtime, Map<String, Object> context) {
        return NodeResult.next(Map.of("completed", true));
    }
}
