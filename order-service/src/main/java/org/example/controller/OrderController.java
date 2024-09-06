package org.example.controller;

import org.example.model.request.OrderRequest;
import org.example.model.response.ApiResponse;
import org.example.model.response.OrderResponse;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    private ResponseEntity<ApiResponse<OrderResponse>> createOrder(OrderRequest orderRequest) {
        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .message("Order Created")
                .payload(orderService.createOrder(orderRequest))
                .status(HttpStatus.CREATED)
                .localdatetime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse<OrderResponse>> getOrderById(Long id) {
        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .message("Order Found")
                .payload(orderService.getOrderById(id))
                .status(HttpStatus.OK)
                .localdatetime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/{id}")
    private ResponseEntity<ApiResponse<OrderResponse>> updateOrder( @RequestBody OrderRequest orderRequest,@PathVariable Long id) {
        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .message("Order Updated")
                .payload(orderService.updateOrder(orderRequest,id))
                .status(HttpStatus.OK)
                .localdatetime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping
    private ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        ApiResponse<List<OrderResponse>> response = ApiResponse.<List<OrderResponse>>builder()
                .message("Orders Found")
                .payload(orderService.getAllOrders())
                .status(HttpStatus.OK)
                .localdatetime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable Long id) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("Order Deleted")
                .payload(orderService.deleteOrder(id))
                .status(HttpStatus.OK)
                .localdatetime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response,response.getStatus());
    }
}
