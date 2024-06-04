package br.com.sistemaescolar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info=@Info(title="Sistema Escolar V1.0 - " + "${ENV}" + " Teste Deploy"))
@SpringBootApplication
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class SistemaEscolar {

	public static void main(String[] args) {
		SpringApplication.run(SistemaEscolar.class, args);
	}

}
