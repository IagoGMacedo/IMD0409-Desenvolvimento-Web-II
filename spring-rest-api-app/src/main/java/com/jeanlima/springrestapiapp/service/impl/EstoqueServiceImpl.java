package com.jeanlima.springrestapiapp.service.impl;

import java.util.Optional;
import java.util.List;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.jeanlima.springrestapiapp.exception.RegraNegocioException;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.repository.EstoqueRepository;
import com.jeanlima.springrestapiapp.repository.ProdutoRepository;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.rest.dto.EstoquePorNomeDTO;

import com.jeanlima.springrestapiapp.service.EstoqueService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;


    @Override
    public Optional<Estoque> getEstoqueById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Estoque salvar(EstoqueDTO dto) {
        Integer idProduto = dto.getProduto();
        Produto produto = produtoRepository.findById(idProduto).orElseThrow(
            () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    )
        );

        Estoque estoque = new Estoque();
        estoque.setQuantidade(dto.getQuantidade());
        estoque.setProduto(produto);

        produto.setEstoque(estoque);
        Estoque estoqueSalvo = repository.save(estoque);
        produtoRepository.save(produto);

        return estoqueSalvo;
    }

    @Override
    public void deletar(Estoque estoque) {
        repository.delete(estoque);
    }

    @Override
    public Estoque atualizar(Estoque estoqueExistente, Estoque novoEstoque) {
        novoEstoque.setId(estoqueExistente.getId());
        return repository.save(novoEstoque);
    }

    @Override
    public Estoque salvar(Estoque estoque) {
        return repository.save(estoque);
    }

    @Override
    public List<Estoque> listaTodos() {
        return repository.findAll();
    }

    @Override
    public List<Estoque> encontrarPorNome(EstoquePorNomeDTO dto) {
        //return repository.encontrarPorNome(dto.getNomeProduto());
        return null;
    }

    
}
