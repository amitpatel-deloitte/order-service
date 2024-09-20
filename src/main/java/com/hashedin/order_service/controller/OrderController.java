package com.hashedin.order_service.controller;

import com.hashedin.order_service.model.Order;
import com.hashedin.order_service.model.OrderRequestDTO;
import com.hashedin.order_service.model.OrderStatus;
import com.hashedin.order_service.model.PaymentStatus;
import com.hashedin.order_service.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order placeOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.placeOrder(orderRequestDTO.getRestaurantId(), orderRequestDTO.getUserId(), orderRequestDTO.getItemsIdsWithQuantity());
    }

    @PutMapping("/{orderId}/order-status")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus){
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    @PutMapping("/{orderId}/payment-status")
    public Order updatePaymentStatus(@PathVariable Long orderId, @RequestParam PaymentStatus paymentStatus){
        return orderService.updatePaymentStatus(orderId, paymentStatus);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }
}
