package deeroot.deeroot_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DeerootShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeerootShopApplication.class, args);
	}

}
