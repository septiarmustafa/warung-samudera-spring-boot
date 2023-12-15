package com.enigma.wmsapp.dto.request;

import com.enigma.wmsapp.constant.ETransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BillRequest {
    private ETransactionType transactionType;
    private List<BillDetailRequest> billDetailList;
}
