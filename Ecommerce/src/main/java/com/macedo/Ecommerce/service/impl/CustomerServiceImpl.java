package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.ShoppingCart;
import com.macedo.Ecommerce.repository.ShoppingCartRepository;
import com.macedo.Ecommerce.repository.CustomerRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.model.Customer;
import com.macedo.Ecommerce.rest.dto.CustomerDTO;
import com.macedo.Ecommerce.service.CustomerService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository userRepository;

    private final ShoppingCartRepository shoppingCartRepository;
    private final Patcher patcher;

    @Override
    public List<CustomerDTO> getUsers(Customer filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(userRepository.findAll(example));
    }

    @Override
    public CustomerDTO getUserById(Integer id) {
        Customer user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user"));
        return toDTO(user);
    }

    @Override
    public CustomerDTO createUser(CustomerDTO User) {
        Customer newUser = new Customer();
        newUser = extractUser(User);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(newUser);

        newUser.setShoppingCart(shoppingCart);
        userRepository.save(newUser);
        shoppingCartRepository.save(shoppingCart);
        return toDTO(newUser);
    }

    @Override
    public CustomerDTO updateUser(Integer id, CustomerDTO User) {
        Customer existingUser = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("user"));

        Customer newUser = extractUser(User);
        newUser.setId(existingUser.getId());
        return toDTO(userRepository.save(newUser));
    }

    @Override
    public CustomerDTO patchUser(Integer id, CustomerDTO UserIncompletaDto) {
        Customer existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user"));

        Customer incompleteUser = extractUser(UserIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteUser, existingUser);
        return toDTO(userRepository.save(existingUser));
    }

    @Override
    public void deleteUser(Integer id) {
        Customer user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("user"));
        userRepository.delete(user);
    }

    private Customer extractUser(CustomerDTO dto) {
        Customer user = new Customer();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    private CustomerDTO toDTO(Customer user) {
        return CustomerDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    private List<CustomerDTO> toDTOList(List<Customer> users) {
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream().map(
                user -> CustomerDTO
                        .builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

}
