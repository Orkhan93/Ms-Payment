package com.example.mspayment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageablePaymentResponse {

    List<PaymentResponse> paymentResponseList;
    private Long totalElements;
    private int totalPage;
    private Boolean hasNextPage;

}