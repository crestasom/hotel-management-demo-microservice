package com.cretasom.sevice_discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SeviceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeviceDiscoveryApplication.class, args);
	}

}
