## 项目介绍

本项目用于本地化查询、导出 HMDB 中的代谢物的信息。原始数据来自于：https://hmdb.ca/downloads ，先使用 Python 将原始代谢物的数据导入到 Mongo DB 中，然后在提供查询和导出。

## 运行说明

本项目基于 RuoYi 4.4 版本，其实本可不必在框架中，只不过我是在 RuoYi 框架中开发，核心代码不多，且使用了 RuoYi 的部分功能，比如有前端网页、导出Excel等。

1. 使用 Docker 安装 MongoDB，我使用的是4.2版本，具体操作请查阅官方文档，创建一个database,默认`bio_db`，和默认 collection `hmdb2210`
> docker pull mongo:4.2

2. 根目录 python_code 中提供了 requirements.txt，安装 Python 库，实验环境为 Python3.10.7，HMDBTools.py 是主程序
   注意修改 XML 路径和 MongoDB 的地址和帐号密码


3. 运行 HMDBTools.py，等待导入完成


4. 在 Mysql 中创建一个数据库，或使用 `biotools_opensource` 默认名称


5. 运行本 Java 项目（基于Springboot）,浏览器打开 http://localhost:8080/bio/hmdb
> 需要先登陆 RuoYi 后台系统，默认帐号 admin / admin123 ，需要先修改application.yml中的 mysql 和 mongodb 的地址

![1](https://github.com/5venw0ng/hmdblocal_tools/blob/master/2022-11-11_14-44.png)

![2](https://github.com/5venw0ng/hmdblocal_tools/blob/master/2022-11-11_14-44_1.png)

