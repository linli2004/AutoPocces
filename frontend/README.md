# AutoProcess Frontend

前端采用 Vue 3、TypeScript、Element Plus 和 Vue Flow。当前只保留基础工程结构和页面入口，后续开发时直接接入真实 dev 环境接口。

## 目录结构

```text
frontend/
├─ build/                          # 构建扩展说明和后续 Vite 配置拆分位置
├─ public/                         # 静态资源
├─ tests/                          # 前端测试入口
├─ types/                          # 全局 TypeScript 类型声明
└─ src/
   ├─ api/                         # 后端接口封装，统一调用 request
   ├─ assets/                      # 全局样式和静态资源
   ├─ components/                  # 可复用组件，目前包含流程画布
   ├─ design/                      # 设计规范沉淀目录
   ├─ directives/                  # 自定义指令预留
   ├─ enums/                       # 前端枚举预留
   ├─ hooks/                       # 组合式逻辑预留
   ├─ layouts/                     # 页面整体布局
   ├─ locales/                     # 国际化预留
   ├─ logics/                      # 复杂业务逻辑封装预留
   ├─ router/                      # 页面路由
   ├─ settings/                    # 菜单和系统设置
   ├─ store/                       # Pinia 状态管理预留
   ├─ types/                       # 业务类型定义
   ├─ utils/                       # 请求封装等工具
   └─ views/                       # 业务页面
```

## 页面说明

| 页面 | 作用 |
|---|---|
| 运行总览 | 统计入口占位，后续接入真实运行统计。 |
| 连接器 | 连接器和动作配置入口。 |
| 流程设计 | 流程画布、节点配置和发布入口。 |
| 运行实例 | 流程实例与节点日志查询入口。 |
| 审批中心 | 人工审批任务处理入口。 |

## 接口约定

前端请求路径不使用 `/api` 前缀，开发环境通过 Vite 代理转发到后端 `http://localhost:8080`。

## 构建

```bash
npm install
npm run build
```
