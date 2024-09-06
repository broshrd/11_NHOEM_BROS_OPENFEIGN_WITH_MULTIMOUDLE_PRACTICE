package org.example.controller;

import org.example.model.request.ProductRequest;
import org.example.model.response.ApiResponse;
import org.example.model.response.ProductDto;
import org.example.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductRequest productRequest) {
        ApiResponse<ProductDto> response = productService.createProduct(productRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProducts(@PathVariable Long id) {
        ApiResponse<ProductDto> response = productService.getProduct(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        ApiResponse<ProductDto> response = productService.updateProduct(id, productRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> deleteProduct(@PathVariable Long id) {
        ApiResponse<ProductDto> response = productService.deleteProduct(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {
        ApiResponse<List<ProductDto>> response = productService.getAllProducts();
        return new ResponseEntity<>(response, response.getStatus());
    }
}
