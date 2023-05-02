package org.apache.airavata.datacatalog.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.airavata.datacatalog.api.sharing.SharingManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "org.apache.airavata.datacatalog.api", "org.apache.custos.sharing.core" })
@SpringBootApplication
@EnableJpaRepositories({ "org.apache.custos.sharing.core.persistance.repository",
        "org.apache.airavata.datacatalog.api.repository" })
@EnableJpaAuditing
@EntityScan(basePackages = { "org.apache.airavata.datacatalog.api.model",
        "org.apache.custos.sharing.core.persistance.model" })
public class DataCatalogApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCatalogApiServiceApplication.class, args);
    }

    @Bean
    @Primary
    public SharingManager getSharingManager(
            @Value("${sharing.manager.class.name:org.apache.airavata.datacatalog.api.sharing.SimpleSharingManagerImpl}") String sharingManagerClassName) {
        try {
            Class<?> sharingManagerClass = (Class<?>) Class.forName(sharingManagerClassName);
            Constructor<?> constructor = sharingManagerClass.getConstructor();
            try {
                return (SharingManager) constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new RuntimeException("Failed to instantiated sharing manager " + sharingManagerClass, e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load the sharing manager class " + sharingManagerClassName, e);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Failed to find no-arg constructor for " + sharingManagerClassName, e);
        }
    }
}
