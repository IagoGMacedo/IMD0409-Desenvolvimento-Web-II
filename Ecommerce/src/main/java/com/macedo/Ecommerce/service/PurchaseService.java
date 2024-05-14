package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.Purchase;
import com.macedo.Ecommerce.rest.dto.PurchaseDTO;

public interface PurchaseService {
    public PurchaseDTO findById(Integer id);
    public PurchaseDTO save(PurchaseDTO Purchase);
    public void delete(Integer id);
    public PurchaseDTO update(Integer id, PurchaseDTO Purchase);
    public List<PurchaseDTO> findAll(Purchase filtro);
    public PurchaseDTO patch(Integer id, PurchaseDTO PurchaseIncompletaDto);
}

