package com.bookstore.dto;

import com.bookstore.entity.Order;
import com.bookstore.enumClassess.PaymentStatus;
import com.bookstore.enumClassess.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private List<OrderItemResponseDTO> items;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;

    public static OrderResponseDTO fromEntity(Order order) {
        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream()
                .map(OrderItemResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getName(),
                order.getUser().getEmail(),
                itemDTOs,
                order.getTotalPrice(),
                order.getStatus(),
                order.getPaymentStatus(),
                order.getCreatedAt()
        );
    }
}
