package com.jesusfs.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TasksApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksApiApplication.class, args);
	}

}
