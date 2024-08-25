package com.example.ecommerce.payment;

import com.example.ecommerce.notification.NotificationProducer;
import com.example.ecommerce.notification.PaymentNotificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    private NotificationProducer notificationProducer;

    public Integer createPayment(@Valid PaymentRequest request) {
            var payment = repository.save(mapper.toPayment(request));

            notificationProducer.sendNotification(
                    new PaymentNotificationRequest(
                            request.orderReference(),
                            request.amount(),
                            request.paymentMethod(),
                            request.customer().firstname(),
                            request.customer().lastname(),
                            request.customer().email()
                    )
            );
            return payment.getId();
    }
}
