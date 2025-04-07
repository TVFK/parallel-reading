package ru.taf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // TODO убрать это в application.yaml
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/covers/**")
                .addResourceLocations("file:C:/Users/TAFIK/OneDrive/Рабочий стол/parallel reading/covers/");
    }
}