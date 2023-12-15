package com.enigma.wmsapp.service;

import com.enigma.wmsapp.entity.ProductPrice;

public interface ProductPriceService {
   ProductPrice create (ProductPrice productPrice);
   ProductPrice createOrUpdate (ProductPrice productPrice);
   ProductPrice getById (String id);
   void delete (String id);
}
