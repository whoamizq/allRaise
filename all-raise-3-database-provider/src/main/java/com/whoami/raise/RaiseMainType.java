package com.whoami.raise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableEurekaClient // ר�����Eurekaע������
@MapperScan("com.whoami.raise.mapper")  // ����mybatis��mapper�ӿ����ڰ������Զ�ɨ�裬���������ע�����Ч
@EnableDiscoveryClient  //��Ϊͨ��
@SpringBootApplication
public class RaiseMainType {

	public static void main(String[] args) {
		SpringApplication.run(RaiseMainType.class, args);
	}

}
