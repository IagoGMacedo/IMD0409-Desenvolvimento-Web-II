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
import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.repository.CreditCardRepository;
import com.macedo.Ecommerce.repository.UserRepository;
import com.macedo.Ecommerce.rest.dto.RegisterCreditCardDTO;
import com.macedo.Ecommerce.service.CreditCardService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final UserRepository userRepository;
    private final Patcher patcher;


    @Override
    public ResponseCreditCardDTO findById(Integer id) {
        CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("credit carrd"));
        return toDTO(creditCard);
    }


    @Override
    public ResponseCreditCardDTO save(RegisterCreditCardDTO creditCard) {
        Integer idUser = creditCard.getIdUser();
        User user = userRepository
                .findById(idUser)
                .orElseThrow(() -> new NotFoundException("user"));

        CreditCard newCreditCard = new CreditCard();
        newCreditCard = extractCreditCard(creditCard);
        newCreditCard.setUser(user);
        return toDTO(creditCardRepository.save(newCreditCard));
    }


    @Override
    public void delete(Integer id) {
        CreditCard creditCard = creditCardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));
        creditCardRepository.delete(creditCard);
    }

    @Override
    public ResponseCreditCardDTO update(Integer id, RegisterCreditCardDTO creditCard) {
        CreditCard existingCreditCard = creditCardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));

        CreditCard newCreditCard = extractCreditCard(creditCard);
        newCreditCard.setId(existingCreditCard.getId());
        return toDTO(creditCardRepository.save(newCreditCard));
    }

    @Override
    public List<ResponseCreditCardDTO> findAll(CreditCard filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(creditCardRepository.findAll(example));
    }

    @Override
    public ResponseCreditCardDTO patch(Integer id, RegisterCreditCardDTO CreditCardIncompletaDto) {
        CreditCard existingCreditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));

        CreditCard incompleteCreditCard = extractCreditCard(CreditCardIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompleteCreditCard, existingCreditCard);
        return toDTO(creditCardRepository.save(existingCreditCard));
    }

    private CreditCard extractCreditCard(RegisterCreditCardDTO dto) {
        CreditCard creditCard = new CreditCard();
        if (dto.getIdUser() != null) {
            Integer idUser = dto.getIdUser();
            User user = userRepository
                    .findById(idUser)
                    .orElseThrow(() -> new NotFoundException("user"));
            creditCard.setUser(user);
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
                .idUser(creditCard.getUser().getId())
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
