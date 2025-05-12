package com.journalingN.journaling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalingApplication {

	public static void main(String[] args) {
 		SpringApplication.run(JournalingApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager someThing(MongoDatabaseFactory DbFactory){
		return new MongoTransactionManager(DbFactory);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

