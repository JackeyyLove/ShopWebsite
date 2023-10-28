package com.example.shopapp.controller;

import com.example.shopapp.dto.CategoryDto;
import com.example.shopapp.dto.ProductDto;
import jakarta.validation.Path;
import jakarta.validation.Valid;
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
@RequestMapping("${api.prefix}/products")
public class ProductController {
    // Build Get all products REST API
    @GetMapping()
    public ResponseEntity<String> getAllProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return ResponseEntity.ok("Get all products");
    }

    // Build get specific product REST API
    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok("Get the product with id " + id) ;

    }

    //http://localhost:8088/api/v1/products
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDto productDto,
                                           //@RequestPart("file") MultipartFile file,
                                           BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages =  bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<MultipartFile> files = productDto.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
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
                    // Save into product in DB -> do it later
                }
            }

            return ResponseEntity.ok("Product created successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
}
