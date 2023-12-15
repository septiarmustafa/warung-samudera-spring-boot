package com.enigma.wmsapp.service;

import com.enigma.wmsapp.entity.ProductPrice;

public interface ProductPriceService {
   ProductPrice create (ProductPrice product);
   ProductPrice getById (String id);
}
