package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.model.Address;
import com.macedo.Ecommerce.rest.dto.AddressDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.CreditCard;
import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.repository.CreditCardRepository;
import com.macedo.Ecommerce.repository.UserRepository;
import com.macedo.Ecommerce.rest.dto.CreditCardDTO;
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
    public CreditCardDTO findById(Integer id) {
         CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("credit carrd"));
        return toDTO(creditCard);
    }


    @Override
    public CreditCardDTO save(CreditCardDTO creditCard) {
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
    public CreditCardDTO update(Integer id, CreditCardDTO creditCard) {
        CreditCard existingCreditCard = creditCardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));

        CreditCard newCreditCard = extractCreditCard(creditCard);
        newCreditCard.setId(existingCreditCard.getId());
        return toDTO(creditCardRepository.save(newCreditCard));
    }

    @Override
    public List<CreditCardDTO> findAll(CreditCard filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(creditCardRepository.findAll(example));
    }

    @Override
    public CreditCardDTO patch(Integer id, CreditCardDTO CreditCardIncompletaDto) {
        CreditCard existingCreditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));

        CreditCard incompleteCreditCard = extractCreditCard(CreditCardIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompleteCreditCard, existingCreditCard);
        return toDTO(creditCardRepository.save(existingCreditCard));
    }

    private CreditCard extractCreditCard(CreditCardDTO dto){
        CreditCard creditCard = new CreditCard();
        if (dto.getIdUser() != null) {
            Integer idUser = dto.getIdUser();
            User user = userRepository
                    .findById(idUser)
                    .orElseThrow(() -> new NotFoundException("user"));
            creditCard.setUser(user);
        }
        return creditCard;
    }
    private CreditCardDTO toDTO(CreditCard creditCard) {
        return CreditCardDTO.builder()
                .id(creditCard.getId())
                .idUser(creditCard.getUser().getId())
                .build();
    }
    private List<CreditCardDTO> toDTOList(List<CreditCard> creditCards){
        if (CollectionUtils.isEmpty(creditCards)) {
            return Collections.emptyList();
        }
        return creditCards.stream().map(
                address -> CreditCardDTO
                        .builder()
                        .id(address.getId())
                        .idUser(address.getUser().getId())
                        .build()
        ).collect(Collectors.toList());
    }
    
}
