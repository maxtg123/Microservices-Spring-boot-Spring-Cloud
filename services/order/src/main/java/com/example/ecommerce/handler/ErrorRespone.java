package com.example.ecommerce.handler;

import java.util.Map;

public record ErrorRespone (
        Map<String, String> errors
){
}
