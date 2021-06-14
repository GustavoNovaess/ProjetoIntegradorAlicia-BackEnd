package org.generation.alicia.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors
				.basePackage("org.generation.alicia.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metadata())
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET,responseMessageForGET());
	}
	
	public static ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title("Alicia")
				.description("Rede Social")
				.version("1.0.0")
				.license("Apacahe License Version 2.0")
				.licenseUrl("http://localhost:8080/swagger-ui/")
				.contact(contact())
				.build();
	}
	
	private static Contact contact() {
		return new Contact("Alicia", "https://github.com/GustavoNovaess/ProjetoIntegradorAlicia", "aliciaprojeto@gmail.com");
	}
	
private static List<Response> responseMessageForGET() {
		
		return new ArrayList<Response>() {
			
			private static final long serialVersionUID = 1L;
			{
				add(new ResponseBuilder().code("200") // OK
				.description("OK").build());
				add(new ResponseBuilder().code("201") // CREATED
				.description("Objeto criado com sucesso.").build());
				add(new ResponseBuilder().code("401") // UNATHORIZED
				.description("Acesso negado.").build());
				add(new ResponseBuilder().code("403") // FORBIDDEN
				.description("Acesso não permitido").build());
				add(new ResponseBuilder().code("404") // NOT FOUND
				.description("Ops! Página não encontrada.").build());
				add(new ResponseBuilder().code("500") // INTERNAL ERROR
				.description("Erro do Servidor.").build());
			}
		};
		
	}
	
}
