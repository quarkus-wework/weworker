# 企微开放接口以及企微官方后台API实现

集成了企微开放平台api，提供对应功能，方便企业通过自建应用管理企业/客户；另外企微管理员可以在此项目下通过手机端企微扫描二维码将企微官方后台的功能api化，能够更进一步满足企微管理的需求。

## 开发方式
需要准备好mysql，clone此项目，执行命令：
```shell script
mvn compile quarkus:dev 
```
## 打包
```shell script
# 普通打包
mvn package -Dquarkus.package.type=uber-jar
# 打包成原生文件（需要graalVM）
mvn package -Pnative
# 打包成原生镜像
mvn package -Pnative -Dquarkus.native.container-build=true
```
