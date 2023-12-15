package com.enigma.wmsapp.controller;

import com.enigma.wmsapp.constant.AppPath;
import com.enigma.wmsapp.dto.request.ProductRequest;
import com.enigma.wmsapp.dto.response.BranchResponse;
import com.enigma.wmsapp.dto.response.CommonResponse;
import com.enigma.wmsapp.dto.response.ProductResponse;
import com.enigma.wmsapp.entity.Branch;
import com.enigma.wmsapp.entity.Product;
import com.enigma.wmsapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.PRODUCTS)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProductAndProductPrice(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ProductResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new product")
                        .data(productResponse).build());
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct (){
        List<Product> productResponse = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully get all product")
                        .data(productResponse).build());
    }

    // get product by branch

    @PutMapping
    public ResponseEntity<?> updateProduct (@RequestBody Product product) {
        ProductResponse productResponse = productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update product")
                        .data(productResponse).build());
    }

    @DeleteMapping(value = AppPath.ID_PRODUCT)
    public ResponseEntity<?> deleteBranch (@PathVariable String id_product){
        productService.deleteProduct(id_product);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete product")
                        .data("OK").build());
    }
}
