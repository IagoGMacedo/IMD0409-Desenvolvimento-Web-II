package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.CreditCard;
import com.macedo.Ecommerce.model.PaymentMethod;
import com.macedo.Ecommerce.model.Purchase;
import com.macedo.Ecommerce.repository.CreditCardRepository;
import com.macedo.Ecommerce.repository.PaymentRepository;
import com.macedo.Ecommerce.repository.PurchaseRepository;
import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentCreditCardDTO;
import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;
import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDebitCardDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.model.Payment;
import com.macedo.Ecommerce.rest.dto.RegisterPaymentDTO;
import com.macedo.Ecommerce.service.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final CreditCardRepository creditCardRepository;

    private final PurchaseRepository purchaseRepository;


    @Override
    public List<ResponsePaymentDTO> getPayments(Payment filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(paymentRepository.findAll(example));
    }

    @Override
    public ResponsePaymentDTO getPaymentById(Integer id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("payment"));
        return toDTO(payment);
    }

    @Override
    public List<ResponsePaymentDTO> getPaymentsByUserId(Integer userId) {
        /* 
        List<Payment> list = paymentRepository.findPaymentsByCustomerId(userId)
                .orElseThrow(() -> new NotFoundException("user"));

        return toDTOList(list);
        */
        return getPayments(new Payment());
    }

    private Payment extractPayment(RegisterPaymentDTO dto) {
        Payment payment = new Payment();
        if (dto.getIdCreditCard() != null) {
            Integer idCreditCard = dto.getIdCreditCard();
            CreditCard creditCard = creditCardRepository
                    .findById(idCreditCard)
                    .orElseThrow(() -> new NotFoundException("credit card"));
            payment.setCreditCard(creditCard);
        }

        if (dto.getIdPurchase() != null) {
            Integer idPurchase = dto.getIdPurchase();
            Purchase purchase = purchaseRepository
                    .findById(idPurchase)
                    .orElseThrow(() -> new NotFoundException("purchase"));
            payment.setPurchase(purchase);
        }

        return payment;
    }

    private ResponsePaymentDTO toDTO(Payment payment) {
        ResponsePaymentDTO response = ResponsePaymentDTO
                .builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .price(payment.getPrice())
                .build();

        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO)
            response.setCreditCard(toCreditCardDTO(payment));

        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_DEBITO)
            response.setDebitCard(toDebitCardDTO(payment));

        return response;
    }

    private List<ResponsePaymentDTO> toDTOList(List<Payment> payments) {
        if (CollectionUtils.isEmpty(payments)) {
            return Collections.emptyList();
        }
        return payments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ResponsePaymentCreditCardDTO toCreditCardDTO(Payment payment) {
        return ResponsePaymentCreditCardDTO
                .builder()
                .idCreditCard(payment.getCreditCard().getId())
                .installments(payment.getInstallments())
                .build();
    }

    private ResponsePaymentDebitCardDTO toDebitCardDTO(Payment payment) {
        return ResponsePaymentDebitCardDTO
                .builder()
                .idCreditCard(payment.getCreditCard().getId())
                .build();
    }

   

}
