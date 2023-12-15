package com.enigma.wmsapp.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {
    private String productId;
    private String productPriceId;
    private String productCode;
    private String productName;
    private String price;
    private String branchId;
}

//{
//        "errors": "string",
//        "data": {
//        "productId": "string",
//        "productPriceId": "string",
//        "productCode": "string",
//        "productName": "string",
//        "price": "big decimal",
//        "branch": {
//        "branchId": "string",
//        "branchCode": "string",
//        "branchName": "string",
//        "address": "string",
//        "phoneNumber": "string"
//        }
//        },
//        "paging": null
//        }
