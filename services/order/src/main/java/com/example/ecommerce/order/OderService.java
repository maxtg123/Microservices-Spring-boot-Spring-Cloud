package com.example.ecommerce.order;

import com.example.ecommerce.customer.CustomerClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OderService {


    private final CustomerClient customerClient;

    public Integer CreateOrder(@Valid OrderRequest request) {
        //check the customer --> OpenFeign

        // purchase the products -> product- microservice

        //persist oder

        //persist oder lines

        //start payment process

        //send the oder confirmation --> notification-microservice()
        return null;
    }
}
