package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.client.ProductsClient;
import org.example.dto.PaymentDtoRq;
import org.example.dto.PaymentResult;
import org.example.dto.ProductDto;
import org.example.exceptions.MyException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Profile("payments")
@Service
@Log4j2
public class PaymentService {
    private final ProductsClient productsClient;

    public PaymentService(ProductsClient productsClient) {
        this.productsClient = productsClient;
    }

    public PaymentResult makePayment(PaymentDtoRq paymentDtoRq) {

        log.info("2. Received request to payment service for user {}.", paymentDtoRq.getUserId());
        BigDecimal amount = paymentDtoRq.getAmount();
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must not be null or zero");
        }

        ProductDto productDto = productsClient.getProductById(paymentDtoRq.getProductId());
        if (productDto == null) {
            throw new IllegalArgumentException("product not found" + paymentDtoRq.getProductId());
        }

        if (productDto.userId() == null || productDto.userId() != paymentDtoRq.getUserId()) {
            log.info("productDto.userId()" + productDto.userId());

            throw new IllegalArgumentException("product does not belong to this user " + paymentDtoRq.getUserId());
        }

        log.info("3. Calling productsClient.payment");
        try {
            productsClient.payment(paymentDtoRq.getProductId(), amount);
        }
        catch (MyException e) {
            throw e;
        }

        return new PaymentResult("OK", paymentDtoRq.getProductId(), amount, "Payment successful");

    }
}
