package com.whoami.raise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.whoami.raise.mapper")  // 配置mybatis的mapper接口所在包进行自动扫描，配置了这个注解才生效
@EnableDiscoveryClient  // 专门针对Eureka注册中心，更为通用
@SpringBootApplication
public class RaiseMainType {

	public static void main(String[] args) {
		SpringApplication.run(RaiseMainType.class, args);
	}

}
