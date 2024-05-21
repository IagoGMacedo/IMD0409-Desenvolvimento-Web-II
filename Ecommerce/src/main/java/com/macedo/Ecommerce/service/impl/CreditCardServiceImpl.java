package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.rest.dto.ResponseCreditCardDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.CreditCard;
import com.macedo.Ecommerce.model.Customer;
import com.macedo.Ecommerce.repository.CreditCardRepository;
import com.macedo.Ecommerce.repository.CustomerRepository;
import com.macedo.Ecommerce.rest.dto.RegisterCreditCardDTO;
import com.macedo.Ecommerce.service.CreditCardService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CustomerRepository userRepository;
    private final Patcher patcher;

    @Override
    public List<ResponseCreditCardDTO> getCreditCards(CreditCard filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(creditCardRepository.findAll(example));
    }

    @Override
    public ResponseCreditCardDTO getCreditCardById(Integer id) {
        CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("credit card"));
        return toDTO(creditCard);
    }

    @Override
    public ResponseCreditCardDTO createCreditCard(RegisterCreditCardDTO creditCard) {
        Integer idUser = creditCard.getIdUser();
        Customer user = userRepository
                .findById(idUser)
                .orElseThrow(() -> new NotFoundException("user"));

        CreditCard newCreditCard = new CreditCard();
        newCreditCard = extractCreditCard(creditCard);
        newCreditCard.setCustomer(user);
        return toDTO(creditCardRepository.save(newCreditCard));
    }

    @Override
    public ResponseCreditCardDTO updateCreditCard(Integer id, RegisterCreditCardDTO creditCard) {
        CreditCard existingCreditCard = creditCardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));

        CreditCard newCreditCard = extractCreditCard(creditCard);
        newCreditCard.setId(existingCreditCard.getId());
        return toDTO(creditCardRepository.save(newCreditCard));
    }

    @Override
    public ResponseCreditCardDTO patchCreditCard(Integer id, RegisterCreditCardDTO CreditCardIncompletaDto) {
        CreditCard existingCreditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));

        CreditCard incompleteCreditCard = extractCreditCard(CreditCardIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteCreditCard, existingCreditCard);
        return toDTO(creditCardRepository.save(existingCreditCard));
    }

    @Override
    public void deleteCreditCard(Integer id) {
        CreditCard creditCard = creditCardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));
        creditCardRepository.delete(creditCard);
    }

    private CreditCard extractCreditCard(RegisterCreditCardDTO dto) {
        CreditCard creditCard = new CreditCard();
        if (dto.getIdUser() != null) {
            Integer idUser = dto.getIdUser();
            Customer user = userRepository
                    .findById(idUser)
                    .orElseThrow(() -> new NotFoundException("user"));
            creditCard.setCustomer(user);
        }
        creditCard.setCardHolderName(dto.getCardHolderName());
        creditCard.setNumber(dto.getNumber());
        creditCard.setValidity(dto.getValidity());
        creditCard.setCvv(dto.getCvv());
        return creditCard;
    }

    private ResponseCreditCardDTO toDTO(CreditCard creditCard) {
        return ResponseCreditCardDTO
                .builder()
                .id(creditCard.getId())
                .idUser(creditCard.getCustomer().getId())
                .cardHolderName(creditCard.getCardHolderName())
                .lastNumbers(creditCard.getNumber().substring(creditCard.getNumber().length() - 4))
                .build();
    }

    private List<ResponseCreditCardDTO> toDTOList(List<CreditCard> creditCards) {
        if (CollectionUtils.isEmpty(creditCards)) {
            return Collections.emptyList();
        }
        return creditCards.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
