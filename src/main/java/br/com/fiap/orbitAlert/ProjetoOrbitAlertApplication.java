package br.com.fiap.orbitAlert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@EntityScan
@EnableJpaRepositories
@SpringBootApplication
public class ProjetoOrbitAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoOrbitAlertApplication.class, args);
	}

}
