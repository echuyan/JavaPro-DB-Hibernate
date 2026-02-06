package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationProducts {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationProducts.class);
        application.setAdditionalProfiles("products");
        application.run(args);
    }

}