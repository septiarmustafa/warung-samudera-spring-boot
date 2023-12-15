package com.enigma.wmsapp.service.impl;

import com.enigma.wmsapp.dto.request.BillRequest;
import com.enigma.wmsapp.dto.response.BillDetailResponse;
import com.enigma.wmsapp.dto.response.BillResponse;
import com.enigma.wmsapp.dto.response.BranchResponse;
import com.enigma.wmsapp.dto.response.ProductResponse;
import com.enigma.wmsapp.entity.Bill;
import com.enigma.wmsapp.entity.BillDetail;
import com.enigma.wmsapp.entity.ProductPrice;
import com.enigma.wmsapp.repository.BillRepository;
import com.enigma.wmsapp.service.BillService;
import com.enigma.wmsapp.service.ProductPriceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    final BillRepository billRepository;
    final ProductPriceService productPriceService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BillResponse createNewBill(BillRequest billRequest) {

        List<BillDetail> billDetailsList = billRequest.getBillDetailList().stream().map(billDetailRequest -> {

            ProductPrice productPrice = productPriceService.getById(billDetailRequest.getProductPriceId());
            return BillDetail.builder()
                    .productPrice(productPrice)
                    .quantity(billDetailRequest.getQuantity())
                    .build();
        }).toList();

        Bill bill = Bill.builder()
                .receiptNumber("12" + "-" + LocalDateTime.now().getYear() + "-" + "1")
                .transDate(LocalDateTime.now())
                .transactionType(billRequest.getTransactionType())
                .billDetail(billDetailsList)
                .build();
        billRepository.saveAndFlush(bill);

        List<BillDetailResponse> billDetailResponses = bill.getBillDetail().stream().map(billDetail -> {
            billDetail.setBill(bill);
            System.out.println(bill);

            ProductPrice currentProductPrice = billDetail.getProductPrice();
            currentProductPrice.setStock(currentProductPrice.getStock() - billDetail.getQuantity());
            return BillDetailResponse.builder()
                    .billDetailId(billDetail.getId())
                    .quantity(billDetail.getQuantity())
                    .product(ProductResponse.builder()
                            .productId(currentProductPrice.getProduct().getId())
                            .productPriceId(currentProductPrice.getProduct().getId())
                            .productName(currentProductPrice.getProduct().getProductName())
                            .productCode(currentProductPrice.getProduct().getProductCode())
                            .stock(currentProductPrice.getStock())
                            .price(currentProductPrice.getPrice())
                            .branchId(BranchResponse.builder()
                                    .branchId(currentProductPrice.getBranch().getBranchId())
                                    .branchName(currentProductPrice.getBranch().getBranchName())
                                    .branchCode(currentProductPrice.getBranch().getBranchCode())
                                    .address(currentProductPrice.getBranch().getAddress())
                                    .phoneNumber(currentProductPrice.getBranch().getPhoneNumber())
                                    .build())
                            .build())
                    .build();
        }).toList();


        // TODO 10 : Return OrderResponse
        return BillResponse.builder()
                .billId(bill.getId())
                .transDate(bill.getTransDate())
                .receiptNumber(bill.getReceiptNumber())
                .transactionType(bill.getTransactionType())
                .billDetailList(billDetailResponses)
                .build();
    }

    @Override
    public BillResponse getBillById(String id) {
        return null;
    }

    @Override
    public List<BillResponse> getAllBill() {
        return null;
    }

}
