package com.codersquare.filters;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Hosam",
                        email = "dev.hosamyoussef@gmail.com",
                        url = "https://github.com/hosamusf"
                ),
                description = "OpenApi Documentation For Coder Square Project",
                title = "OpenApi Specification - Hosam Youssef ",
                version = "1.0",
                license = @License(
                        name = "MIT License",
                        url = "https://en.wikipedia.org/wiki/MIT_License"
                ),
                termsOfService = "Feel free to use as you wish"
        ),
        servers = @Server(
                description = "Local ENV",
                url = "http://localhost:3001/"
        )
)
public class OpenApiConfig {
}
