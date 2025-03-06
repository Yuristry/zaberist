package org.zaber.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.zaber.*")
public class ZaberStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZaberStarterApplication.class, args);
	}

}
