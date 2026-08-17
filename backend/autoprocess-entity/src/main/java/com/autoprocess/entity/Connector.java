package com.autoprocess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * 外部系统连接器，用于描述一个可被平台调用的业务系统或服务。
 */
@Entity
@Table(name = "connector")
public class Connector {
    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** 连接器类型，例如 HTTP、DATABASE、EMAIL、WEBHOOK，用于决定后续由哪类适配器处理。 */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "base_url", length = 255)
    private String baseUrl;

    /** 认证类型，例如 NONE、BASIC、BEARER、API_KEY，用于决定调用外部系统时如何附加凭证。 */
    @Column(name = "auth_type", length = 50)
    private String authType;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Connector() {
    }

    public Connector(String id, String name, String type, String baseUrl, String authType, boolean enabled, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.baseUrl = baseUrl;
        this.authType = authType;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }

    public String id() { return id; }
    public String name() { return name; }
    public String type() { return type; }
    public String baseUrl() { return baseUrl; }
    public String authType() { return authType; }
    public boolean enabled() { return enabled; }
    public LocalDateTime createdAt() { return createdAt; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getAuthType() { return authType; }
    public void setAuthType(String authType) { this.authType = authType; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
