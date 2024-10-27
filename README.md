# yj-rocketmq-start

`yj-rocketmq-start` 是一个基于 Apache RocketMQ 的 Spring Boot 自动配置库，旨在简化 RocketMQ 的集成和使用。它提供了易于使用的配置和功能，帮助开发者快速上手 RocketMQ。

## 特性

- 简单的配置方式，通过 `application.yml` 进行 RocketMQ 的基本配置。
- 支持生产者和消费者的统一管理。
- 内置监控和日志功能，便于对消息队列的使用情况进行监控。
- 自定义消息发送和接收的处理逻辑，满足不同的业务需求。
- 支持自动重试和消息延迟投递。

## 依赖

在你的 `pom.xml` 文件中添加以下依赖：

```xml
<dependency>
    <groupId>com.mumu</groupId>
    <artifactId>yj-rocketmq-start</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
