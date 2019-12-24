package org.innovect.assignment;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class AppRunner {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppRunner.class);
		System.setProperty("java.io.tmpdir", "/home/administrator/Work/Java_Projects/temp");
		app.setBannerMode(Banner.Mode.OFF);

		app.run(args);
	}
}
