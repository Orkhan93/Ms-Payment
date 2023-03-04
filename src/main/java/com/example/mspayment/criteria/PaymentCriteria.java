package com.example.mspayment.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCriteria {

    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private String description;

}