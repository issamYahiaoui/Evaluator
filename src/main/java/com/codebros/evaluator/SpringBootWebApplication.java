package com.codebros.evaluator;



import com.codebros.evaluator.auth.service.SeederService;
import com.codebros.evaluator.auth.service.UserService;
import com.codebros.evaluator.workspace.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class SpringBootWebApplication {


	@Autowired
	SeederService seederService;

	@EventListener
	public void seed(ContextRefreshedEvent event) {
		seederService.seed();
	}


	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

}