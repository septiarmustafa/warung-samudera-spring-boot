package com.enigma.wmsapp.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BranchResponse {
    private String branchId;
    private String branchCode;
    private String branchName;
    private String address;
    private String phoneNumber;
}

//{
//        "errors": "string",
//        "data": {
//        "branchId": "string",
//        "branchCode": "string",
//        "branchName": "string",
//        "address": "string",
//        "phoneNumber": "string"
//        },
//        "paging": null
//        }
