# LiteARouter

## _摘要（ very important!!! ）_：

1. 顾名思义，本项目是【 [ARouter](https://github.com/alibaba/ARouter/) 精简版】，仅支持组件导航（Activity、Fragment 等）
2. 本库对 ARouter 进行了改造：支持在【单独 [RePluginX](https://github.com/GitLqr/RePluginX) 插件】中使用。

> 注意：LiteARouter 并不支持在 RePlugin 宿主中 统筹导航各个插件中的组件（丑话说在前面，别自己眼瞎还来骂我），仅仅只是支持在各个插件中单独使用而已。

## 一、功能介绍

1. **支持 [RePluginX](https://github.com/GitLqr/RePluginX) 插件内使用**
2. **同时兼容 `AndroidX` 与 `Support`**
3. 支持直接解析标准 URL 进行跳转
4. 映射关系按组分类、多级管理，按需初始化
5. 支持获取 Fragment
6. 完全支持 Kotlin 以及混编
7. 支持动态注册路由信息

## 二、典型应用

1. 从外部 URL 映射到内部页面，以及参数传递与解析
2. 跨模块页面跳转，模块间解耦
3. 跨模块 API 调用，通过控制反转来做组件解耦

## 三、基础功能

### 1. 添加依赖和配置

```gradle
compile 'com.github.GitLqr.LiteARouter:litearouter-api:${version}'
annotationProcessor 'com.github.GitLqr.LiteARouter:litearouter-compiler:${version}' // Java项目
// kapt 'com.github.GitLqr.LiteARouter:litearouter-compiler:${version}' // Kotlin项目
```

> 注：`${version}` 需要替换为最新的版本号，该库发布在 jitpack 上，最新版本为 ![JitPack](https://img.shields.io/jitpack/v/github/GitLqr/LiteARouter)

### 2. 添加注解

```java
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/test/activity")
public class YourActivity extend Activity {
    ...
}
```

### 3. 初始化 SDK

```java
if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
    LiteARouter.openLog();     // 打印日志
    LiteARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
}
LiteARouter.init(mApplication); // 尽可能早，推荐在Application中初始化
```

### 4. 发起路由操作

```java
// 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
LiteARouter.getInstance().build("/test/activity").navigation();

// 2. 跳转并携带参数
LiteARouter.getInstance().build("/test/1")
            .withLong("key1", 666L)
            .withString("key3", "888")
            .withObject("key4", new Test("Jack", "Rose"))
            .navigation();
```

### 5. 添加混淆规则

```
# LiteARouter
-keep public class com.gitlqr.litearouter.**{*;}
```

> 该库自带混淆规则，如果你项目 Gradle 版本比较旧的话，可能需要自己复制应用以上混淆规则。
