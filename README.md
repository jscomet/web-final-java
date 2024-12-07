

### 项目简介
`web-java` 是一个基于 Spring Boot 和 MyBatis-Plus 的 Web 系统后台项目。该项目使用 Maven 进行构建和依赖管理。

### 技术栈
- **语言**: Java
- **框架**: Spring Boot
- **构建工具**: Maven
- **数据库**: MyBatis-Plus

### 接口文档
项目的接口文档可以通过以下链接访问：
[接口文档](https://app.apifox.com/main/teams/3100106)

### 快速生成 CRUD 代码
项目使用 EasyCode 插件来快速生成 CRUD 代码。使用方法如下：
1. 在 IntelliJ IDEA 中安装 EasyCode 插件。
2. 导入 `docs/EasyCodeConfig.json` 配置文件。
3. 使用 mybatis-plus-fixed 模式生成代码。

### 工具类
项目中包含以下工具类：
- **AssertUtils**: 断言工具类，用于判断条件是否成立，否则返回相应的错误响应。
- **AuthUtils**: 权限工具类，用于用户的权限控制，判断当前用户的角色。
- **ExcelUtils**: Excel 工具类，基于 easypoi 实现用户的导入导出功能。
- **HttpRequestUtils**: HTTP 请求工具类。
- **HttpResponseUtils**: HTTP 响应工具类。
- **JwtUtils**: JWT 工具类，用于生成和解析 JSON Web Token。
- **UploadUtils**: 文件上传工具类，主要用于将文件存储在本地。

### 配置文件
项目的 EasyCode 配置文件位于 `docs/EasyCodeConfig.json`，该文件包含了生成代码所需的模板和配置。

### 环境部署
进入docker目录并执行一下命令，集成mysql和redis:
   ```bash
   docker-compose -f  up -d
   ```
   ```
