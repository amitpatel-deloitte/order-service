package com.hashedin.order_service.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private int menu_id;
    private String name;
    private List<ItemDTO> items;
}
