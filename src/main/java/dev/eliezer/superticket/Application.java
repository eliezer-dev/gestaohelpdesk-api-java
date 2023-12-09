package dev.eliezer.superticket;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
		title = "SuperTicket",
		description = "API responsável por administrar chamados de atendimento.",
		version = "0.0.2-Final"
))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
