package com.autoprocess.engine;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.entity.WorkflowNode;
import com.autoprocess.service.ConnectorService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Executor placeholder for email notification nodes.
 */
@Component
public class EmailNodeExecutor implements NodeExecutor {
    private final ConnectorService connectorService;

    public EmailNodeExecutor(ConnectorService connectorService) {
        this.connectorService = connectorService;
    }

    @Override
    public boolean supports(String nodeType) {
        return "EMAIL".equals(nodeType);
    }

    @Override
    public NodeResult execute(WorkflowNode node, WorkflowRuntime runtime, Map<String, Object> context) {
        throw new BusinessException("Email node execution is not implemented yet");
    }
}
