package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.CreditCard;
import com.macedo.Ecommerce.model.Purchase;
import com.macedo.Ecommerce.repository.CreditCardRepository;
import com.macedo.Ecommerce.repository.PaymentRepository;
import com.macedo.Ecommerce.repository.PurchaseRepository;
import com.macedo.Ecommerce.rest.dto.CreditCardDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.model.Payment;
import com.macedo.Ecommerce.rest.dto.PaymentDTO;
import com.macedo.Ecommerce.service.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final CreditCardRepository creditCardRepository;

    private final PurchaseRepository purchaseRepository;

    private final Patcher patcher;


    @Override
    public PaymentDTO findById(Integer id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("payment"));
        return toDTO(payment);
    }

    @Override
    public PaymentDTO save(PaymentDTO Payment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");

        //cadastrar pagamento
        //alterar informações de purchase, salvar
    }

    @Override
    public void delete(Integer id) {
        Payment payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("payment"));
        paymentRepository.delete(payment);
    }

    @Override
    public PaymentDTO update(Integer id, PaymentDTO Payment) {
        Payment existingPayment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("payment"));

        Payment newPayment = extractPayment(Payment);
        newPayment.setId(existingPayment.getId());
        return toDTO(paymentRepository.save(newPayment));
    }

    @Override
    public List<PaymentDTO> findAll(Payment filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(paymentRepository.findAll(example));
    }

    @Override
    public PaymentDTO patch(Integer id, PaymentDTO PaymentIncompletaDto) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("payment"));

        Payment incompletePayment = extractPayment(PaymentIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompletePayment, existingPayment);
        return toDTO(paymentRepository.save(existingPayment));
    }

    private Payment extractPayment(PaymentDTO dto){
        Payment payment = new Payment();
        if(dto.getIdCreditCard() != null){
            Integer idCreditCard = dto.getIdCreditCard();
            CreditCard creditCard = creditCardRepository
                    .findById(idCreditCard)
                    .orElseThrow(() -> new NotFoundException("credit card"));
            payment.setCreditCard(creditCard);
        }

        if(dto.getIdPurchase() != null){
            Integer idPurchase = dto.getIdPurchase();
            Purchase purchase = purchaseRepository
                    .findById(idPurchase)
                    .orElseThrow(() -> new NotFoundException("purchase"));
            payment.setPurchase(purchase);
        }

        return payment;
    }
    private PaymentDTO toDTO(Payment payment) {
        return PaymentDTO
                .builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .idPurchase(payment.getPurchase().getId())
                .idCreditCard(payment.getCreditCard().getId())
                .installments(payment.getInstallments())
                .price(payment.getPrice())
                .build();
    }
    private List<PaymentDTO> toDTOList(List<Payment> payments){
        if (CollectionUtils.isEmpty(payments)) {
            return Collections.emptyList();
        }
        return payments.stream().map(
                payment -> PaymentDTO
                        .builder()
                        .id(payment.getId())
                        .paymentMethod(payment.getPaymentMethod())
                        .idPurchase(payment.getPurchase().getId())
                        .idCreditCard(payment.getCreditCard().getId())
                        .installments(payment.getInstallments())
                        .price(payment.getPrice())
                        .build()
        ).collect(Collectors.toList());
    }
    
}
