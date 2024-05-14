package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.rest.dto.UserDTO;
import com.macedo.Ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Patcher patcher;

    @Override
    public UserDTO findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user"));
        return toDTO(user);
    }

    @Override
    public UserDTO save(UserDTO User) {
        User newUser = new User();
        newUser = extractUser(User);
        return toDTO(userRepository.save(newUser));
    }


    @Override
    public void delete(Integer id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("user"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO update(Integer id, UserDTO User) {
        User existingUser = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("user"));

        User newUser = extractUser(User);
        newUser.setId(existingUser.getId());
        return toDTO(userRepository.save(newUser));
    }

    @Override
    public List<UserDTO> findAll(User filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(userRepository.findAll(example));
    }

    @Override
    public UserDTO patch(Integer id, UserDTO UserIncompletaDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user"));

        User incompleteUser = extractUser(UserIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompleteUser, existingUser);
        return toDTO(userRepository.save(existingUser));
    }

    private User extractUser(UserDTO dto){
        User user = new User();
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        return user;
    }
    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    private List<UserDTO> toDTOList(List<User> users){
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream().map(
                        user -> UserDTO
                                .builder()
                                .id(user.getId())
                                .name(user.getName())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build())
                .collect(Collectors.toList());
    }
    
}
