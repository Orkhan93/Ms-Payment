package com.example.mspayment.mapper;

import com.example.mspayment.domain.Payment;
import com.example.mspayment.request.PaymentRequest;
import com.example.mspayment.response.PaymentResponse;

import java.time.LocalDateTime;

public class PaymentMapper {

    public static Payment requestToDomain(PaymentRequest paymentRequest){
        return Payment.builder()
                .amount(paymentRequest.getAmount())
                .description(paymentRequest.getDescription())
                .build();
    }

    public static PaymentResponse domainToResponse(Payment payment){
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .description(payment.getDescription())
                .responseAt(LocalDateTime.now())
                .build();
    }

}