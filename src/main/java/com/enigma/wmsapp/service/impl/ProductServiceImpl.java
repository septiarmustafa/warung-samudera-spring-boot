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
    public ProductResponse updateProduct(ProductRequest productRequest) {
        BranchResponse branchResponse = branchService.getByIdBranch(productRequest.getBranchId().getId());

        Optional<Product> optionalProduct = productRepository.findById(productRequest.getProductId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            product.setProductCode(productRequest.getProductCode());
            product.setProductName(productRequest.getProductName());

            productRepository.save(product);

            ProductPrice productPrice = product.getProductPrice().isEmpty()
                    ? new ProductPrice()
                    : product.getProductPrice().get(0);

            productPrice.setPrice(productRequest.getPrice());
            productPrice.setIsActive(true);
            productPrice.setBranch(Branch.builder().branchId(branchResponse.getBranchId()).build());
            productPrice.setProduct(product);

            productPriceService.createOrUpdate(productPrice);

            return ProductResponse.builder()
                    .productId(product.getId())
                    .productPriceId(productPrice.getId())
                    .productCode(product.getProductCode())
                    .productName(product.getProductName())
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
        } else {
            return null;
        }

    }

    @Override
    public void deleteProduct(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            List<ProductPrice> productPrices = product.getProductPrice();
            for (ProductPrice productPrice : productPrices) {
                productPriceService.delete(productPrice.getId());
            }
            productRepository.deleteById(id);
        }
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
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            Join<Product, ProductPrice> productPriceJoin = root.join("productPrice");

            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%" ));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPriceJoin.get("price"), maxPrice));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = productRepository.findAll(specification,pageable);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()) {
            Optional<ProductPrice> productPrice = product.getProductPrice()
                    .stream()
                    .filter(ProductPrice::getIsActive).findFirst();
            if (productPrice.isEmpty()) continue;
            Branch branch = productPrice.get().getBranch();
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
