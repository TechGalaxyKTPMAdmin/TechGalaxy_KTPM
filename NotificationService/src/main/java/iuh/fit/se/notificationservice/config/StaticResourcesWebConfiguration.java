package iuh.fit.se.notificationservice.config;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StaticResourcesWebConfiguration
        implements WebMvcConfigurer {

    @Value("${upload-file.base-uri}")
    private String uploadDir;
    private String baseURI;

    @PostConstruct
    public void init() {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        this.baseURI = uploadPath.toString();
        this.baseURI = this.baseURI.replace("\\", "/");
        this.baseURI = "file:///" + this.baseURI + "/";

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/storage/**")
                .addResourceLocations(baseURI);
    }
}
