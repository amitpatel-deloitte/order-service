package com.hashedin.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Order")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int order_id;
    @Column(nullable = false)
    private int user_id;
    @Column(nullable = false)
    private int restaurant_id;
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private long totalPrice;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime timeStamp;
}
