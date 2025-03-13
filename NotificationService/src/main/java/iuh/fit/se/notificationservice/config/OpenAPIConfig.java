package iuh.fit.se.notificationservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private Server createServer(String url, String description) {
        return new Server()
                .url(url)
                .description(description);
    }

    private Contact createContact() {
        return new Contact()
                .name("Tech Galaxy Team");
    }

    private License createLicense() {
        return new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");
    }

    private Info createApiInfo() {
        return new Info()
                .title("Tech Galaxy Mobile Store API")
                .version("1.0")
                .contact(createContact())
                .description("""
                            Welcome to the Tech Galaxy Mobile Store API!
                        
                            This project is developed by a team of enthusiastic members who are passionate about technology and customer satisfaction. 
                        
                            **Key Features**:
                            - Secure and reliable API for managing mobile store operations.
                            - Detailed product listings with real-time updates.
                            - Comprehensive user and order management system.
                        
                            Enjoy exploring our API and happy coding!
                        """)
                .license(createLicense());
    }

    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
                .info(createApiInfo())
                .servers(List.of(
                        createServer("http://localhost:8081", "Development Server - Local Environment"),
                        createServer("http://localhost:8080", "Production Server - Live Environment")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }
}
