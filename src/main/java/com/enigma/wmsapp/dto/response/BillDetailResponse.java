package com.enigma.wmsapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BillDetailResponse {
    private String billDetailId;
    private ProductResponse product;
    private Integer quantity;
    private Long totalSales;
}
