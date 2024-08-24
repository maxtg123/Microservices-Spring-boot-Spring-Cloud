package com.example.ecommerce.kafka;

import com.example.ecommerce.customer.CustomerResponse;
import com.example.ecommerce.order.PaymentMethod;
import com.example.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String OrderReference,

        BigDecimal TotalAmount,

        PaymentMethod paymentMethod,

        CustomerResponse response,

        List<PurchaseResponse>products
        ) {

}
