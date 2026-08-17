# AutoProcess

AutoProcess 是“面向异构业务系统的可扩展智能流程编排与集成平台”的毕业设计工程。当前阶段只搭建基础开发骨架，不内置模拟数据或固定演示流程。

## 顶层目录

```text
AutoPocces/
├─ backend/                        # Spring Boot 多模块后端
├─ frontend/                       # Vue 3 前端工程
├─ docs/                           # 毕业设计文档和开发说明
├─ sql/                            # 数据库建表脚本
├─ scripts/                        # 后续脚本目录
└─ README.md                       # 项目总说明
```

## 关键约定

- 后端接口不使用 `/api` 前缀，例如 `/workflows`、`/connectors`。
- 后端采用 `common/entity/biz/controller/server` 多模块结构。
- `biz` 内部采用 `service` 接口、`service.impl` 实现、`mapper` 接口、`mapper_autoprocess` XML SQL。
- Controller 只调用 Service 接口，ServiceImpl 调用 Mapper 接口，Mapper 接口对应 XML SQL。
- 前端采用 Vue 3 + TypeScript + Element Plus + Vue Flow。
- 当前实现类只作为开发占位，后续业务代码在 dev 环境中继续补充。

## 子目录说明

- 后端目录说明见 [backend/README.md](backend/README.md)
- 前端目录说明见 [frontend/README.md](frontend/README.md)
- 开发结构说明见 [docs/dev/PROJECT_STRUCTURE.md](docs/dev/PROJECT_STRUCTURE.md)
