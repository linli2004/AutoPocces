package com.autoprocess.engine;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.entity.WorkflowNode;
import com.autoprocess.service.ConnectorService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Executor placeholder for connector action nodes.
 */
@Component
public class ConnectorActionNodeExecutor implements NodeExecutor {
    private final ConnectorService connectorService;

    public ConnectorActionNodeExecutor(ConnectorService connectorService) {
        this.connectorService = connectorService;
    }

    @Override
    public boolean supports(String nodeType) {
        return "CONNECTOR_ACTION".equals(nodeType);
    }

    @Override
    public NodeResult execute(WorkflowNode node, WorkflowRuntime runtime, Map<String, Object> context) {
        throw new BusinessException("Connector action node execution is not implemented yet");
    }
}
