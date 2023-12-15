package com.enigma.wmsapp.service.impl;

import com.enigma.wmsapp.dto.request.ProductRequest;
import com.enigma.wmsapp.dto.response.BranchResponse;
import com.enigma.wmsapp.dto.response.ProductResponse;
import com.enigma.wmsapp.entity.Branch;
import com.enigma.wmsapp.entity.Product;
import com.enigma.wmsapp.entity.ProductPrice;
import com.enigma.wmsapp.repository.ProductRepository;
import com.enigma.wmsapp.service.BranchService;
import com.enigma.wmsapp.service.ProductPriceService;
import com.enigma.wmsapp.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BranchService branchService;
    private final ProductPriceService productPriceService;


    @Override
    public ProductResponse getByIdProduct(String id) {
        Product product = productRepository.findById(id).orElse(null);
        return product == null ? null : ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .build();
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(product -> Product.builder()
                        .id(product.getId())
                        .productName(product.getProductName())
                        .productCode(product.getProductCode())
                        .productPrice(product.getProductPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Product product) {
        Product products = productRepository.findById(product.getId()).orElse(null);
        if (products == null) return null;
        productRepository.save(products);
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .build();
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest) {
        BranchResponse branchResponse = branchService.getByIdBranch(productRequest.getBranchId().getId());

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .productCode(productRequest.getProductCode())
                .build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .isActive(true)
                .product(product)
                .branch(Branch.builder()
                        .branchId(branchResponse.getBranchId())
                        .build())
                .build();

        productPriceService.create(productPrice);
        return ProductResponse.builder()
                .productId(product.getId())
                .productPriceId(productPrice.getId())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .branchId(BranchResponse.builder()
                        .branchId(branchResponse.getBranchId())
                        .branchCode(branchResponse.getBranchCode())
                        .branchName(branchResponse.getBranchName())
                        .address(branchResponse.getAddress())
                        .phoneNumber(branchResponse.getPhoneNumber())
                        .build())
                .build();
    }

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        // specification untuk menentukan kriteria pencarian, disini criteria pencarian ditandakan dengan root, root yang dimaksud adalah entity product
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            // join digunakan untuk merelasikan antara product dan product price
            Join<Product, ProductPrice> productPriceJoin = root.join("productPrice");
            // predicate digunakan untuk menggunakanLIKE dimana nanti kita akan menggunakan kondisi pencarian parameter
            // disini kita akan mencari nama produk atau harga yang sama atau harga dibawahnya, makanya menggunakan LessThanOrEquals
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%" ));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPriceJoin.get("price"), maxPrice));
            }

            // kode return mengembalikan query dimana pada dasarnya kita membangun klause where yang sudah ditentukan dari predicate atau criteria
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(page, size);

        // pageabale sebuah interface dari package spring framwork
        //Cara paling umum untuk membuat sebuah Pageable instance adalah dengan menggunakan PageRequest implementasi:

        Page<Product> products = productRepository.findAll(specification,pageable);
        // ini digunakan untuk menyimpan response product yang baru
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()) {
            // for disini digunakan untuk mengiterasi daftar product yang disimpan dalam object
            Optional<ProductPrice> productPrice = product.getProductPrice() // optional ini untuk mencari harga yang aktif
                    .stream()
                    .filter(ProductPrice::getIsActive).findFirst();
            if (productPrice.isEmpty()) continue; // kondisi ini digunakan untuk memeriksa apakah productPricenya kosong atau tidak, jika kosong maka di skip
            Branch branch = productPrice.get().getBranch(); // ini digunakan untuk jika harga product yang aktif ditemukan di store
            productResponses.add(
                    ProductResponse.builder()
                            .productId(product.getId())
                            .productPriceId(productPrice.get().getId())
                            .productName(product.getProductName())
                            .productCode(product.getProductCode())
                            .price(productPrice.get().getPrice())
                            .stock(productPrice.get().getStock())
                            .branchId(BranchResponse.builder()
                                    .branchId(branch.getBranchId())
                                    .branchName(branch.getBranchName())
                                    .branchCode(branch.getBranchCode())
                                    .address(branch.getAddress())
                                    .phoneNumber(branch.getPhoneNumber())
                                    .build())
                    .build());
        }
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }
}
