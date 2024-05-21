package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.Purchase;
import com.macedo.Ecommerce.rest.dto.RegisterPurchaseDTO;
import com.macedo.Ecommerce.rest.dto.ResponsePurchaseDTO;

public interface PurchaseService {
    public List<ResponsePurchaseDTO> getPurchases(Purchase filtro);

    public ResponsePurchaseDTO getPurchaseById(Integer id);

    public List<ResponsePurchaseDTO> getPurchasesByCustomerId(Integer customerId);

    public ResponsePurchaseDTO createPurchase(RegisterPurchaseDTO Purchase);

    public void deletePurchase(Integer id);

}
