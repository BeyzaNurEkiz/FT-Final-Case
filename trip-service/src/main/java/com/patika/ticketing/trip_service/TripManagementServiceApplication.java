package com.patika.ticketing.trip_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TripManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripManagementServiceApplication.class, args);
	}

}
