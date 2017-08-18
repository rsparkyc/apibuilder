package apibuilder.config;

import apibuilder.service.IListService;
import apibuilder.service.factory.ListServiceFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public IListService[] iListServiceList(final IListService[] services) {
        ListServiceFactory.registerServices(services);
        return services;
    }
}
