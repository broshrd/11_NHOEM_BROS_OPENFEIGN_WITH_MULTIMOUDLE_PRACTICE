package org.example.service.orderServiceImpl;

import org.example.client.CustomerFeignClient;
import org.example.client.ProductFeignClient;
import org.example.model.Order;
import org.example.model.request.OrderRequest;
import org.example.model.response.ApiResponse;
import org.example.model.response.CustomerDto;
import org.example.model.response.OrderResponse;
import org.example.model.response.ProductDto;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductFeignClient productFeignClient;
    private final CustomerFeignClient customerFeignClient;

    public OrderServiceImpl(OrderRepository orderRepository, ProductFeignClient productFeignClient, CustomerFeignClient customerFeignClient) {
        this.orderRepository = orderRepository;
        this.productFeignClient = productFeignClient;
        this.customerFeignClient = customerFeignClient;
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Fetch customer details
        ApiResponse<CustomerDto> customerDtoApiResponse = customerFeignClient.getCustomerById(orderRequest.getCustomerId()).getBody();
        assert customerDtoApiResponse != null;
        CustomerDto customerDto = customerDtoApiResponse.getPayload();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Long productId:orderRequest.getProductIds()){
            productDtos.add(Objects.requireNonNull(productFeignClient.getproductById(productId).getBody()).getPayload());
        }
//        System.out.println("customer :"+customerDto);
//        System.out.println("product :"+productDtos);
        // Create order
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setProductIds(orderRequest.getProductIds());
        order.setOrderDate(orderRequest.getOrderDate());

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Prepare response data
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(savedOrder.getId());
        orderResponse.setCustomerId(customerDto);
        orderResponse.setOrderDate(savedOrder.getOrderDate());
        orderResponse.setProductIds(productDtos);
        return orderResponse;
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).get();
        CustomerDto customerDto = customerFeignClient.getCustomerById(order.getCustomerId()).getBody().getPayload();
        List<ProductDto> productDtos = order.getProductIds().stream().map(productId -> {
            ApiResponse<ProductDto> productDtoApiResponse = productFeignClient.getproductById(productId).getBody();
            assert productDtoApiResponse != null;
            return productDtoApiResponse.getPayload();
        }).collect(Collectors.toList());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setCustomerId(customerDto);
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setProductIds(productDtos);
        return orderResponse;
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderDate(LocalDate.now());
        order.setProductIds(orderRequest.getProductIds());
        order.setCustomerId(orderRequest.getCustomerId());
        List<ProductDto> productDtos = new ArrayList<>();

        for(Long productId:order.getProductIds()){
            productDtos.add(Objects.requireNonNull(productFeignClient.getproductById(productId).getBody()).getPayload());
        }
        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(savedOrder.getId());
        orderResponse.setCustomerId(customerFeignClient.getCustomerById(order.getCustomerId()).getBody().getPayload());
        orderResponse.setOrderDate(savedOrder.getOrderDate());
        orderResponse.setProductIds(productDtos);

        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(order -> {
            CustomerDto customerDto = customerFeignClient.getCustomerById(order.getCustomerId()).getBody().getPayload();
            List<ProductDto> productDtos = order.getProductIds().stream().map(productId -> {
                ApiResponse<ProductDto> productDtoApiResponse = productFeignClient.getproductById(productId).getBody();
                assert productDtoApiResponse != null;
                return productDtoApiResponse.getPayload();
            }).collect(Collectors.toList());
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(order.getId());
            orderResponse.setCustomerId(customerDto);
            orderResponse.setOrderDate(order.getOrderDate());
            orderResponse.setProductIds(productDtos);
            return orderResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public String deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return "order ID: "+id+" deleted successfully";
    }


}
