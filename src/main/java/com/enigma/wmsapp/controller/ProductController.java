package com.enigma.wmsapp.controller;

import com.enigma.wmsapp.constant.AppPath;
import com.enigma.wmsapp.dto.request.ProductRequest;
import com.enigma.wmsapp.dto.response.CommonResponse;
import com.enigma.wmsapp.dto.response.PagingResponse;
import com.enigma.wmsapp.dto.response.ProductResponse;
import com.enigma.wmsapp.entity.Product;
import com.enigma.wmsapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping(value = AppPath.PAGE)
    public  ResponseEntity<?> getAllProductPage(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size
    ){
        Page<ProductResponse> productResponses = productService.getAllByNameOrPrice(name, maxPrice, page,size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(productResponses.getTotalPages())
                .size(size)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all product")
                        .data(productResponses.getContent())
                        .paging(pagingResponse)
                        .build());
    }

    @PutMapping
    public ResponseEntity<?> updateProduct (@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(productRequest);
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
