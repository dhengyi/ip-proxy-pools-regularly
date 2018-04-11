# IP代理池（ip-proxy-pools）
平时在写爬虫的时候，最怕的事情就是IP封禁。这是我自制的一个IP代理池，使用Java语言进行编写，结合Redis数据库对代理IP进行存储。被抓取的代理IP来源于[xici代理网](http://www.xicidaili.com/)。

**对于技术上的实现细节，参考本人所写的博客链接**：
[Java网络爬虫（十一）--重构定时爬取以及IP代理池（多线程+Redis+代码优化）](http://blog.csdn.net/championhengyi/article/details/77053448)

## 环境需求
>- JDK 1.8
>- Redis 3.0.6
>- IDEA
>- Maven

## 实现架构
![架构说明](http://on-img.com/chart_image/598c1f86e4b02cf2fc84c11a.png)

## 使用说明
要使用此IP代理池，只能将本项目clone至本地，然后使用IDEA运行源代码。运行结果如下图：

![运行结果](http://i4.bvimg.com/633787/dbbaab4034d2b5f5.png)

就目前来说，如果想要真正的将此IP代理池运用到其它工程中，还需要对代码做额外的补充，最基本也要考虑使用`通知/等待机制`。

对于将此IP代理池如何运用到一个工程中，可以参考：[multithreading-crawlers](https://github.com/championheng/multithreading-crawlers)

## TODO
1. 优化任务分配策略
2. 对外提供接口与使用文档
3. 可视化管理... ...

**注：此IP代理池真正运用在工程中的版本，[multithreading-crawlers](https://github.com/championheng/multithreading-crawlers)，可以称为第三版，与此版本差别还是挺大的，对于版本3，我会尽快push到这个仓库中... ...**

## 版本说明
![version 2.0](https://img.shields.io/badge/version-2.0-blue.svg)
