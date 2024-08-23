package com.example.ecommerce.order;

import com.example.ecommerce.customer.CustomerClient;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.product.ProductClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OderService {


    private final CustomerClient customerClient;

    private final ProductClient productClient;

    public Integer CreateOrder(@Valid OrderRequest request) {
        //check the customer --> OpenFeign

        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can not create oder:: No customer exits with the provided ID::"));

        // purchase the products -> product- microservice(RestTemplate)



        //persist oder

        //persist oder lines

        //start payment process

        //send the oder confirmation --> notification-microservice()
        return null;
    }
}
