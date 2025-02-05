package com.microserviceproject.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Employee Api Specification",
				description = "Api documentation for Employee Service",
				version = "1.0",
				contact = @Contact(
						name = "Sagiri",
						email = "thang.nh225763@gmail.com",
						url = "https://sagiri.vercel.app"
				),
				license = @License(
						name = "MIT License",
						url = "https://sagiri.vercel.app/licenses"
				),
				termsOfService = "https://sagiri.vercel.app/terms"
		),
		servers = {
				@Server(
						description = "Local ENV",
						url = "http://localhost:9002"
				),
				@Server(
						description = "Dev ENV",
						url = "https://employee-service.dev.com"
				),
				@Server(
						description = "Prod ENV",
						url = "https://employee-service.prod.com"
				),
		}
)
public class OpenApiConfig {
}
