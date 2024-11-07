package com.luv2code.spring_boot_library.config;

import com.luv2code.spring_boot_library.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private String allowOrigin = "https://localhost:3000";
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                                                     CorsRegistry cors){
        HttpMethod[] httpMethods = {HttpMethod.POST,HttpMethod.PATCH,HttpMethod.DELETE,HttpMethod.PUT};
        config.exposeIdsFor(Book.class);
        disableHttpMethods(Book.class , config , httpMethods);
        cors.addMapping(config.getBasePath()+"/**").allowedOrigins(allowOrigin);
    }

    private void disableHttpMethods(Class bookClass, RepositoryRestConfiguration config, HttpMethod[] unsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(bookClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)));
    }
}
