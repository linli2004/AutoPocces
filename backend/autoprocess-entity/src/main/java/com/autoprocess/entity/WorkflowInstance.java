package com.autoprocess.entity;

import com.autoprocess.enums.InstanceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 流程实例，表示某次业务事件触发后的运行状态和上下文。
 */
@Entity
@Table(name = "workflow_instance")
public class WorkflowInstance {
    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "workflow_id", nullable = false, length = 64)
    private String workflowId;

    /** 实例运行状态：RUNNING 运行中、WAITING_APPROVAL 等待审批、COMPLETED 完成、FAILED 失败、REJECTED 拒绝。 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private InstanceStatus status;

    @Transient
    private Map<String, Object> context;

    @Lob
    @Column(name = "context_json", columnDefinition = "json")
    private String contextJson;

    @Column(name = "current_node_id", length = 64)
    private String currentNodeId;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    protected WorkflowInstance() {
    }

    public WorkflowInstance(String id, String workflowId, InstanceStatus status, Map<String, Object> context, String currentNodeId, LocalDateTime startedAt, LocalDateTime finishedAt, String errorMessage) {
        this.id = id;
        this.workflowId = workflowId;
        this.status = status;
        this.context = context;
        this.contextJson = null;
        this.currentNodeId = currentNodeId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.errorMessage = errorMessage;
    }

    public String id() { return id; }
    public String workflowId() { return workflowId; }
    public InstanceStatus status() { return status; }
    public Map<String, Object> context() { return context; }
    public String contextJson() { return contextJson; }
    public String currentNodeId() { return currentNodeId; }
    public LocalDateTime startedAt() { return startedAt; }
    public LocalDateTime finishedAt() { return finishedAt; }
    public String errorMessage() { return errorMessage; }

    public String getId() { return id; }
    public String getWorkflowId() { return workflowId; }
    public InstanceStatus getStatus() { return status; }
    public Map<String, Object> getContext() { return context; }
    public String getContextJson() { return contextJson; }
    public String getCurrentNodeId() { return currentNodeId; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getFinishedAt() { return finishedAt; }
    public String getErrorMessage() { return errorMessage; }

    public void setId(String id) { this.id = id; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }
    public void setStatus(InstanceStatus status) { this.status = status; }
    public void setContext(Map<String, Object> context) { this.context = context; }
    public void setContextJson(String contextJson) { this.contextJson = contextJson; }
    public void setCurrentNodeId(String currentNodeId) { this.currentNodeId = currentNodeId; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public void setFinishedAt(LocalDateTime finishedAt) { this.finishedAt = finishedAt; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
