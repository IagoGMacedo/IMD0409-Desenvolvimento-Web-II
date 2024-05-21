package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.CreditCard;
import com.macedo.Ecommerce.rest.dto.RegisterCreditCardDTO;
import com.macedo.Ecommerce.rest.dto.ResponseCreditCardDTO;

public interface CreditCardService {
    public List<ResponseCreditCardDTO> getCreditCards(RegisterCreditCardDTO filtro);

    public ResponseCreditCardDTO getCreditCardById(Integer id);

    public List<ResponseCreditCardDTO> getCreditCardsByCustomerId(Integer id);

    public ResponseCreditCardDTO createCreditCard(RegisterCreditCardDTO CreditCard);

    public ResponseCreditCardDTO updateCreditCard(Integer id, RegisterCreditCardDTO CreditCard);

    public ResponseCreditCardDTO patchCreditCard(Integer id, RegisterCreditCardDTO CreditCardIncompletaDto);

    public void deleteCreditCard(Integer id);

}
