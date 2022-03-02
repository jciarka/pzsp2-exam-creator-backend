package com.PZSP2.PFIMJ;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.PZSP2.PFIMJ.repositories")
public class PfimjApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfimjApplication.class, args);
	}

}
