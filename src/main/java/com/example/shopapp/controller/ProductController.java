package com.example.shopapp.controller;

import com.example.shopapp.dto.CategoryDto;
import com.example.shopapp.dto.ProductDto;
import com.example.shopapp.dto.ProductImageDto;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.example.shopapp.service.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    // Build Get all products REST API
    @GetMapping()
    public ResponseEntity<?> getAllProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("createdAt").descending()
        );
        Page<ProductDto> productDtoPage = productService.getAllProduct(pageRequest);
        int totalPages = productDtoPage.getTotalPages();
        List<ProductDto> productDtos = productDtoPage.getContent();
        return ResponseEntity.ok(productDtos);
    }

    // Build get specific product REST API
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product) ;

    }

    //http://localhost:8088/api/v1/products
    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto,
                                           BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages =  bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct =  productService.createProduct(productDto);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // upload files
    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> uploadFiles(@PathVariable("id") Long productId,  @ModelAttribute("file") List<MultipartFile> files) {
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() > 5) {
                return ResponseEntity.badRequest().body("Upload maximum 5 images");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file: files) {
                if (file != null) {
                    if (file.getSize() == 0) continue;
                    // Check size of file and format
                    if (file.getSize() > 10*1024*1024) { // Size > 10MB
                        throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File is too large");
                    }
                    String contentType = file.getContentType();
                    if (contentType == null  || !contentType.startsWith("image/"))  {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body("File must be an image");
                    }
                    // Save file and update thumbnail in Dto
                    String filename = storeFile(file);
                    // Save into product in DB
                    ProductImage productImage = productService.createProductImage(existingProduct.getId(),
                            ProductImageDto.builder().imageUrl(filename).build()
                    );
                    productImages.add(productImage);
                }
            }
            return ResponseEntity.ok(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    // Store file with different names
    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Add UUID before name of file to ensure that file's name is unique
        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;
        // Path to directory that we want to store file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        //Check and create directory if it doesn't exist
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Path to the file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Copy file to destination dir
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    // Build update product REST API
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok("Update product successfully");
    }

    // Build delete category REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok("Delete product " + id + " successfully");

    }

    // Fake values for product table
    //@PostMapping("/createFakeProducts")
    private ResponseEntity<String> createFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {
            String productName = faker.commerce().productName();
            if (productService.existsByName(productName)) {
                continue;
            }
            ProductDto productDto = ProductDto.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(10, 99999))
                    .description(faker.lorem().sentence())
                    .categoryId((long)faker.number().numberBetween(1, 6))
                    .build();
            try {
                productService.createProduct(productDto);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Generated successfully");
    }
}
