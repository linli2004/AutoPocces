create table sys_user
(
    id            varchar(64)  not null comment 'ID'
        primary key,
    username      varchar(64)  not null comment '登录账号',
    password_hash varchar(255) not null comment '密码密文',
    display_name  varchar(64)  not null comment '显示名称',
    role          varchar(32)  not null comment '用户角色',
    status        varchar(32)  not null comment '用户状态',
    created_at    datetime     not null comment '创建时间',
    updated_at    datetime     not null comment '更新时间',
    constraint uk_sys_user_username
        unique (username),
    index idx_sys_user_role (role),
    index idx_sys_user_status (status)
)
    comment '系统用户' row_format = DYNAMIC;

create table edge_agent
(
    id                varchar(64)  not null comment 'ID'
        primary key,
    name              varchar(128) not null comment '代理名称',
    agent_key         varchar(128) not null comment '代理接入标识',
    status            varchar(32)  not null comment '代理状态',
    config_json       json         null comment '代理配置JSON',
    last_heartbeat_at datetime     null comment '最后心跳时间',
    created_at        datetime     not null comment '创建时间',
    updated_at        datetime     not null comment '更新时间',
    constraint uk_edge_agent_key
        unique (agent_key),
    index idx_edge_agent_status (status),
    index idx_edge_agent_last_heartbeat (last_heartbeat_at)
)
    comment '企业内网执行代理' row_format = DYNAMIC;

create table connector
(
    id         varchar(64)           not null comment 'ID'
        primary key,
    name       varchar(100)          not null comment '连接器名称',
    type       varchar(50)           not null comment '连接器类型;HTTP、EMAIL、DATABASE等',
    base_url   varchar(255)          null comment '服务基础地址',
    auth_type  varchar(50)           null comment '认证方式',
    enabled    tinyint(1) default 1  not null comment '是否启用;0-否 1-是',
    created_at datetime              null comment '创建时间',
    index idx_connector_type (type),
    index idx_connector_enabled (enabled)
)
    comment '外部业务系统连接器' row_format = DYNAMIC;

create table connector_action
(
    id            varchar(64)  not null comment 'ID'
        primary key,
    connector_id  varchar(64)  not null comment '所属连接器ID',
    name          varchar(100) not null comment '动作名称',
    action_key    varchar(100) not null comment '动作标识;流程节点通过该标识调用',
    method        varchar(20)  not null comment '请求方法或操作方法',
    path          varchar(255) not null comment '请求路径',
    input_schema  json         null comment '输入参数结构',
    output_schema json         null comment '输出结果结构',
    constraint uk_connector_action_key
        unique (connector_id, action_key),
    constraint fk_connector_action_connector
        foreign key (connector_id) references connector (id),
    index idx_connector_action_connector (connector_id)
)
    comment '连接器可执行动作' row_format = DYNAMIC;

create table workflow_definition
(
    id              varchar(64)            not null comment 'ID'
        primary key,
    workflow_key    varchar(100)           not null comment '流程标识;用于Webhook触发',
    name            varchar(120)           not null comment '流程名称',
    description     varchar(500)           null comment '流程描述',
    status          varchar(30)            not null comment '流程状态;DRAFT、PUBLISHED、DISABLED',
    version         int         default 1  not null comment '流程版本号',
    definition_json json                   not null comment '流程定义JSON;包含节点、连线和配置',
    created_at      datetime               null comment '创建时间',
    updated_at      datetime               null comment '更新时间',
    constraint uk_workflow_definition_key
        unique (workflow_key),
    index idx_workflow_definition_status (status)
)
    comment '流程定义' row_format = DYNAMIC;

create table workflow_instance
(
    id              varchar(64)   not null comment 'ID'
        primary key,
    workflow_id     varchar(64)   not null comment '流程定义ID',
    status          varchar(30)   not null comment '实例状态;RUNNING、WAITING_APPROVAL、COMPLETED等',
    context_json    json          null comment '运行上下文JSON',
    current_node_id varchar(64)   null comment '当前节点ID',
    started_at      datetime      null comment '开始时间',
    finished_at     datetime      null comment '结束时间',
    error_message   varchar(1000) null comment '错误信息',
    constraint fk_workflow_instance_workflow
        foreign key (workflow_id) references workflow_definition (id),
    index idx_workflow_instance_workflow (workflow_id),
    index idx_workflow_instance_status (status),
    index idx_workflow_instance_started_at (started_at)
)
    comment '流程运行实例' row_format = DYNAMIC;

create table node_execution_log
(
    id            varchar(64)   not null comment 'ID'
        primary key,
    instance_id   varchar(64)   not null comment '流程实例ID',
    node_id       varchar(64)   not null comment '节点ID',
    node_name     varchar(120)  null comment '节点名称',
    status        varchar(30)   not null comment '执行状态;SUCCESS、WAITING、FAILED',
    input_json    json          null comment '节点输入JSON',
    output_json   json          null comment '节点输出JSON',
    error_message varchar(1000) null comment '错误信息',
    started_at    datetime      null comment '开始时间',
    finished_at   datetime      null comment '结束时间',
    constraint fk_node_log_instance
        foreign key (instance_id) references workflow_instance (id),
    index idx_node_log_instance (instance_id),
    index idx_node_log_status (status)
)
    comment '节点执行日志' row_format = DYNAMIC;

create table approval_task
(
    id             varchar(64)  not null comment 'ID'
        primary key,
    instance_id    varchar(64)  not null comment '流程实例ID',
    node_id        varchar(64)  not null comment '审批节点ID',
    title          varchar(200) not null comment '审批标题',
    assignee       varchar(100) not null comment '审批人或审批角色',
    status         varchar(30)  not null comment '审批状态;PENDING、APPROVED、REJECTED',
    form_data_json json         null comment '审批展示数据JSON',
    created_at     datetime     null comment '创建时间',
    handled_at     datetime     null comment '处理时间',
    comment        varchar(500) null comment '审批意见',
    constraint fk_approval_instance
        foreign key (instance_id) references workflow_instance (id),
    index idx_approval_instance (instance_id),
    index idx_approval_assignee_status (assignee, status),
    index idx_approval_status (status)
)
    comment '人工审批任务' row_format = DYNAMIC;

create table event_record
(
    id           varchar(64)  not null comment 'ID'
        primary key,
    workflow_id  varchar(64)  not null comment '流程定义ID',
    event_key    varchar(100) not null comment '事件标识或Webhook标识',
    payload_json json         not null comment '事件载荷JSON',
    status       varchar(30)  not null comment '事件处理状态',
    created_at   datetime     null comment '接收时间',
    constraint fk_event_workflow
        foreign key (workflow_id) references workflow_definition (id),
    index idx_event_workflow (workflow_id),
    index idx_event_key (event_key),
    index idx_event_status (status)
)
    comment '业务事件记录' row_format = DYNAMIC;

create table ai_assist_log
(
    id          varchar(64) not null comment 'ID'
        primary key,
    user_id     varchar(64) null comment '用户ID',
    instance_id varchar(64) null comment '流程实例ID',
    assist_type varchar(64) not null comment 'AI辅助类型',
    prompt_json json        null comment '提示词JSON',
    result_json json        null comment '结果JSON',
    created_at  datetime    not null comment '创建时间',
    constraint fk_ai_assist_user
        foreign key (user_id) references sys_user (id),
    constraint fk_ai_assist_instance
        foreign key (instance_id) references workflow_instance (id),
    index idx_ai_assist_instance (instance_id),
    index idx_ai_assist_type (assist_type)
)
    comment 'AI辅助记录' row_format = DYNAMIC;

