package com.example.shopapp.service.impl;

import com.example.shopapp.dto.ProductDto;
import com.example.shopapp.dto.ProductImageDto;
import com.example.shopapp.exception.InvalidParamException;
import com.example.shopapp.model.Category;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.repository.ProductImageRepository;
import com.example.shopapp.repository.ProductRepository;
import com.example.shopapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Override
    public Product createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("The category with id " + productDto.getCategoryId() + " doesn't exist"));
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .category(category)
                .thumbnail(productDto.getThumbnail())
                .build();
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
        Category category = categoryRepository.findById(productDto.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Cannot find category with id: " + productDto.getCategoryId()));
        product.setCategory(category);
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setThumbnail(productDto.getThumbnail());
        product.setDescription(productDto.getDescription());
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
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws InvalidParamException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Cannot find product with id: " + productId));

        ProductImage newProductImage = ProductImage.builder()
                .imageUrl(productImageDto.getImageUrl())
                .product(product)
                .build();
        // Khong cho insert qua 5 anh cho 1 san pham
         int size = productImageRepository.findByProductId(productId).size();
         if (size >= 5) {
             throw new InvalidParamException("Number of images must be <= 5");
         }
        return productImageRepository.save(newProductImage);
    }
}
