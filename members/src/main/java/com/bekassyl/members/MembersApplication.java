package com.bekassyl.members;

import com.bekassyl.members.dto.MembersInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(MembersInfoDto.class)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Members microservice REST API Documentation",
                description = "JaryqLibrary members microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Bekasyl Asylbekov",
                        email = "bekasylasylbekov@gmail.com"
                )
        )
)
public class MembersApplication {
    public static void main(String[] args) {
        SpringApplication.run(MembersApplication.class, args);
    }
}
