package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.CreditCard;
import com.macedo.Ecommerce.rest.dto.CreditCardDTO;

public interface CreditCardService {
    public CreditCardDTO findById(Integer id);
    public CreditCardDTO save(CreditCardDTO CreditCard);
    public void delete(Integer id);
    public CreditCardDTO update(Integer id, CreditCardDTO CreditCard);
    public List<CreditCardDTO> findAll(CreditCard filtro);
    public CreditCardDTO patch(Integer id, CreditCardDTO CreditCardIncompletaDto);
}
