package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.CreditCard;
import com.macedo.Ecommerce.rest.dto.RegisterCreditCardDTO;
import com.macedo.Ecommerce.rest.dto.ResponseCreditCardDTO;

public interface CreditCardService {
    public ResponseCreditCardDTO findById(Integer id);
    public ResponseCreditCardDTO save(RegisterCreditCardDTO CreditCard);
    public void delete(Integer id);
    public ResponseCreditCardDTO update(Integer id, RegisterCreditCardDTO CreditCard);
    public List<ResponseCreditCardDTO> findAll(CreditCard filtro);
    public ResponseCreditCardDTO patch(Integer id, RegisterCreditCardDTO CreditCardIncompletaDto);
}
