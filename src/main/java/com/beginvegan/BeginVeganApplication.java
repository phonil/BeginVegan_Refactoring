package com.beginvegan;

import com.beginvegan.global.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource(value = { "classpath:s3/application-s3.yml" }, factory = YamlPropertySourceFactory.class)
@PropertySource(value = { "classpath:database/application-database.yml" }, factory = YamlPropertySourceFactory.class)
@PropertySource(value = { "classpath:oauth2/application-oauth2.yml" }, factory = YamlPropertySourceFactory.class)
@PropertySource(value = { "classpath:swagger/application-springdoc.yml" }, factory = YamlPropertySourceFactory.class)
@PropertySource(value = { "classpath:firebase/application-firebase.yml" }, factory = YamlPropertySourceFactory.class)
public class BeginVeganApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeginVeganApplication.class, args);
	}

}
