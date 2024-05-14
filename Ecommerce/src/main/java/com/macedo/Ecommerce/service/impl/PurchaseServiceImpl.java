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

import com.macedo.Ecommerce.rest.dto.PaymentDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Ecommerce.rest.dto.ProductDTO;
import com.macedo.Ecommerce.rest.dto.ProductItemDTO;
import com.macedo.Ecommerce.rest.dto.PurchaseDTO;
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
    public PurchaseDTO findById(Integer id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));
        return toDTO(purchase);
    }

    @Override
    public PurchaseDTO save(PurchaseDTO purchase) {
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
        newPurchase.setTotalPrice(totalPrice);
        Payment payment = extractPayment(purchase.getPayment(), totalPrice);


        //fazer a mesma coisa com endereço e address
        newPurchase.setPayment(payment);
        purchaseRepository.save(newPurchase);
        productItemRepository.saveAll(productItems);
        newPurchase.setProductItems(productItems);

        return toDTO(newPurchase);
    }


    @Override
    public void delete(Integer id) {
        Purchase purchase = purchaseRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));
        purchaseRepository.delete(purchase);
    }

    @Override
    public PurchaseDTO update(Integer id, PurchaseDTO Purchase) {
        Purchase existingPurchase = purchaseRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));

        Purchase newPurchase = extractPurchase(Purchase);
        newPurchase.setId(existingPurchase.getId());
        return toDTO(purchaseRepository.save(newPurchase));
    }

    @Override
    public List<PurchaseDTO> findAll(Purchase filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(purchaseRepository.findAll(example));
    }

    @Override
    public PurchaseDTO patch(Integer id, PurchaseDTO PurchaseIncompletaDto) {
        Purchase existingPurchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));

        Purchase incompletePurchase = extractPurchase(PurchaseIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompletePurchase, existingPurchase);
        return toDTO(purchaseRepository.save(existingPurchase));
    }

    private Purchase extractPurchase(PurchaseDTO dto) {
        return null;
    }

    private PurchaseDTO toDTO(Purchase purchase) {
        return PurchaseDTO.builder()
        .id(purchase.getId())
        .idUser(purchase.getUser().getId())
        .productItems(toDTOProductItems(purchase.getProductItems()))
        .totalPrice(purchase.getTotalPrice())
        .date(purchase.getDate())
        .payment(toPaymentDTO(purchase.getPayment()))
        .idAddress(purchase.getAddress().getId())
        .build();
    }

    

    private List<PurchaseDTO> toDTOList(List<Purchase> purchases) {
        if (CollectionUtils.isEmpty(purchases)) {
            return Collections.emptyList();
        }
        return purchases.stream().map(
                purchase -> PurchaseDTO
                        .builder()
                        .id(purchase.getId())
                        .idUser(purchase.getUser().getId())
                        .productItems(toDTOProductItems(purchase.getProductItems()))
                        .totalPrice(purchase.getTotalPrice())
                        .date(purchase.getDate())
                        .payment(toPaymentDTO(purchase.getPayment()))
                        .idAddress(purchase.getAddress().getId())
                        .build()
        ).collect(Collectors.toList());
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
                    return productItem;
                }).collect(Collectors.toList());
    }

    private List<ProductItemDTO> toDTOProductItems(List<ProductItem> productItems) {
        if(CollectionUtils.isEmpty(productItems)){
            return Collections.emptyList();
        }
        return productItems.stream().map(
                item -> ProductItemDTO
                            .builder()
                            .idProduct(item.getId())
                            .quantity(item.getQuantity())
                            .build()
        ).collect(Collectors.toList());
    }

    private BigDecimal getTotalPrice(List<ProductItem> productItems) {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (ProductItem productItem : productItems) {
            totalValue =  totalValue.add(productItem.getProduct().getPrice().multiply(new BigDecimal(productItem.getQuantity())));
        }
        return totalValue;
    }

    private Payment extractPayment(PaymentDTO dto, BigDecimal totalPrice){
        Payment payment = new Payment();
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPrice(totalPrice);
        if(payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO){
            Integer idCreditCard = dto.getIdCreditCard();
            CreditCard creditCard = creditCardRepository
                    .findById(idCreditCard)
                    .orElseThrow(() -> new NotFoundException("credit card"));
            payment.setCreditCard(creditCard);
            payment.setInstallments(payment.getInstallments());
        }
        paymentRepository.save(payment);
        return payment;
    }

    private PaymentDTO toPaymentDTO(Payment payment){
        return PaymentDTO
                .builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                //.idPurchase(payment.getPurchase().getId())
                //.idCreditCard(payment.getCreditCard().getId())
                .installments(payment.getInstallments())
                .price(payment.getPrice())
                .build();
    }

}
