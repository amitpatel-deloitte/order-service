package com.hashedin.order_service.client;

import com.hashedin.order_service.model.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", url = "http://localhost:8082")
public interface RestaurantClient {

    @GetMapping("/restaurant/{restaurantId}")
    RestaurantDTO getRestaurantById(@PathVariable int restaurantId);
}
