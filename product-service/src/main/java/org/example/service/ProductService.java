package org.example.service;

import org.example.model.request.ProductRequest;
import org.example.model.response.ApiResponse;
import org.example.model.response.ProductDto;

import java.util.List;

public interface ProductService {
    ApiResponse<ProductDto> createProduct(ProductRequest request);

    ApiResponse<ProductDto> getProduct(Long id);

    ApiResponse<ProductDto> updateProduct(Long id, ProductRequest productRequest);

    ApiResponse<ProductDto> deleteProduct(Long id);

    ApiResponse<List<ProductDto>> getAllProducts();
}
