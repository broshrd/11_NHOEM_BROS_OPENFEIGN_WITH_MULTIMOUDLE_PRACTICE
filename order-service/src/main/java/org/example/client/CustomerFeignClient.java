package org.example.client;
import org.example.model.response.ApiResponse;
import org.example.model.response.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://localhost:8083/api/v1/customer")
public interface CustomerFeignClient {
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<CustomerDto>> getCustomerById(@PathVariable Long id);
}
