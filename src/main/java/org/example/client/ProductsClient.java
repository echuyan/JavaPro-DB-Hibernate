package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PaymentRequest;
import org.example.dto.ProductDto;
import org.example.exceptions.ApiError;
import org.example.exceptions.MyException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Profile("payments")
@Component
public class ProductsClient {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public ProductsClient(RestClient restClient, ObjectMapper objectMapper) {

        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public List<ProductDto> getProductsByUserId(long userId) {
        ProductDto[] productDtos = restClient.get()
                .uri("users/{userId}/products", userId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    throw new ProductServiceException("Error while retrieving user prodcuts: " + resp);
                })
                .body(ProductDto[].class);
        return productDtos == null ? List.of() : Arrays.asList(productDtos);
    }

    public ProductDto getProductById(long productId) {
        return restClient.get()
                .uri("products/{productId}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    throw new ProductServiceException("Error while getting product by id: " + resp);
                })
                .body(ProductDto.class);
    }

    public void payment(long productId, BigDecimal amount) {
        log.info("4. Received request to ProductsClient for productId {} with amount {}.", productId, amount);
        restClient.post()
                .uri("products/{productId}/payment", productId)
                .body(new PaymentRequest(amount))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    log.info("resp: " + resp);
                    String body;
                    try {
                        body = new String(resp.getBody().readAllBytes(), StandardCharsets.UTF_8);
                        log.info("body: " + body);
                    } catch (Exception e) {
                        throw new MyException(resp.getStatusCode().value(), "Cannot read body");
                    }

                    ApiError apiError = null;
                    try {
                        apiError = objectMapper.readValue(body, ApiError.class);
                        log.info("apiError: " + apiError);
                    } catch (Exception ignored) {
                    }

                    String message = (apiError != null && apiError.getMessage() != null && !apiError.getMessage().isBlank())
                            ? apiError.getMessage() : (!body.isBlank() ? body : "Error" + resp.getStatusCode().value());


                    throw new MyException(resp.getStatusCode().value(), message);
                }).toBodilessEntity();
    }
}
