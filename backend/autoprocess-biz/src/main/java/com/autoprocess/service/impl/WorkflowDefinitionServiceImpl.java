package com.autoprocess.service.impl;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.common.util.IdUtil;
import com.autoprocess.entity.WorkflowDefinition;
import com.autoprocess.enums.WorkflowStatus;
import com.autoprocess.mapper.WorkflowDefinitionMapper;
import com.autoprocess.service.JsonDataService;
import com.autoprocess.service.WorkflowDefinitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Default workflow definition service implementation backed by MyBatis.
 */
@Service
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {
    private final WorkflowDefinitionMapper workflowDefinitionMapper;
    private final JsonDataService jsonDataService;

    public WorkflowDefinitionServiceImpl(WorkflowDefinitionMapper workflowDefinitionMapper, JsonDataService jsonDataService) {
        this.workflowDefinitionMapper = workflowDefinitionMapper;
        this.jsonDataService = jsonDataService;
    }

    @Override
    public List<WorkflowDefinition> list() {
        List<WorkflowDefinition> workflows = workflowDefinitionMapper.selectList();
        workflows.forEach(jsonDataService::parseWorkflowDefinitionJson);
        return workflows;
    }

    @Override
    public WorkflowDefinition get(String id) {
        WorkflowDefinition workflow = workflowDefinitionMapper.selectById(id);
        if (workflow == null) {
            throw new BusinessException("Workflow not found: " + id);
        }
        jsonDataService.parseWorkflowDefinitionJson(workflow);
        return workflow;
    }

    @Override
    public WorkflowDefinition getByWorkflowKey(String workflowKey) {
        WorkflowDefinition workflow = workflowDefinitionMapper.selectByWorkflowKey(workflowKey);
        if (workflow == null) {
            throw new BusinessException("Workflow key not found: " + workflowKey);
        }
        jsonDataService.parseWorkflowDefinitionJson(workflow);
        return workflow;
    }

    @Override
    @Transactional
    public WorkflowDefinition save(WorkflowDefinition workflow) {
        LocalDateTime now = LocalDateTime.now();
        boolean creating = workflow.id() == null || workflow.id().isBlank() || workflowDefinitionMapper.selectById(workflow.id()) == null;

        if (workflow.id() == null || workflow.id().isBlank()) {
            workflow.setId(IdUtil.nextId());
        }
        if (workflow.status() == null) {
            workflow.setStatus(WorkflowStatus.DRAFT);
        }
        if (workflow.version() <= 0) {
            workflow.setVersion(1);
        }
        if (workflow.createdAt() == null) {
            workflow.setCreatedAt(now);
        }
        workflow.setUpdatedAt(now);
        jsonDataService.fillWorkflowDefinitionJson(workflow);

        if (creating) {
            workflowDefinitionMapper.insert(workflow);
        } else {
            workflowDefinitionMapper.update(workflow);
        }
        jsonDataService.parseWorkflowDefinitionJson(workflow);
        return workflow;
    }

    @Override
    @Transactional
    public WorkflowDefinition publish(String id) {
        WorkflowDefinition workflow = get(id);
        workflow.setStatus(WorkflowStatus.PUBLISHED);
        workflow.setUpdatedAt(LocalDateTime.now());
        workflowDefinitionMapper.updateStatus(workflow);
        return get(id);
    }
}
