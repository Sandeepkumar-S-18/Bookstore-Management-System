package com.bookstore.dto;

import com.bookstore.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long bookId;
    private String title;
    private int quantity;
    private BigDecimal totalPrice;

    public static OrderItemResponseDTO fromEntity(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getBook().getId(),
                item.getBook().getTitle(),
                item.getQuantity(),
                item.getTotalPrice()
        );
    }
}
