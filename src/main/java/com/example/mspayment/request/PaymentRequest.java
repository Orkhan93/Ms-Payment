package com.example.mspayment.request;

import com.example.mspayment.error.constraint.validation.Amount;
import com.example.mspayment.error.constraint.validation.Currency;
import com.example.mspayment.error.constraint.validation.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @Amount
    private BigDecimal amount;

    @Description
    private String description;

    @Currency
    private String currency;

}