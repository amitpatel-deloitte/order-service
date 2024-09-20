package com.hashedin.order_service.service;

import com.hashedin.order_service.ExceptionHandler.NotAllowedException;
import com.hashedin.order_service.ExceptionHandler.NotFoundException;
import com.hashedin.order_service.client.RestaurantClient;
import com.hashedin.order_service.model.*;
import com.hashedin.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantClient restaurantClient;

    public Order placeOrder(int restaurantId, int userId, Map<Integer, Integer> itemsIdWithQuantity) {
        RestaurantDTO restaurantDTO = restaurantClient.getRestaurantById(restaurantId);
        if( restaurantDTO == null ){
            throw new NotFoundException(" Restaurant with id: " + restaurantId + " not found");
        }
        List<MenuDTO> menus = restaurantDTO.getMenus();
        if(menus == null){
            throw new NotFoundException(" No menu associated with restaurant id: " + restaurantId);
        }
        List<ItemDTO> validItems = new ArrayList<>();
        Set<Integer> itemIds = itemsIdWithQuantity.keySet();
        for(MenuDTO menu : menus){
            validItems.addAll(menu.getItems().stream()
                    .filter(item -> itemIds.contains(item.getItem_id()))
                    .toList());
        }
        if(validItems.size() != itemIds.size()){
            throw new NotFoundException(" Some item ids are not in the restaurant menu ");
        }

        double total_price = validItems.stream()
                .mapToDouble(item -> item.getPrice() * itemsIdWithQuantity.get(item.getItem_id()))
                .sum();

        Order order = new Order();
        order.setRestaurant_id(restaurantId);
        order.setUser_id(userId);
        order.setItemIds(new ArrayList<>(itemIds));
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setTotalPrice(total_price);
        order.setTimeStamp(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(" Order with id :  " + orderId + " not found"));

        if((order.getPaymentStatus() != PaymentStatus.PAID )&& ( orderStatus == OrderStatus.ACCEPTED ||  orderStatus == OrderStatus.PLACED
        ||  orderStatus == OrderStatus.PREPARING ||  orderStatus == OrderStatus.OUT_FOR_DELIVERY || orderStatus == OrderStatus.DELIVERED )) {
            throw new NotAllowedException( " Setting order status : " + orderStatus + " Not allowed as Payment is still pending ");
        }
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    public Order updatePaymentStatus( Long orderId, PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(" Order with id :  " + orderId + " not found"));

        if(paymentStatus == PaymentStatus.PAID){
            order.setOrderStatus(OrderStatus.PLACED);
        }

        if(paymentStatus == PaymentStatus.FAILED){
            order.setOrderStatus(OrderStatus.PAYMENT_FAILED);
        }

        order.setPaymentStatus(paymentStatus);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(" Order with id :  " + orderId + " not found"));
    }
}
