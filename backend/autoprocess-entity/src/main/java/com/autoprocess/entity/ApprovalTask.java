package com.autoprocess.entity;

import com.autoprocess.enums.ApprovalStatus;
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
 * 人工审批任务，由审批节点生成，审批结果会驱动流程继续或终止。
 */
@Entity
@Table(name = "approval_task")
public class ApprovalTask {
    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "instance_id", nullable = false, length = 64)
    private String instanceId;

    @Column(name = "node_id", nullable = false, length = 64)
    private String nodeId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "assignee", nullable = false, length = 100)
    private String assignee;

    /** 审批状态：PENDING 待处理、APPROVED 已通过、REJECTED 已拒绝。 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ApprovalStatus status;

    @Transient
    private Map<String, Object> formData;

    @Lob
    @Column(name = "form_data_json", columnDefinition = "json")
    private String formDataJson;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "handled_at")
    private LocalDateTime handledAt;

    @Column(name = "comment", length = 500)
    private String comment;

    protected ApprovalTask() {
    }

    public ApprovalTask(String id, String instanceId, String nodeId, String title, String assignee, ApprovalStatus status, Map<String, Object> formData, LocalDateTime createdAt, LocalDateTime handledAt, String comment) {
        this.id = id;
        this.instanceId = instanceId;
        this.nodeId = nodeId;
        this.title = title;
        this.assignee = assignee;
        this.status = status;
        this.formData = formData;
        this.formDataJson = null;
        this.createdAt = createdAt;
        this.handledAt = handledAt;
        this.comment = comment;
    }

    public String id() { return id; }
    public String instanceId() { return instanceId; }
    public String nodeId() { return nodeId; }
    public String title() { return title; }
    public String assignee() { return assignee; }
    public ApprovalStatus status() { return status; }
    public Map<String, Object> formData() { return formData; }
    public String formDataJson() { return formDataJson; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime handledAt() { return handledAt; }
    public String comment() { return comment; }

    public String getId() { return id; }
    public String getInstanceId() { return instanceId; }
    public String getNodeId() { return nodeId; }
    public String getTitle() { return title; }
    public String getAssignee() { return assignee; }
    public ApprovalStatus getStatus() { return status; }
    public Map<String, Object> getFormData() { return formData; }
    public String getFormDataJson() { return formDataJson; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getHandledAt() { return handledAt; }
    public String getComment() { return comment; }

    public void setId(String id) { this.id = id; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }
    public void setTitle(String title) { this.title = title; }
    public void setAssignee(String assignee) { this.assignee = assignee; }
    public void setStatus(ApprovalStatus status) { this.status = status; }
    public void setFormData(Map<String, Object> formData) { this.formData = formData; }
    public void setFormDataJson(String formDataJson) { this.formDataJson = formDataJson; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setHandledAt(LocalDateTime handledAt) { this.handledAt = handledAt; }
    public void setComment(String comment) { this.comment = comment; }
}
