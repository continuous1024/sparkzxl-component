# sparksys-boot-starter
> 职能：
> 聚合开发脚手架的基本功能，包含spring boot基本环境，工具类，database，日志，API接口文档，web层以及高性能的http调用框架retrofit

## POM

```xml
<dependencies>
    <dependency>
        <groupId>com.sparksys</groupId>
        <artifactId>sparksys-core</artifactId>
    </dependency>
    <dependency>
        <groupId>com.sparksys</groupId>
        <artifactId>sparksys-database-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>com.sparksys</groupId>
        <artifactId>sparksys-log-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>com.sparksys</groupId>
        <artifactId>sparksys-swagger-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>com.sparksys</groupId>
        <artifactId>sparksys-web-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>com.github.lianjiatech</groupId>
        <artifactId>retrofit-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

## 功能
- 聚合企业级开发脚手架基本的组件
- 启动后的初始化操作，例如，操作系统，依赖环境，访问地址，接口文档地址等
- EnableSpringUtil注解的自动装载

## 更多
> 减少搭建框架的时间，让开发人员专注于业务系统的开发