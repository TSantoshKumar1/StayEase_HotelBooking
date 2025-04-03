package com.airbnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableRetry
public class AirbnbApplication {

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate1 = new RestTemplate();
		return restTemplate1;
	}

	public static void main(String[] args) {



		SpringApplication.run(AirbnbApplication.class, args);



	}

}
