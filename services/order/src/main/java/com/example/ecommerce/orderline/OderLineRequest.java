package com.example.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity) {
}
