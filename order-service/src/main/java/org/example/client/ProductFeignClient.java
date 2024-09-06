package org.example.client;

import org.apache.catalina.authenticator.SavedRequest;
import org.example.model.response.ApiResponse;
import org.example.model.response.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8085/api/v1/products")
public interface ProductFeignClient {
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<ProductDto>> getproductById(@PathVariable("id") Long id);
}
