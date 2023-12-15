package com.enigma.wmsapp.service;

import com.enigma.wmsapp.dto.request.BillRequest;
import com.enigma.wmsapp.dto.response.BillResponse;

import java.util.List;

public interface BillService {
    BillResponse createNewBill(BillRequest billRequest);
    BillResponse getBillById (String id);
    List<BillResponse> getAllBill();

}
