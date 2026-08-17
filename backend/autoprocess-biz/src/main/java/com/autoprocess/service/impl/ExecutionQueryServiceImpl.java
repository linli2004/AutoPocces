package com.autoprocess.service.impl;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.entity.NodeExecutionLog;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.mapper.NodeExecutionLogMapper;
import com.autoprocess.mapper.WorkflowInstanceMapper;
import com.autoprocess.service.ExecutionQueryService;
import com.autoprocess.service.JsonDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Default runtime query service implementation backed by MyBatis.
 */
@Service
public class ExecutionQueryServiceImpl implements ExecutionQueryService {
    private final WorkflowInstanceMapper workflowInstanceMapper;
    private final NodeExecutionLogMapper nodeExecutionLogMapper;
    private final JsonDataService jsonDataService;

    public ExecutionQueryServiceImpl(WorkflowInstanceMapper workflowInstanceMapper, NodeExecutionLogMapper nodeExecutionLogMapper, JsonDataService jsonDataService) {
        this.workflowInstanceMapper = workflowInstanceMapper;
        this.nodeExecutionLogMapper = nodeExecutionLogMapper;
        this.jsonDataService = jsonDataService;
    }

    @Override
    public List<WorkflowInstance> listInstances() {
        List<WorkflowInstance> instances = workflowInstanceMapper.selectList();
        instances.forEach(jsonDataService::parseWorkflowInstanceJson);
        return instances;
    }

    @Override
    public WorkflowInstance getInstance(String id) {
        WorkflowInstance instance = workflowInstanceMapper.selectById(id);
        if (instance == null) {
            throw new BusinessException("Workflow instance not found: " + id);
        }
        jsonDataService.parseWorkflowInstanceJson(instance);
        return instance;
    }

    @Override
    public List<NodeExecutionLog> listLogs(String instanceId) {
        List<NodeExecutionLog> logs = nodeExecutionLogMapper.selectByInstanceId(instanceId);
        logs.forEach(jsonDataService::parseNodeLogJson);
        return logs;
    }
}
