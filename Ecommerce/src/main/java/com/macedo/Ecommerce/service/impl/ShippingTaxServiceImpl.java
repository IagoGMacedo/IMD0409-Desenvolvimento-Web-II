package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.rest.dto.ShippingTaxDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.ShippingTax;
import com.macedo.Ecommerce.repository.ShippingTaxRepository;
import com.macedo.Ecommerce.repository.CustomerRepository;
import com.macedo.Ecommerce.service.ShippingTaxService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class ShippingTaxServiceImpl implements ShippingTaxService {

    private final ShippingTaxRepository shippingTaxRepository;
    private final CustomerRepository userRepository;
    private final Patcher patcher;

    @Override
    public List<ShippingTaxDTO> getShippingTaxes(ShippingTax filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(shippingTaxRepository.findAll(example));
    }

    @Override
    public ShippingTaxDTO getShippingTaxById(Integer id) {
        ShippingTax shippingTax = shippingTaxRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("shipping tax"));
        return toDTO(shippingTax);
    }

    @Override
    public ShippingTaxDTO createShippingTax(ShippingTaxDTO shippingTax) {
        ShippingTax newShippingTax = new ShippingTax();
        newShippingTax = extractShippingTax(shippingTax);
        return toDTO(shippingTaxRepository.save(newShippingTax));
    }

    @Override
    public ShippingTaxDTO updateShippingTax(Integer id, ShippingTaxDTO shippingTax) {
        ShippingTax existingShippingTax = shippingTaxRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("shipping Tax"));

        ShippingTax newShippingTax = extractShippingTax(shippingTax);
        newShippingTax.setId(existingShippingTax.getId());
        return toDTO(shippingTaxRepository.save(newShippingTax));
    }

    @Override
    public ShippingTaxDTO patchShippingTax(Integer id, ShippingTaxDTO ShippingTaxIncompletaDto) {
        ShippingTax existingShippingTax = shippingTaxRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("shippingTax"));

        ShippingTax incompleteShippingTax = extractShippingTax(ShippingTaxIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteShippingTax, existingShippingTax);
        return toDTO(shippingTaxRepository.save(existingShippingTax));
    }

    @Override
    public void deleteShippingTax(Integer id) {
        ShippingTax shippingTax = shippingTaxRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("shippingTax"));
        shippingTaxRepository.delete(shippingTax);
    }

    private ShippingTax extractShippingTax(ShippingTaxDTO dto) {
        ShippingTax shippingTax = new ShippingTax();
        shippingTax.setState(dto.getState());
        shippingTax.setValue(dto.getValue());
        return shippingTax;
    }

    private ShippingTaxDTO toDTO(ShippingTax shippingTax) {
        return ShippingTaxDTO
                .builder()
                .id(shippingTax.getId())
                .state(shippingTax.getState())
                .value(shippingTax.getValue())
                .build();
    }

    private List<ShippingTaxDTO> toDTOList(List<ShippingTax> shippingTaxs) {
        if (CollectionUtils.isEmpty(shippingTaxs)) {
            return Collections.emptyList();
        }
        return shippingTaxs.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
