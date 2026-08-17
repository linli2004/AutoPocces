package com.autoprocess.service.impl;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.engine.WorkflowEngineService;
import com.autoprocess.entity.ApprovalTask;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.enums.ApprovalStatus;
import com.autoprocess.mapper.ApprovalTaskMapper;
import com.autoprocess.service.ApprovalService;
import com.autoprocess.service.JsonDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Default approval service implementation backed by mapper data.
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {
    private final ApprovalTaskMapper approvalTaskMapper;
    private final WorkflowEngineService workflowEngineService;
    private final JsonDataService jsonDataService;

    public ApprovalServiceImpl(ApprovalTaskMapper approvalTaskMapper, WorkflowEngineService workflowEngineService, JsonDataService jsonDataService) {
        this.approvalTaskMapper = approvalTaskMapper;
        this.workflowEngineService = workflowEngineService;
        this.jsonDataService = jsonDataService;
    }

    @Override
    public List<ApprovalTask> list(ApprovalStatus status) {
        List<ApprovalTask> tasks = status == null ? approvalTaskMapper.selectList() : approvalTaskMapper.selectByStatus(status.name());
        tasks.forEach(jsonDataService::parseApprovalTaskJson);
        return tasks;
    }

    @Override
    public ApprovalTask get(String id) {
        ApprovalTask task = approvalTaskMapper.selectById(id);
        if (task == null) {
            throw new BusinessException("Approval task not found: " + id);
        }
        jsonDataService.parseApprovalTaskJson(task);
        return task;
    }

    @Override
    @Transactional
    public WorkflowInstance approve(String id, String comment) {
        ApprovalTask task = get(id);
        ensurePending(task);
        task.setStatus(ApprovalStatus.APPROVED);
        task.setHandledAt(LocalDateTime.now());
        task.setComment(comment);
        approvalTaskMapper.updateHandleResult(task);
        return workflowEngineService.resumeAfterApproval(task.instanceId(), task.nodeId(), true);
    }

    @Override
    @Transactional
    public WorkflowInstance reject(String id, String comment) {
        ApprovalTask task = get(id);
        ensurePending(task);
        task.setStatus(ApprovalStatus.REJECTED);
        task.setHandledAt(LocalDateTime.now());
        task.setComment(comment);
        approvalTaskMapper.updateHandleResult(task);
        return workflowEngineService.resumeAfterApproval(task.instanceId(), task.nodeId(), false);
    }

    private void ensurePending(ApprovalTask task) {
        if (task.status() != ApprovalStatus.PENDING) {
            throw new BusinessException("Approval task has already been handled");
        }
    }
}
