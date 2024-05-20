package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.Discount;
import com.macedo.Ecommerce.rest.dto.DiscountDTO;

public interface DiscountService {
    public List<DiscountDTO> getDiscounts(Discount filtro);

    public DiscountDTO getDiscountById(Integer id);

    public DiscountDTO createDiscount(DiscountDTO discountDTO);

    public DiscountDTO updateDiscount(Integer id, DiscountDTO discountDTO);

    public DiscountDTO patchDiscount(Integer id, DiscountDTO discountIncomplete);

    public void deleteDiscount(Integer id);
}
