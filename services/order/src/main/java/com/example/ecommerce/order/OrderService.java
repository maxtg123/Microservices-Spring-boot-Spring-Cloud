package com.example.ecommerce.order;

import com.example.ecommerce.customer.CustomerClient;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.orderline.OderLineRequest;
import com.example.ecommerce.orderline.OrderLineService;
import com.example.ecommerce.product.ProductClient;
import com.example.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderMapper mapper;

    private final OrderLineService orderLineService;

    public Integer CreateOrder( OrderRequest request) {
        //check the customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can not create oder:: No customer exits with the provided ID::"));

        // purchase the products -> product- microservice(RestTemplate)
        this.productClient.purchaseProducts(request.products());

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

        //send the oder confirmation --> notification-microservice(kafka)
        return null;
    }
}
