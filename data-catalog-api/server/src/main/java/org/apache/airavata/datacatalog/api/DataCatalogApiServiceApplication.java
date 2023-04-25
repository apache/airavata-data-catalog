package org.apache.airavata.datacatalog.api;

import org.apache.airavata.datacatalog.api.sharing.SharingManager;
import org.apache.airavata.datacatalog.api.sharing.SharingManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "org.apache.airavata.datacatalog.api", "org.apache.custos.sharing.core" })
@SpringBootApplication
@EnableJpaRepositories({ "org.apache.custos.sharing.core.persistance.repository",
        "org.apache.airavata.datacatalog.api.repository" })
@EntityScan(basePackages = { "org.apache.airavata.datacatalog.api.model",
        "org.apache.custos.sharing.core.persistance.model" })
public class DataCatalogApiServiceApplication {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DataCatalogApiServiceApplication.class, args);
    }

    @Bean
    @Primary
    public SharingManager getSharingManager() {
        // TODO: externalize bean name to a property
        // return (SharingManager) applicationContext.getBean("simpleSharingManager");
        return applicationContext.getBean(SharingManagerImpl.class);
    }
}
