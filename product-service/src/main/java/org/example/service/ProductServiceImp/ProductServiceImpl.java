package org.example.service.ProductServiceImp;

import org.example.model.Product;
import org.example.model.request.ProductRequest;
import org.example.model.response.ApiResponse;
import org.example.model.response.ProductDto;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ApiResponse<ProductDto> createProduct(ProductRequest productRequest) {
        Product product =new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return new ApiResponse<>("Successful",productDto, HttpStatus.CREATED, LocalDateTime.now());
    }

    @Override
    public ApiResponse<ProductDto> getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.get().getId());
            productDto.setName(product.get().getName());
            productDto.setPrice(product.get().getPrice());
            return new ApiResponse<>("Successful", productDto, HttpStatus.OK, LocalDateTime.now());
        }
        return new ApiResponse<>("Product not found", null, HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @Override
    public ApiResponse<ProductDto> updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product.get().setName(productRequest.getName());
            product.get().setPrice(productRequest.getPrice());
            productRepository.save(product.get());
            ProductDto productDto = new ProductDto();
            productDto.setId(product.get().getId());
            productDto.setName(product.get().getName());
            productDto.setPrice(product.get().getPrice());
            return new ApiResponse<>("Update product Successful", productDto, HttpStatus.OK, LocalDateTime.now());
        }
        return new ApiResponse<>("Product not found", null, HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @Override
    public ApiResponse<ProductDto> deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return new ApiResponse<>("Delete product Successful", null, HttpStatus.OK, LocalDateTime.now());
        }
        return new ApiResponse<>("Product not found", null, HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @Override
    public ApiResponse<List<ProductDto>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            return productDto;
        }).toList();
        return new ApiResponse<>("Successful", productDtos, HttpStatus.OK, LocalDateTime.now());
    }
}
