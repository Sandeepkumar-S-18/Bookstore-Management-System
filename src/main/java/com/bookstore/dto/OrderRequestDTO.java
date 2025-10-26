package com.bookstore.dto;

import com.bookstore.entity.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequestDTO> items;

    public static Order toEntity(OrderRequestDTO dto, User user, List<Book> books) {
        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = dto.getItems().stream().map(itemDTO -> {
            Book book = books.stream()
                    .filter(b -> b.getId().equals(itemDTO.getBookId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Book not found with ID: " + itemDTO.getBookId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setTotalPrice(book.getPrice().multiply(
                    java.math.BigDecimal.valueOf(itemDTO.getQuantity()))
            );

            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        return order;
    }
}
