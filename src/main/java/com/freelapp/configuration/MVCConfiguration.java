package com.freelapp.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer{
	
//	crea in ogni ambiente in cui runna l'applicazione il path assoluto 
//	alla cartella upload cosicché i loghi caricati per ogni cliente
//	possano essere visualizzati idipendentemente dal percorso del progetto
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String logoLocation = "upload";
		
		Path logoClienteDirectory = Paths.get(logoLocation);
		
		String logoClientePath = logoClienteDirectory.toFile().getAbsolutePath();
		
		System.out.println("logoClientePath" + logoClientePath);
		
		registry.addResourceHandler("/" + logoLocation + "/**")
			.addResourceLocations("file:" + logoClientePath + "/");
	}

}
