package com.example.ecommerce.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//@FeignClient(
//    name = "customer-service",
//    url = "${application.config.customer-url}"
//)

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url:http://localhost:8080/api/v1/customers}"
)

public interface CustomerClient {

  @GetMapping("/{customer-id}")
  Optional<CustomerResponse> findCustomerById(@PathVariable("customer-id") String customerId);
}
