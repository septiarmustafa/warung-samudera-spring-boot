package com.enigma.wmsapp.controller;

import com.enigma.wmsapp.constant.AppPath;
import com.enigma.wmsapp.dto.request.BillRequest;
import com.enigma.wmsapp.dto.response.BillResponse;
import com.enigma.wmsapp.dto.response.CommonResponse;
import com.enigma.wmsapp.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.TRANSACTIONS)
public class BillController {

    private final BillService billService;
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody BillRequest billRequest) {
        BillResponse orderResponse = billService.createNewBill(billRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new bill")
                        .data(orderResponse).build());
    }
}
