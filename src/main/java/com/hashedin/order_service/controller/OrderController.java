package com.hashedin.order_service.controller;

import com.hashedin.order_service.model.Order;
import com.hashedin.order_service.model.OrderRequestDTO;
import com.hashedin.order_service.model.OrderStatus;
import com.hashedin.order_service.model.PaymentStatus;
import com.hashedin.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@OpenAPIDefinition(
        info = @Info(title = " Order Management System")
)
@Tag(name = "Order Control")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @Operation(summary = "Place Orders ", description = " To place order after providing restaurant id and items list")
    public Order placeOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.placeOrder(orderRequestDTO.getRestaurantId(), orderRequestDTO.getUserId(), orderRequestDTO.getItemsIdsWithQuantity());
    }

    @PutMapping("/{orderId}/order-status")
    @Operation(summary = " Update order status", description = " To update the order status ")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus){
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    @PutMapping("/{orderId}/payment-status")
    @Operation(summary = " Update payment status", description = " To update the payment status")
    public Order updatePaymentStatus(@PathVariable Long orderId, @RequestParam PaymentStatus paymentStatus){
        return orderService.updatePaymentStatus(orderId, paymentStatus);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = " Get order details ", description = " To get order details by using order id")
    public Order getOrderById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }
}
