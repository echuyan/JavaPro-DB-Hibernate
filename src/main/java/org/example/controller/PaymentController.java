package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.client.ProductsClient;
import org.example.dto.PaymentDtoRq;
import org.example.dto.PaymentResult;
import org.example.dto.ProductDto;
import org.example.service.PaymentService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("payments")
@RestController
@RequestMapping
@Log4j2
public class PaymentController {
    private final ProductsClient productsClient;
    private final PaymentService paymentService;

    public PaymentController(ProductsClient productsClient, PaymentService paymentService) {
        this.productsClient = productsClient;
        this.paymentService = paymentService;
    }


    @GetMapping("/users/{userId}/products")
    public List<ProductDto> getProducts(@PathVariable Long userId) {
        return productsClient.getProductsByUserId(userId);
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public PaymentResult pay(@RequestBody PaymentDtoRq request) {

        log.info("1. Received request to payment controller for user {}.", request.getUserId());

        return paymentService.makePayment(request);
    }

}
