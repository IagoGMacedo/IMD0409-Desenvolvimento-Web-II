package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.Purchase;
import com.macedo.Ecommerce.rest.dto.RegisterPurchaseDTO;
import com.macedo.Ecommerce.rest.dto.ResponsePurchaseDTO;

public interface PurchaseService {
    public ResponsePurchaseDTO findById(Integer id);
    public ResponsePurchaseDTO save(RegisterPurchaseDTO Purchase);
    public void delete(Integer id);
    public ResponsePurchaseDTO update(Integer id, RegisterPurchaseDTO Purchase);
    public List<ResponsePurchaseDTO> findAll(Purchase filtro);
    public ResponsePurchaseDTO patch(Integer id, RegisterPurchaseDTO PurchaseIncompletaDto);
    public List<ResponsePurchaseDTO> findByUser(Integer userId);
}

