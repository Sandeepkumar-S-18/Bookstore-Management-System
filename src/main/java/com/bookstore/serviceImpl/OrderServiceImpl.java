package com.bookstore.serviceImpl;

import com.bookstore.dto.*;
import com.bookstore.entity.*;
import com.bookstore.enumClassess.OrderStatus;
import com.bookstore.enumClassess.PaymentStatus;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.*;
import com.bookstore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    // -------------------- Place Order --------------------
    @Override
    public OrderResponseDTO placeOrder(Long userId, OrderRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Book> books = bookRepository.findAllById(
                request.getItems().stream().map(OrderItemRequestDTO::getBookId).toList()
        );

        // Validate stock before proceeding
        for (OrderItemRequestDTO item : request.getItems()) {
            Book book = books.stream()
                    .filter(b -> b.getId().equals(item.getBookId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Book not found with ID: " + item.getBookId()));
            if (book.getStockQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for book: " + book.getTitle() + ". Available: " + book.getStockQuantity());
            }
        }

        Order order = OrderRequestDTO.toEntity(request, user, books);

        BigDecimal totalPrice = order.getItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);

        Order saved = orderRepository.save(order);

        saved.getItems().forEach(item -> {
            Book b = item.getBook();
            b.setStockQuantity(b.getStockQuantity() - item.getQuantity());
            bookRepository.save(b);
        });

        return OrderResponseDTO.fromEntity(saved);
    }

    // -------------------- Get Order By Id --------------------
    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return OrderResponseDTO.fromEntity(order);
    }

    // -------------------- Get All Orders (Admin) --------------------
    @Override
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderResponseDTO::fromEntity);
    }

    // -------------------- Get Orders by User --------------------
    @Override
    public Page<OrderResponseDTO> getOrdersByUser(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable).map(OrderResponseDTO::fromEntity);
    }

    // -------------------- Update Order Status (Admin) --------------------
    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Validate status
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status + ". Valid statuses: PENDING, SHIPPED, DELIVERED");
        }

        Order saved = orderRepository.save(order);
        return OrderResponseDTO.fromEntity(saved);
    }
}
