package com.example.shopapp.service;

import com.example.shopapp.dto.ProductDto;
import com.example.shopapp.dto.ProductImageDto;
import com.example.shopapp.exception.InvalidParamException;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService{
    public Product createProduct(ProductDto productDto);
    Product getProductById(long id);
    Page<Product> getAllProduct(PageRequest pageRequest);
    Product updateProduct(long id, ProductDto productDto);
    void deleteProduct(long id);
    boolean existsByName(String name);
    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception;
}
