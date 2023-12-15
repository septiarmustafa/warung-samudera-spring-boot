package com.enigma.wmsapp.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductRequest {
        private String productId;
        private String productCode;
        private String productName;

        @NotBlank(message = "price is required")
        @Min(value = 0, message = "price must be greater than equal 0")
        private Long price;

        @NotBlank(message = "stock is required")
        @Min(value = 0, message = "stock must be greater than equal 0")
        private Integer stock;

        @NotBlank(message = "branchId is required")
        private BranchRequest branchId;



}
