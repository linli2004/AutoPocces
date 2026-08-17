package com.autoprocess.engine;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.entity.WorkflowNode;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Executor placeholder for condition branch nodes.
 */
@Component
public class IfNodeExecutor implements NodeExecutor {
    @Override
    public boolean supports(String nodeType) {
        return "IF".equals(nodeType);
    }

    @Override
    public NodeResult execute(WorkflowNode node, WorkflowRuntime runtime, Map<String, Object> context) {
        throw new BusinessException("Condition node execution is not implemented yet");
    }
}
