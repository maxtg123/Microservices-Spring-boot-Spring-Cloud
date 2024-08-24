package com.example.ecommerce.order;

import com.example.ecommerce.customer.CustomerClient;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.kafka.OrderConfirmation;
import com.example.ecommerce.kafka.OrderProducer;
import com.example.ecommerce.orderline.OderLineRequest;
import com.example.ecommerce.orderline.OrderLineService;
import com.example.ecommerce.product.ProductClient;
import com.example.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderMapper mapper;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    public Integer CreateOrder( OrderRequest request) {
        // //check the customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can not create oder:: No customer exits with the provided ID::"));

        // purchase the products -> product- microservice(RestTemplate)
      var purchaseProducts =  this.productClient.purchaseProducts(request.products());

        //persist order
       var order = this.repository.save(mapper.toOrder(request));

        //persist oder lines
        for (PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        //todo start payment process

        orderProducer.SendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );
        //send the oder confirmation --> notification-microservice(kafka)
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }


    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("no order found with the provider ID: %d", orderId)));

    }
}
