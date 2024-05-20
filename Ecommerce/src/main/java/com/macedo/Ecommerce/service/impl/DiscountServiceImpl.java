package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.rest.dto.DiscountDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.Discount;
import com.macedo.Ecommerce.model.Customer;
import com.macedo.Ecommerce.repository.DiscountRepository;
import com.macedo.Ecommerce.repository.CustomerRepository;
import com.macedo.Ecommerce.service.DiscountService;
import com.macedo.Ecommerce.service.DiscountService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final Patcher patcher;

    @Override
    public List<DiscountDTO> getDiscounts(Discount filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(discountRepository.findAll(example));
    }

    @Override
    public DiscountDTO getDiscountById(Integer id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("credit carrd"));
        return toDTO(discount);
    }

    @Override
    public DiscountDTO createDiscount(DiscountDTO discount) {
        Discount newDiscount = new Discount();
        newDiscount = extractDiscount(discount);
        return toDTO(discountRepository.save(newDiscount));
    }

    @Override
    public DiscountDTO updateDiscount(Integer id, DiscountDTO discount) {
        Discount existingDiscount = discountRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("discount"));

        Discount newDiscount = extractDiscount(discount);
        newDiscount.setId(existingDiscount.getId());
        return toDTO(discountRepository.save(newDiscount));
    }

    @Override
    public DiscountDTO patchDiscount(Integer id, DiscountDTO DiscountIncompletaDto) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("discount"));

        Discount incompleteDiscount = extractDiscount(DiscountIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteDiscount, existingDiscount);
        return toDTO(discountRepository.save(existingDiscount));
    }

    @Override
    public void deleteDiscount(Integer id) {
        Discount discount = discountRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("discount"));
        discountRepository.delete(discount);
    }

    private Discount extractDiscount(DiscountDTO dto) {
        Discount discount = new Discount();
        discount.setRate(dto.getRate());
        return discount;
    }

    private DiscountDTO toDTO(Discount discount) {
        return DiscountDTO
                .builder()
                .id(discount.getId())
                .rate(discount.getRate())
                .build();
    }

    private List<DiscountDTO> toDTOList(List<Discount> creditCards) {
        if (CollectionUtils.isEmpty(creditCards)) {
            return Collections.emptyList();
        }
        return creditCards.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
