package com.bookstore.controller;

import com.bookstore.dto.OrderRequestDTO;
import com.bookstore.dto.OrderResponseDTO;
import com.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // -------------------- 🛒 Place an Order (Customer Only) --------------------
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @PathVariable Long userId,
            @RequestBody OrderRequestDTO orderRequestDTO
    ) {
        OrderResponseDTO response = orderService.placeOrder(userId, orderRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // -------------------- 📦 Get a Specific Order (Customer or Admin) --------------------
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // -------------------- 📋 Get All Orders (Admin Only) --------------------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    // -------------------- 👤 Get All Orders for a User (Customer or Admin) --------------------
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderResponseDTO>> getOrdersByUser(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId, pageable));
    }

    // -------------------- 🔄 Update Order Status (Admin Only) --------------------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}
