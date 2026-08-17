# AutoProcess 工程结构说明

本工程围绕“面向异构业务系统的可扩展智能流程编排与集成平台”展开。当前阶段只搭建基础开发骨架，保证后续编码时目录、接口、数据库脚本和文档说明能够对应。

## 后端结构

```text
backend/
├─ pom.xml                         # 后端父工程
├─ autoprocess-common/             # 公共响应、异常、工具
├─ autoprocess-entity/             # 实体、枚举、流程节点模型
├─ autoprocess-biz/                # Service、Impl、Mapper、XML、流程引擎
├─ autoprocess-controller/         # REST 接口、DTO、异常处理
└─ autoprocess-server/             # 启动入口、配置、数据库迁移
```

后端核心调用关系：

```text
Controller -> Service接口 -> ServiceImpl -> Mapper接口 -> Mapper XML -> MySQL
```

## biz 模块细分

```text
autoprocess-biz/src/main/java/com/autoprocess/
├─ service/                        # 业务接口
├─ service/impl/                   # 业务实现占位
├─ mapper/                         # MyBatis Mapper 接口
├─ engine/                         # 流程节点执行器接口和占位实现
└─ engine/impl/                    # 流程引擎实现占位

autoprocess-biz/src/main/resources/
└─ mapper_autoprocess/             # MyBatis XML SQL
```

## 前端结构

```text
frontend/
├─ build/                          # 构建扩展配置
├─ public/                         # 静态资源
├─ tests/                          # 前端测试入口
├─ types/                          # 全局类型声明
└─ src/
   ├─ api/                         # 后端接口封装
   ├─ assets/                      # 样式与资源
   ├─ components/                  # 通用组件与流程画布组件
   ├─ design/                      # 设计规范沉淀
   ├─ directives/                  # 自定义指令预留
   ├─ enums/                       # 前端枚举
   ├─ hooks/                       # 组合式逻辑
   ├─ layouts/                     # 页面布局
   ├─ locales/                     # 国际化预留
   ├─ logics/                      # 业务逻辑封装
   ├─ router/                      # 路由
   ├─ settings/                    # 菜单与系统配置
   ├─ store/                       # 状态管理
   ├─ types/                       # 业务类型
   ├─ utils/                       # 请求封装等工具
   └─ views/                       # 页面
```

## 当前保留的功能入口

| 页面/模块 | 用途 |
|---|---|
| 连接器 | 后续开发连接器配置、动作配置、接口测试。 |
| 流程设计 | 后续开发画布编辑、节点配置、流程发布。 |
| 运行实例 | 后续开发实例列表、节点日志、异常追踪。 |
| 审批中心 | 后续开发人工审批任务。 |
| 事件网关 | 后续开发手动触发、Webhook 接收、事件记录。 |
