package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.Address;
import com.macedo.Ecommerce.repository.AddressRepository;
import com.macedo.Ecommerce.rest.dto.AddressDTO;
import com.macedo.Ecommerce.service.AddressService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private final Patcher patcher;


    @Override
    public AddressDTO findById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("address"));
        return toDTO(address);
    }


    @Override
    public AddressDTO save(AddressDTO address) {
        Integer idUser = address.getIdUser();
        User user = userRepository
                .findById(idUser)
                .orElseThrow(() -> new NotFoundException("user"));

        Address newAddress = new Address();
        newAddress = extractAddress(address);
        newAddress.setUser(user);
        return toDTO(addressRepository.save(newAddress));
    }


    @Override
    public void delete(Integer id) {
        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("address"));
        addressRepository.delete(address);
    }

    @Override
    public AddressDTO update(Integer id, AddressDTO Address) {
        Address existingAddress = addressRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("address"));

        Address newAddress = extractAddress(Address);
        newAddress.setId(existingAddress.getId());
        return toDTO(addressRepository.save(newAddress));
    }

    @Override
    public List<AddressDTO> findAll(Address filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(addressRepository.findAll(example));
    }

    @Override
    public AddressDTO patch(Integer id, AddressDTO AddressIncompletaDto) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("address"));

        Address incompleteAddress = extractAddress(AddressIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompleteAddress, existingAddress);
        return toDTO(addressRepository.save(existingAddress));
    }



    private Address extractAddress(AddressDTO dto){
        Address address = new Address();
        if (dto.getIdUser() != null) {
            Integer idUser = dto.getIdUser();
            User user = userRepository
                    .findById(idUser)
                    .orElseThrow(() -> new NotFoundException("user"));
            address.setUser(user);
        }
        address.setCompleteAddress(dto.getCompleteAddress());
        address.setCep(dto.getCep());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setComplement(dto.getComplement());
        address.setNumber(dto.getNumber());
        address.setState(dto.getState());
        return address;
    }

    private AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .idUser(address.getUser().getId())
                .cep(address.getCep())
                .completeAddress(address.getCompleteAddress())
                .number(address.getNumber())
                .complement(address.getComplement())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    private List<AddressDTO> toDTOList(List<Address> addresses){
        if (CollectionUtils.isEmpty(addresses)) {
            return Collections.emptyList();
        }
        return addresses.stream().map(
                address -> AddressDTO
                        .builder()
                        .id(address.getId())
                        .idUser(address.getUser().getId())
                        .cep(address.getCep())
                        .completeAddress(address.getCompleteAddress())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .district(address.getDistrict())
                        .city(address.getCity())
                        .state(address.getState())
                        .build()
        ).collect(Collectors.toList());
    }
    
}
