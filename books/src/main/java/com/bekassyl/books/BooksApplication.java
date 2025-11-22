package com.bekassyl.books;

import com.bekassyl.books.dto.BooksInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(BooksInfoDto.class)
@OpenAPIDefinition(
        info = @Info(
                title = "Books microservice REST API Documentation",
                description = "JaryqLibrary books microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Bekasyl Asylbekov",
                        email = "bekasylasylbekov@gmail.com"
                )
        )
)
public class BooksApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }
}
