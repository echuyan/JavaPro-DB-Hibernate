package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@Profile("payments")
@Configuration
public class ProductsClientConfig {

    @Bean
    public RestClient productsRestClient(@Value("${products.base-url}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }

}
