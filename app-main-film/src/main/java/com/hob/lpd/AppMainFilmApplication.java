package com.hob.lpd;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AppMainFilmApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(AppMainFilmApplication.class).web(true).run(args);
	}
}
