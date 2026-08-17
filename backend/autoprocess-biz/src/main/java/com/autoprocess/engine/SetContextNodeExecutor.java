package com.autoprocess.engine;

import com.autoprocess.entity.WorkflowNode;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Executor placeholder for setting workflow context values.
 */
@Component
public class SetContextNodeExecutor implements NodeExecutor {
    @Override
    public boolean supports(String nodeType) {
        return "SET_CONTEXT".equals(nodeType);
    }

    @Override
    public NodeResult execute(WorkflowNode node, WorkflowRuntime runtime, Map<String, Object> context) {
        return NodeResult.next(Map.copyOf(node.config()));
    }
}
