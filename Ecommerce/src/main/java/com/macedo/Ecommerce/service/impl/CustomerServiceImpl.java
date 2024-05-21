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

    private final CustomerRepository customerRepository;

    private final ShoppingCartRepository shoppingCartRepository;
    private final Patcher patcher;

    @Override
    public List<CustomerDTO> getCustomers(Customer filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(customerRepository.findAll(example));
    }

    @Override
    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer"));
        return toDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer = extractCustomer(customerDTO);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(newCustomer);

        newCustomer.setShoppingCart(shoppingCart);
        customerRepository.save(newCustomer);
        shoppingCartRepository.save(shoppingCart);
        return toDTO(newCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("customer"));

        Customer newCustomer = extractCustomer(customerDTO);
        newCustomer.setId(existingCustomer.getId());
        return toDTO(customerRepository.save(newCustomer));
    }

    @Override
    public CustomerDTO patchCustomer(Integer id, CustomerDTO incompleteCustomerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer"));

        Customer incompleteCustomer = extractCustomer(incompleteCustomerDTO);

        patcher.patchPropertiesNotNull(incompleteCustomer, existingCustomer);
        return toDTO(customerRepository.save(existingCustomer));
    }

    @Override
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("customer"));
        customerRepository.delete(customer);
    }

    private Customer extractCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        return customer;
    }

    private CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }

    private List<CustomerDTO> toDTOList(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            return Collections.emptyList();
        }
        return customers.stream().map(
                customer -> CustomerDTO
                        .builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

}
