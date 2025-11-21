package com.cretasom.servics_rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

public class RatingServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingServicesApplication.class, args);
	}

	@Bean
	@LoadBalanced
	RestTemplate getRestTemplate() {
		System.out.println("Creating ben of Rest Template");
		var factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(3000); // 3 seconds to establish connection
		factory.setConnectionRequestTimeout(3000); // 3 seconds to wait for response

		return new RestTemplate(factory);
	}

}
