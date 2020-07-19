package com.whoami.raise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * member-manager用户启动类
 * @author whoami
 *
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class RaiseMainType {

	public static void main(String[] args) {
		SpringApplication.run(RaiseMainType.class, args);
	}

}
