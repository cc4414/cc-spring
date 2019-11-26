## 简介
cc-spring是一套基于spring的快速开发框架。它提供了一系列可插拔的模块，你可以利用它快速构建一个单体应用或者一个微服务应用。

cc-spring的各个模块分别整合了spring boot、spring cloud alibaba、mybatis plus、hutool等优秀的开源框架。
## 文档
[详细文档](https://cc4414.gitee.io/cc-spring-doc)
## 快速开始
该框架基于spring boot，所以在一个新建的spring boot工程里面进行如下操作

1. 将父工程改为

``` xml
<parent>
    <groupId>cc.cc4414</groupId>
    <artifactId>cc-spring-boot-starter-parent</artifactId>
    <version>0.5.0</version>
</parent>
```

2. 引入依赖

``` xml
<dependency>
    <groupId>cc.cc4414</groupId>
    <artifactId>cc-spring-web-starter</artifactId>
</dependency>
```
3. controller层方法

``` java
@RestController
public class QuickstartController {
    @GetMapping("test")
    @ResultAnnotation
    public Integer test(int i) {
        return i;
    }
}
```

4. 启动项目

spring boot项目启动

5. 使用

项目启动完成后，访问[http://127.0.0.1:8080/swagger-ui.html](http://127.0.0.1:8080/swagger-ui.html)打开swagger页面<br>
测试刚刚写的test方法，可以用swagger测试，也可以直接访问[http://127.0.0.1:8080/test?i=1](http://127.0.0.1:8080/test?i=1)<br>
得到如下结果：
``` js
{
  "code": "0",
  "message": "成功",
  "data": 1,
  "success": true
}
```
将参数i=1去掉，得到如下结果：
``` js
{
  "code": "1",
  "message": "未知错误",
  "data": "xxx",
  "success": false
}
```

6. 小结

仅仅引入了一个依赖，就集成了swagger，对异常结果也进行了处理，并且仅通过一个简单的注解@ResultAnnotation，使得返回的结果有统一的报文格式。<br>
还有分布式锁、重试、异常通知等功能，也可以仅仅通过一个注解方便的实现。<br>
其他模块还有更多强大的功能等着你来尝试，请看[详细教程](https://cc4414.gitee.io/cc-spring-doc)

## 项目结构
``` lua
cc-spring
├── cc-spring-boot-starter-parent
│   ├── cc-spring-boot-starter -- 通用的功能
│   ├── cc-spring-cloud-starter -- spring cloud的常用组件整合
│   ├── cc-spring-cloud-starter-gateway -- 网关
│   ├── cc-spring-web -- controller层统一处理及整合swagger
│   │   ├── cc-spring-web-core
│   │   └── cc-spring-web-starter
│   ├── cc-spring-mybatis -- mybatis-plus的租户、自动填充以及冗余字段更新
│   │   ├── cc-spring-mybatis-core
│   │   └── cc-spring-mybatis-starter
│   ├── cc-spring-resource -- 资源服务器
│   │   ├── cc-spring-resource-core
│   │   └── cc-spring-resource-starter
│   ├── cc-spring-auth -- 认证服务器
│   │   ├── cc-spring-auth-client
│   │   └── cc-spring-auth-server
│   ├── cc-spring-sys -- 系统管理
│   │   ├── cc-spring-sys-client
│   │   └── cc-spring-sys-server
│   ├── cc-spring-generator -- 代码生成器
│   └── cc-spring-xxx -- 其他
├── cc-spring-demo -- 使用样例
└── web -- 前端页面
```
## 核心模块
- cc-spring-boot-starter
- cc-spring-web
- cc-spring-mybatis
- cc-spring-sys
- cc-spring-resource
- cc-spring-auth
- cc-spring-cloud-starter-gateway