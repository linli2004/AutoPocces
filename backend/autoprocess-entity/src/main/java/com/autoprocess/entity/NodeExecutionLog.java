package com.autoprocess.entity;

import com.autoprocess.enums.NodeExecutionStatus;
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
 * 节点执行日志，记录每个节点的输入、输出、状态和错误信息。
 */
@Entity
@Table(name = "node_execution_log")
public class NodeExecutionLog {
    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "instance_id", nullable = false, length = 64)
    private String instanceId;

    @Column(name = "node_id", nullable = false, length = 64)
    private String nodeId;

    @Column(name = "node_name", length = 120)
    private String nodeName;

    /** 节点执行状态：SUCCESS 成功、WAITING 等待外部处理、FAILED 执行失败。 */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private NodeExecutionStatus status;

    @Transient
    private Map<String, Object> input;

    @Lob
    @Column(name = "input_json", columnDefinition = "json")
    private String inputJson;

    @Transient
    private Map<String, Object> output;

    @Lob
    @Column(name = "output_json", columnDefinition = "json")
    private String outputJson;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    protected NodeExecutionLog() {
    }

    public NodeExecutionLog(String id, String instanceId, String nodeId, String nodeName, NodeExecutionStatus status, Map<String, Object> input, Map<String, Object> output, String errorMessage, LocalDateTime startedAt, LocalDateTime finishedAt) {
        this.id = id;
        this.instanceId = instanceId;
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.status = status;
        this.input = input;
        this.inputJson = null;
        this.output = output;
        this.outputJson = null;
        this.errorMessage = errorMessage;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public String id() { return id; }
    public String instanceId() { return instanceId; }
    public String nodeId() { return nodeId; }
    public String nodeName() { return nodeName; }
    public NodeExecutionStatus status() { return status; }
    public Map<String, Object> input() { return input; }
    public String inputJson() { return inputJson; }
    public Map<String, Object> output() { return output; }
    public String outputJson() { return outputJson; }
    public String errorMessage() { return errorMessage; }
    public LocalDateTime startedAt() { return startedAt; }
    public LocalDateTime finishedAt() { return finishedAt; }

    public String getId() { return id; }
    public String getInstanceId() { return instanceId; }
    public String getNodeId() { return nodeId; }
    public String getNodeName() { return nodeName; }
    public NodeExecutionStatus getStatus() { return status; }
    public Map<String, Object> getInput() { return input; }
    public String getInputJson() { return inputJson; }
    public Map<String, Object> getOutput() { return output; }
    public String getOutputJson() { return outputJson; }
    public String getErrorMessage() { return errorMessage; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getFinishedAt() { return finishedAt; }

    public void setId(String id) { this.id = id; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }
    public void setNodeName(String nodeName) { this.nodeName = nodeName; }
    public void setStatus(NodeExecutionStatus status) { this.status = status; }
    public void setInput(Map<String, Object> input) { this.input = input; }
    public void setInputJson(String inputJson) { this.inputJson = inputJson; }
    public void setOutput(Map<String, Object> output) { this.output = output; }
    public void setOutputJson(String outputJson) { this.outputJson = outputJson; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public void setFinishedAt(LocalDateTime finishedAt) { this.finishedAt = finishedAt; }
}
