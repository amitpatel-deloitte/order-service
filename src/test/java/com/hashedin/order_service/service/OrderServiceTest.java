package com.hashedin.order_service.service;

import static org.junit.jupiter.api.Assertions.*;

import com.hashedin.order_service.ExceptionHandler.NotFoundException;
import com.hashedin.order_service.client.RestaurantClient;
import com.hashedin.order_service.model.*;
import com.hashedin.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private RestaurantClient restaurantClient;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrderSuccess(){
        Map<Integer, Integer> itemQuantityMap =  new HashMap<>();
        itemQuantityMap.put(1,2);
        itemQuantityMap.put(2,3);

        ItemDTO item1 = new ItemDTO(1, "Paneer", 200, true);
        ItemDTO item2 = new ItemDTO(2, "Tea", 50, true);

        List<ItemDTO> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);

        MenuDTO menu = new MenuDTO(23, "Lunch", itemList);
        List<MenuDTO> menus = new ArrayList<>();
        menus.add(menu);

        RestaurantDTO restaurant = new RestaurantDTO(21, "Punjabi dhaba", "Mumbai", menus);
        Order expectedOrder = new Order(1,522,21, OrderStatus.PENDING, PaymentStatus.PENDING, Arrays.asList(1,2), 550.0, LocalDateTime.now());
        when(restaurantClient.getRestaurantById(21)).thenReturn(restaurant);
        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);

        Order actualOrder = orderService.placeOrder(21, 522, itemQuantityMap);

        assertNotNull(actualOrder);
        assertEquals(550.0, actualOrder.getTotalPrice());
        assertEquals(OrderStatus.PENDING, actualOrder.getOrderStatus());
        assertEquals(PaymentStatus.PENDING, actualOrder.getPaymentStatus());

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(restaurantClient, times(1)).getRestaurantById(21);

    }

    @Test
    public void testPlaceOrder_RestaurantNotFound(){
        when(restaurantClient.getRestaurantById(21)).thenReturn(null);
        Map<Integer, Integer> itemQuantityMap =  new HashMap<>();
        itemQuantityMap.put(1,2);
        itemQuantityMap.put(2,3);

        Exception exception = assertThrows(NotFoundException.class, () ->  {
            orderService.placeOrder(21, 522, itemQuantityMap);
        });

        assertEquals( " Restaurant with id: 21 not found", exception.getMessage());

    }
}
