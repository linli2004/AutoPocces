package com.autoprocess.entity;

import com.autoprocess.enums.WorkflowStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 流程定义，保存画布结构、发布状态和版本，是流程实例运行时读取的模板。
 */
@Entity
@Table(name = "workflow_definition")
public class WorkflowDefinition {
    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "workflow_key", nullable = false, length = 100)
    private String workflowKey;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    /** 流程定义状态：DRAFT 草稿、PUBLISHED 已发布、DISABLED 已停用。 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private WorkflowStatus status;

    @Column(name = "version", nullable = false)
    private int version;

    @Transient
    private List<WorkflowNode> nodes;

    @Transient
    private List<WorkflowEdge> edges;

    @Lob
    @Column(name = "definition_json", columnDefinition = "json")
    private String definitionJson;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected WorkflowDefinition() {
    }

    public WorkflowDefinition(String id, String workflowKey, String name, String description, WorkflowStatus status, int version, List<WorkflowNode> nodes, List<WorkflowEdge> edges, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.workflowKey = workflowKey;
        this.name = name;
        this.description = description;
        this.status = status;
        this.version = version;
        this.nodes = nodes;
        this.edges = edges;
        this.definitionJson = null;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String id() { return id; }
    public String workflowKey() { return workflowKey; }
    public String name() { return name; }
    public String description() { return description; }
    public WorkflowStatus status() { return status; }
    public int version() { return version; }
    public List<WorkflowNode> nodes() { return nodes; }
    public List<WorkflowEdge> edges() { return edges; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
    public String definitionJson() { return definitionJson; }

    public String getId() { return id; }
    public String getWorkflowKey() { return workflowKey; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public WorkflowStatus getStatus() { return status; }
    public int getVersion() { return version; }
    public List<WorkflowNode> getNodes() { return nodes; }
    public List<WorkflowEdge> getEdges() { return edges; }
    public String getDefinitionJson() { return definitionJson; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(String id) { this.id = id; }
    public void setWorkflowKey(String workflowKey) { this.workflowKey = workflowKey; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(WorkflowStatus status) { this.status = status; }
    public void setVersion(int version) { this.version = version; }
    public void setNodes(List<WorkflowNode> nodes) { this.nodes = nodes; }
    public void setEdges(List<WorkflowEdge> edges) { this.edges = edges; }
    public void setDefinitionJson(String definitionJson) { this.definitionJson = definitionJson; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
