package com.example.mspayment.specification;

import com.example.mspayment.criteria.PaymentCriteria;
import com.example.mspayment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class PaymentSpecification implements Specification<Payment> {

    private final PaymentCriteria paymentCriteria;
    private static final String AMOUNT = "amount";
    private static final String DESCRIPTION = "description";

    @Override
    public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicateList = new ArrayList<>();
        if (paymentCriteria != null) {
            if (paymentCriteria.getAmountFrom() != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(AMOUNT), paymentCriteria.getAmountFrom()));
            }
            if (paymentCriteria.getAmountTo() != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(AMOUNT), paymentCriteria.getAmountTo()));
            }
            if (StringUtils.hasText(paymentCriteria.getDescription())) {
                predicateList.add(criteriaBuilder.like(root.get(DESCRIPTION), "%" + paymentCriteria.getDescription() + "%"));
            }
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}