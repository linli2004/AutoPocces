# AutoProcess Backend

后端采用 Maven 多模块结构，参考 `12345-dev` 的组织方式：实体单独成模块，业务模块内部再分 `service`、`service.impl`、`mapper` 和 XML 映射文件。

当前工程只搭建基础开发骨架，后续业务代码直接在 dev 环境中继续开发。

## 目录结构

```text
backend/
├─ pom.xml                         # 后端父工程，统一版本和模块声明
├─ autoprocess-common/             # 公共响应、业务异常、ID 工具
├─ autoprocess-entity/             # 实体、枚举、流程节点模型
├─ autoprocess-biz/                # 业务接口、实现类、流程引擎、Mapper、XML SQL
├─ autoprocess-controller/         # REST Controller、请求 DTO、全局异常处理
└─ autoprocess-server/             # 启动入口、运行配置、数据库迁移脚本
```

## 后端调用链路

```text
Controller
  -> Service 接口
  -> ServiceImpl 实现类
  -> Mapper 接口
  -> mapper_autoprocess/*.xml
  -> MySQL dev 数据库
```

## biz 模块内部约定

```text
autoprocess-biz/src/main/java/com/autoprocess/
├─ service/                        # 只放业务接口
├─ service/impl/                   # 放接口实现类
├─ mapper/                         # MyBatis Mapper 接口
├─ engine/                         # 流程节点执行器接口和节点执行器
└─ engine/impl/                    # 流程引擎实现类

autoprocess-biz/src/main/resources/
└─ mapper_autoprocess/             # Mapper XML，SQL 统一放这里
```

## 关键约定

- 接口路径不使用 `/api` 前缀，例如 `/workflows`、`/connectors`。
- Controller 只依赖 Service 接口。
- ServiceImpl 才能调用 Mapper 或其他 Service 接口。
- Mapper 接口放在 `autoprocess-biz`，SQL 放在 `mapper_autoprocess/*.xml`。
- 当前实现类只保留基础占位，具体业务逻辑由后续开发补充。

## 构建

```bash
mvn -q -DskipTests package
```
