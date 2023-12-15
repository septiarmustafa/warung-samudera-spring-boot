package com.enigma.wmsapp.service.impl;

import com.enigma.wmsapp.entity.ProductPrice;
import com.enigma.wmsapp.repository.ProductPriceRepository;
import com.enigma.wmsapp.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductPriceRepository productPriceRepository;
    @Override
    public ProductPrice create(ProductPrice productPrice) {
        return productPriceRepository.save(productPrice);
    }

    @Override
    public ProductPrice createOrUpdate(ProductPrice productPrice) {
        return productPrice.getId() == null ? productPriceRepository.save(productPrice) : productPriceRepository.saveAndFlush(productPrice);
    }

    @Override
    public ProductPrice getById(String id) {
        return productPriceRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(String id) {
        productPriceRepository.deleteById(id);
    }

}
