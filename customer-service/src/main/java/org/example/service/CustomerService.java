package org.example.service;

import org.example.model.dto.request.CustomerRequest;
import org.example.model.dto.response.ApiResponse;
import org.example.model.dto.response.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    ApiResponse<CustomerDto> createCustomer(CustomerRequest customerRequest);

    ApiResponse<CustomerDto> getCustomer(Long id);

    ApiResponse<CustomerDto> updateCustomer(Long id, CustomerRequest customerRequest);

    ApiResponse<CustomerDto> deleteCustomer(Long id);

    ApiResponse<List<CustomerDto>> getAllCustomers();
}
