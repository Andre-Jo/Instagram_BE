package com.insta.instagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class InstagramApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagramApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods(
								HttpMethod.GET.name(),
								HttpMethod.HEAD.name(),
								HttpMethod.POST.name(),
								HttpMethod.PUT.name(),
								HttpMethod.DELETE.name()
						);
			}
		};
	}

}
