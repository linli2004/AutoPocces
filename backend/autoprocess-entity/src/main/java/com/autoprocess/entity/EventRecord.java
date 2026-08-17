package com.autoprocess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 事件接收记录，用于追踪手动触发或 Webhook 进入平台后的处理情况。
 */
@Entity
@Table(name = "event_record")
public class EventRecord {
    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "workflow_id", nullable = false, length = 64)
    private String workflowId;

    @Column(name = "event_key", nullable = false, length = 100)
    private String eventKey;

    @Transient
    private Map<String, Object> payload;

    @Lob
    @Column(name = "payload_json", columnDefinition = "json")
    private String payloadJson;

    /** 事件处理状态，建议后续约定 RECEIVED、PROCESSING、SUCCESS、FAILED 等值。 */
    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected EventRecord() {
    }

    public EventRecord(String id, String workflowId, String eventKey, Map<String, Object> payload, String status, LocalDateTime createdAt) {
        this.id = id;
        this.workflowId = workflowId;
        this.eventKey = eventKey;
        this.payload = payload;
        this.payloadJson = null;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String id() { return id; }
    public String workflowId() { return workflowId; }
    public String eventKey() { return eventKey; }
    public Map<String, Object> payload() { return payload; }
    public String payloadJson() { return payloadJson; }
    public String status() { return status; }
    public LocalDateTime createdAt() { return createdAt; }

    public String getId() { return id; }
    public String getWorkflowId() { return workflowId; }
    public String getEventKey() { return eventKey; }
    public Map<String, Object> getPayload() { return payload; }
    public String getPayloadJson() { return payloadJson; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(String id) { this.id = id; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }
    public void setEventKey(String eventKey) { this.eventKey = eventKey; }
    public void setPayload(Map<String, Object> payload) { this.payload = payload; }
    public void setPayloadJson(String payloadJson) { this.payloadJson = payloadJson; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
