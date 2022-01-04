package com.vbqncc.tstock.tstock.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/temp-Cover/**").addResourceLocations("views/*");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //   super.addResourceHandlers(registry);
    }


}
