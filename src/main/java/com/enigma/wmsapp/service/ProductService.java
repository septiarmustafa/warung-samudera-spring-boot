package com.enigma.wmsapp.service;

import com.enigma.wmsapp.dto.request.ProductRequest;
import com.enigma.wmsapp.dto.response.ProductResponse;
import com.enigma.wmsapp.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponse getByIdProduct (String id);

    List<Product> getAllProduct ();

    ProductResponse updateProduct (ProductRequest productRequest);
    void deleteProduct (String id);

    ProductResponse createProductAndProductPrice (ProductRequest productRequest);

    Page<ProductResponse> getAllByNameOrPrice (String productName, String productCode, Long maxPrice, Long minPrice, Integer page, Integer size);
}
