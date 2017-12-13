# MiniForum

Application start from command line:

```
.gradlew clean build bootRun
```

Or in eclipse:

```
clean build bootRun
```

DatabaseConfig:

https://github.com/tfkfan/MiniForum/blob/development/src/main/java/com/tfkfan/application/VaadinSpringConfiguration.java

```
dataSource.setDriverClassName("com.mysql.jdbc.Driver");
dataSource.setUrl("jdbc:mysql://localhost:3306/database");
dataSource.setUsername("username");
dataSource.setPassword("password");
```
