package org.apache.airavata.datacatalog.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"org.apache.airavata.datacatalog.api","org.apache.custos.sharing.core"})
@SpringBootApplication
@EnableJpaRepositories({"org.apache.custos.sharing.core.persistance.repository","org.apache.airavata.datacatalog.api.repository"})
@EntityScan(basePackages = {"org.apache.airavata.datacatalog.api.model","org.apache.custos.sharing.core.persistance.model"})
public class DataCatalogApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCatalogApiServiceApplication.class, args);
    }

}
