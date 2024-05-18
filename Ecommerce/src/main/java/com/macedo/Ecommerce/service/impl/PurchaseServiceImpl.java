package com.macedo.Ecommerce.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.BusinessRuleException;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.*;
import com.macedo.Ecommerce.repository.*;

import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentCreditCardDTO;
import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;
import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDebitCardDTO;
import com.macedo.Ecommerce.rest.dto.RegisterPaymentDTO;
import com.macedo.Ecommerce.rest.dto.ResponsePurchaseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Ecommerce.rest.dto.ProductItemDTO;
import com.macedo.Ecommerce.rest.dto.RegisterPurchaseDTO;
import com.macedo.Ecommerce.service.PurchaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final ProductRepository productRepository;

    private final ProductItemRepository productItemRepository;

    private final PaymentRepository paymentRepository;

    private final CreditCardRepository creditCardRepository;

    private final Patcher patcher;

    @Override
    public List<ResponsePurchaseDTO> getPurchases(Purchase filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(purchaseRepository.findAll(example));
    }

    @Override
    public ResponsePurchaseDTO getPurchaseById(Integer id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));
        return toDTO(purchase);
    }

    @Override
    public List<ResponsePurchaseDTO> getPurchasesByUserId(Integer userId) {
        List<Purchase> list = purchaseRepository.findPurchasesByUserId(userId)
                .orElseThrow(() -> new NotFoundException("user"));

        return toDTOList(list);
    }

    @Override
    @Transactional
    public ResponsePurchaseDTO createPurchase(RegisterPurchaseDTO purchase) {
        Integer idUser = purchase.getIdUser();
        User user = userRepository
                .findById(idUser)
                .orElseThrow(() -> new NotFoundException("user"));

        Integer idAddress = purchase.getIdAddress();
        Address address = addressRepository
                .findById(idAddress)
                .orElseThrow(() -> new NotFoundException("address"));

        Purchase newPurchase = new Purchase();
        newPurchase.setDate(LocalDate.now());
        newPurchase.setUser(user);
        newPurchase.setAddress(address);

        List<ProductItem> productItems = extractProductItems(newPurchase, purchase.getProductItems());
        BigDecimal totalPrice = getTotalPrice(productItems);
        Payment payment = extractPayment(newPurchase, purchase.getPayment(), totalPrice);

        newPurchase.setTotalPrice(totalPrice);
        newPurchase.setPayment(payment);

        purchaseRepository.save(newPurchase);
        productItemRepository.saveAll(productItems);
        paymentRepository.save(payment);
        newPurchase.setProductItems(productItems);

        return toDTO(newPurchase);
    }

    @Override
    public ResponsePurchaseDTO updatePurchase(Integer id, RegisterPurchaseDTO Purchase) {
        Purchase existingPurchase = purchaseRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));

        Purchase newPurchase = extractPurchase(Purchase);
        newPurchase.setId(existingPurchase.getId());
        return toDTO(purchaseRepository.save(newPurchase));
    }

    @Override
    public ResponsePurchaseDTO patchPurchase(Integer id, RegisterPurchaseDTO PurchaseIncompletaDto) {
        Purchase existingPurchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));

        Purchase incompletePurchase = extractPurchase(PurchaseIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompletePurchase, existingPurchase);
        return toDTO(purchaseRepository.save(existingPurchase));
    }

    @Override
    public void deletePurchase(Integer id) {
        Purchase purchase = purchaseRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));
        purchaseRepository.delete(purchase);
    }

    private Purchase extractPurchase(RegisterPurchaseDTO dto) {
        return null;
    }

    private List<ProductItem> extractProductItems(Purchase newPurchase, List<ProductItemDTO> productItems) {
        if (productItems.isEmpty()) {
            throw new BusinessRuleException("Não é possível realizar um pedido sem items.");
        }
        return productItems
                .stream()
                .map(dto -> {
                    Integer idProduct = dto.getIdProduct();
                    Product product = productRepository
                            .findById(idProduct)
                            .orElseThrow(() -> new NotFoundException("product"));

                    if (product.getStockQuantity() < dto.getQuantity()) {
                        throw new BusinessRuleException("Não existe a quantidade solicitada no estoque");
                    }
                    product.setStockQuantity(product.getStockQuantity() - dto.getQuantity());
                    productRepository.save(product);
                    ProductItem productItem = new ProductItem();
                    productItem.setQuantity(dto.getQuantity());
                    productItem.setPurchase(newPurchase);
                    productItem.setProduct(product);
                    productItem.setSubTotal(product.getPrice().multiply(new BigDecimal(dto.getQuantity())));
                    return productItem;
                }).collect(Collectors.toList());
    }

    private Payment extractPayment(Purchase purchase, RegisterPaymentDTO dto, BigDecimal totalPrice) {
        Payment payment = new Payment();
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPrice(totalPrice);
        payment.setPurchase(purchase);
        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO
                || payment.getPaymentMethod() == PaymentMethod.CARTAO_DEBITO) {
            Integer idCreditCard = dto.getIdCreditCard();
            CreditCard creditCard = creditCardRepository
                    .findById(idCreditCard)
                    .orElseThrow(() -> new NotFoundException("credit card"));
            payment.setCreditCard(creditCard);

            if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO)
                payment.setInstallments(dto.getInstallments());
        }
        return payment;
    }

    private ResponsePurchaseDTO toDTO(Purchase purchase) {
        return ResponsePurchaseDTO.builder()
                .id(purchase.getId())
                .idUser(purchase.getUser().getId())
                .productItems(toDTOProductItems(purchase.getProductItems()))
                .totalPrice(purchase.getTotalPrice())
                .date(purchase.getDate())
                .payment(toPaymentDTO(purchase.getPayment()))
                .idAddress(purchase.getAddress().getId())
                .build();
    }

    private List<ResponsePurchaseDTO> toDTOList(List<Purchase> purchases) {
        if (CollectionUtils.isEmpty(purchases)) {
            return Collections.emptyList();
        }
        return purchases.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private List<ProductItemDTO> toDTOProductItems(List<ProductItem> productItems) {
        if (CollectionUtils.isEmpty(productItems)) {
            return Collections.emptyList();
        }
        return productItems.stream().map(
                item -> ProductItemDTO
                        .builder()
                        .idProduct(item.getId())
                        .quantity(item.getQuantity())
                        .subTotal(item.getSubTotal())
                        .build())
                .collect(Collectors.toList());
    }

    private ResponsePaymentDTO toPaymentDTO(Payment payment) {
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

    private BigDecimal getTotalPrice(List<ProductItem> productItems) {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (ProductItem productItem : productItems) {
            totalValue = totalValue
                    .add(productItem.getProduct().getPrice().multiply(new BigDecimal(productItem.getQuantity())));
        }
        return totalValue;
    }

}
