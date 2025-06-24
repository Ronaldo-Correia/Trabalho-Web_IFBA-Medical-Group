package com.system.clinic.config;

import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import gg.jte.TemplateEngineConfig;
import gg.jte.spring.boot.autoconfigure.JteViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;

@Configuration
public class JteConfig {

    @Bean
    public ViewResolver viewResolver(TemplateEngine templateEngine) {
        JteViewResolver viewResolver = new JteViewResolver();
        viewResolver.setPrefix(""); // sem "src/" nem "/jte"
        viewResolver.setSuffix(".jte");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
        return TemplateEngine.create(new ResourceCodeResolver("jte"), TemplateEngineConfig.defaults());
    }
}
