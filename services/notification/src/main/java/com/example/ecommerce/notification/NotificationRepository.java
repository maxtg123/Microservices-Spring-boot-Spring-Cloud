package com.example.ecommerce.kafka.payment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<PaymentConfirmation, String> {
}
