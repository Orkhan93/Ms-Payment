package com.example.mspayment.service;

import com.example.mspayment.client.CountryClient;
import com.example.mspayment.criteria.PaymentCriteria;
import com.example.mspayment.domain.Payment;
import com.example.mspayment.enums.ErrorCode;
import com.example.mspayment.error.ErrorMessage;
import com.example.mspayment.exception.ServiceException;
import com.example.mspayment.mapper.PaymentMapper;
import com.example.mspayment.repository.PaymentRepository;
import com.example.mspayment.request.PaymentRequest;
import com.example.mspayment.response.PageablePaymentResponse;
import com.example.mspayment.response.PaymentResponse;
import com.example.mspayment.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.example.mspayment.constant.ExceptionConstants.COUNTRY_NOT_FOUND_CODE;
import static com.example.mspayment.constant.ExceptionConstants.COUNTRY_NOT_FOUND_MESSAGE;
import static com.example.mspayment.mapper.PaymentMapper.domainToResponse;
import static com.example.mspayment.mapper.PaymentMapper.requestToDomain;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CountryClient countryClient;

    public void savePayment(PaymentRequest paymentRequest) {
        log.info("savePayment started");
        countryClient.getAllAvailableCountries(paymentRequest.getCurrency())
                .stream()
                .filter(country -> country.getRemainingLimit().compareTo(paymentRequest.getAmount()) > 0)
                .findFirst()
                .orElseThrow(() -> ServiceException.of(ErrorCode.COUNTRY_NOT_FOUND.name(), ErrorMessage.COUNTRY_NOT_FOUND));
        paymentRepository.save(requestToDomain(paymentRequest));
        log.info("savePayment success");
    }

    public void updatePayment(Long id, PaymentRequest request) {
        log.info("updatePayment start id : {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> ServiceException.of(ErrorCode.COUNTRY_NOT_FOUND.name(), ErrorMessage.COUNTRY_NOT_FOUND));
        payment.setDescription(request.getDescription());
        payment.setAmount(request.getAmount());
        paymentRepository.save(payment);
        log.info("updatePayment success id : {}", id);
    }

//    public List<PaymentResponse> getAllPayments() {
//        return paymentRepository.findAll()
//                .stream()
//                .map(PaymentMapper::domainToResponse).collect(Collectors.toList());
//    }

    public PageablePaymentResponse getAllPayments(int page, int count, PaymentCriteria paymentCriteria) {
        log.info("getAllPayments start");
        var pageable =
                PageRequest.of(page, count, Sort.by(DESC, "id"));
        var pageablePayments =
                paymentRepository.findAll(new PaymentSpecification(paymentCriteria), pageable);
        var payments = pageablePayments.getContent()
                .stream()
                .map(PaymentMapper::domainToResponse).collect(Collectors.toList());
        log.info("getAllPayments success");
        return PageablePaymentResponse.builder()
                .paymentResponseList(payments)
                .hasNextPage(pageablePayments.hasNext())
                .totalElements(pageablePayments.getTotalElements())
                .totalPage(pageablePayments.getTotalPages())
                .build();
    }

    public PaymentResponse getPaymentById(Long id) {
        log.info("getPayment start id : {}", id);
        Payment payment = fetchPaymentIfExist(id);
        log.info("getPayment success id :  {}", id);
        return domainToResponse(payment);
    }

    public void deletePayment(Long id) {
        log.info("deletePayment start id : {}", id);
        paymentRepository.deleteById(id);
        log.info("deletePayment success id : {}", id);
    }

    private Payment fetchPaymentIfExist(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> ServiceException.of(COUNTRY_NOT_FOUND_CODE, COUNTRY_NOT_FOUND_MESSAGE));
    }

}