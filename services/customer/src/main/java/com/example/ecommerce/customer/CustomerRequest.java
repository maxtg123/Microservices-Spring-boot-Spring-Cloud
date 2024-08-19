package com.example.ecommerce.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerRequest(

     String id,
     @NotNull(message = "Customer firstname is required")
     String firstname,
     @NotNull(message = "Customer lastname is required")
     String lastname,

     @NotNull(message = "Customer email  is required")
     @NotNull(message = "Customer email is not valid email required")
     String email,
     Address address
)
            {
}
