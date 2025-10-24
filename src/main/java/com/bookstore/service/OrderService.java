package com.bookstore.service;

import com.bookstore.dto.OrderRequestDTO;
import com.bookstore.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDTO placeOrder(Long userId, OrderRequestDTO request);
    OrderResponseDTO getOrderById(Long id);
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);
    Page<OrderResponseDTO> getOrdersByUser(Long userId, Pageable pageable);
    OrderResponseDTO updateOrderStatus(Long orderId, String status);
}
