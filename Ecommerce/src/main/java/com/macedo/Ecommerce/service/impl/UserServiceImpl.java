package com.macedo.Ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import com.macedo.Ecommerce.exception.InvalidPasswordException;
import com.macedo.Ecommerce.model.Role;
import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.repository.UserRepository;
import com.macedo.Ecommerce.rest.dto.CategoryDTO;
import com.macedo.Ecommerce.rest.dto.CredentialsDTO;
import com.macedo.Ecommerce.rest.dto.TokenDTO;
import com.macedo.Ecommerce.rest.dto.UserDTO;
import com.macedo.Ecommerce.security.JwtService;

import jakarta.transaction.Transactional;

@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private JwtService jwtService;

    @Transactional
    public UserDTO salvar(UserDTO usuario) {
        User user = extractUser(usuario);
        String senhaCriptografada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCriptografada);
        return toDTO(repository.save(user));
    }

    public TokenDTO autenticar(CredentialsDTO credenciais) {
        User usuario = User.builder()
                .login(credenciais.getLogin())
                .password(credenciais.getPassword()).build();

        try {

            UserDetails user = loadUserByUsername(usuario.getLogin());
            boolean senhasBatem = passwordEncoder.matches(usuario.getPassword(), user.getPassword());

            if (senhasBatem) {
                String token = jwtService.gerarToken(usuario);
                return new TokenDTO(usuario.getLogin(), token);
            }

            throw new InvalidPasswordException();

        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Consulta ao repositório para obter o usuário com base no nome de usuário
        // fornecido
        User usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = new String[] { usuario.getRole().toString() };

        // Cria e retorna o objeto UserDetails com os detalhes do usuário
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getPassword())
                .roles(roles)
                .build();
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .build();
    }

    private User extractUser(UserDTO usuario) {
        User user = new User();
        user.setLogin(usuario.getLogin());
        user.setPassword(usuario.getPassword());
        String roleString = usuario.getRole().toUpperCase();
        Role role = Role.valueOf(roleString);
        user.setRole(role);
        return user;
    }

}
