package edu.ml.tensorflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload-dir/**").addResourceLocations("file:./upload-dir/");
        registry.addResourceHandler("/predicted/**").addResourceLocations("file:./predicted/");
        super.addResourceHandlers(registry);
    }
}
