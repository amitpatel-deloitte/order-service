package com.hashedin.order_service.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private int item_id;
    private String item_name;
    private double price;
    private boolean isAvailable;
}
