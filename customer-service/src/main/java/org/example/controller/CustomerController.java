package org.example.controller;

import org.example.model.dto.request.CustomerRequest;
import org.example.model.dto.response.ApiResponse;
import org.example.model.dto.response.CustomerDto;
import org.example.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    private ResponseEntity<ApiResponse<CustomerDto>> createCustomer(@RequestBody CustomerRequest customerRequest){
        ApiResponse<CustomerDto> response = customerService.createCustomer(customerRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse<CustomerDto>> getCustomer(@PathVariable("id") Long id){
        ApiResponse<CustomerDto> response = customerService.getCustomer(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PutMapping("/{id}")
    private ResponseEntity<ApiResponse<CustomerDto>> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerRequest customerRequest){
        ApiResponse<CustomerDto> response = customerService.updateCustomer(id, customerRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<ApiResponse<CustomerDto>> deleteCustomer(@PathVariable Long id){
        ApiResponse<CustomerDto> response = customerService.deleteCustomer(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping
    private ResponseEntity<ApiResponse<List<CustomerDto>>> getAllCustomers(){
        ApiResponse<List<CustomerDto>> response = customerService.getAllCustomers();
        return new ResponseEntity<>(response, response.getStatus());
    }
}
