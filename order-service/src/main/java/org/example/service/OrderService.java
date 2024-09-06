package org.example.service;

import org.example.model.request.OrderRequest;
import org.example.model.response.ApiResponse;
import org.example.model.response.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

   OrderResponse createOrder(OrderRequest orderRequest);

   OrderResponse getOrderById(Long id);

   OrderResponse updateOrder(OrderRequest orderRequest,Long id);

    List<OrderResponse> getAllOrders();

   String deleteOrder(Long id);
}
