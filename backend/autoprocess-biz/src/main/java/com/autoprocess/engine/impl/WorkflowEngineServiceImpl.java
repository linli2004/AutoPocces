package com.autoprocess.engine.impl;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.engine.WorkflowEngineService;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.mapper.NodeExecutionLogMapper;
import com.autoprocess.mapper.WorkflowInstanceMapper;
import com.autoprocess.service.JsonDataService;
import com.autoprocess.service.WorkflowDefinitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Workflow engine implementation placeholder.
 * The class keeps the dependency location ready for real workflow scheduling.
 */
@Service
public class WorkflowEngineServiceImpl implements WorkflowEngineService {
    private final WorkflowDefinitionService workflowDefinitionService;
    private final WorkflowInstanceMapper workflowInstanceMapper;
    private final NodeExecutionLogMapper nodeExecutionLogMapper;
    private final JsonDataService jsonDataService;

    public WorkflowEngineServiceImpl(WorkflowDefinitionService workflowDefinitionService,
                                     WorkflowInstanceMapper workflowInstanceMapper,
                                     NodeExecutionLogMapper nodeExecutionLogMapper,
                                     JsonDataService jsonDataService) {
        this.workflowDefinitionService = workflowDefinitionService;
        this.workflowInstanceMapper = workflowInstanceMapper;
        this.nodeExecutionLogMapper = nodeExecutionLogMapper;
        this.jsonDataService = jsonDataService;
    }

    @Override
    @Transactional
    public WorkflowInstance start(String workflowId, Map<String, Object> payload) {
        workflowDefinitionService.get(workflowId);
        throw new BusinessException("Workflow engine start is not implemented yet");
    }

    @Override
    @Transactional
    public WorkflowInstance resumeAfterApproval(String instanceId, String approvedNodeId, boolean approved) {
        throw new BusinessException("Workflow engine resume is not implemented yet");
    }
}
