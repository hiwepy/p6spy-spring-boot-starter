# p6spy-spring-boot-starter
p6spy starter for spring boot

### 说明


 > 基于 p6spy 的 Spring Boot Starter 实现

1. 最新IP数据下载地址： https://github.com/lionsoul2014/p6spy

### Maven

``` xml
<dependency>
	<groupId>com.github.vindell</groupId>
	<artifactId>p6spy-spring-boot-starter</artifactId>
	<version>${project.version}</version>
</dependency>
```

### Sample

```java

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.p6spy.engine.spy.P6DriverManagerDataSource;
import com.p6spy.spring.boot.ext.P6spyDataSource;

@EnableP6Spy
@SpringBootApplication
public class Application {
	
	@Bean
	@P6spyDataSource
	DataSource dataSource() {
		
		// just demo
		P6DriverManagerDataSource ds =	new P6DriverManagerDataSource();
		ds.setUrl("url");
		ds.setUser("username");
		ds.setPassword("password");
		
		return ds;
	};
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}

```

