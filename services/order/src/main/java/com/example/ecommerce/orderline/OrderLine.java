package com.example.ecommerce.orderline;

import com.example.ecommerce.order.Order;
import com.example.ecommerce.order.Order;
import jakarta.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OrderLine {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer productId;
    private double quantity;
}
