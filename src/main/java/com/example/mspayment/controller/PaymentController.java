package com.example.mspayment.controller;

import com.example.mspayment.criteria.PaymentCriteria;
import com.example.mspayment.request.PaymentRequest;
import com.example.mspayment.response.PageablePaymentResponse;
import com.example.mspayment.response.PaymentResponse;
import com.example.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public PageablePaymentResponse getAllPayments(@Valid @RequestParam int page,
                                                  @RequestParam int count,
                                                  PaymentCriteria paymentCriteria) {
        return paymentService.getAllPayments(page, count, paymentCriteria);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public PaymentResponse getPaymentById(@Valid @PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void savePayment(@Valid @RequestBody PaymentRequest request) {
        paymentService.savePayment(request);
    }

    @PutMapping("/{id}")
    public void updatePayment(@Valid @PathVariable Long id, @RequestBody PaymentRequest request) {
        paymentService.updatePayment(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@Valid @PathVariable Long id) {
        paymentService.deletePayment(id);
    }

}