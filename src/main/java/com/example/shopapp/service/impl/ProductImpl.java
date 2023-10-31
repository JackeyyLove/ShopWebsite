package com.example.shopapp.service.impl;

import com.example.shopapp.dto.ProductDto;
import com.example.shopapp.model.Category;
import com.example.shopapp.model.Product;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.repository.ProductRepository;
import com.example.shopapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class ProductImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Override
    public Product createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("The category with id " + productDto.getCategoryId() + " doesn't exist"));
        Product product = modelMapper.map(productDto, Product.class);
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Cannot found product with id: " + id)));
        return product;
    }

    @Override
    public Page<Product> getAllProduct(PageRequest pageRequest) {
        // Get product by page and limit
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find product with id: " + id));
        product = modelMapper.map(productDto, Product.class);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find product with id: " + id));
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
