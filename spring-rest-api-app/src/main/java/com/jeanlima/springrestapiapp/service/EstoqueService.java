package com.jeanlima.springrestapiapp.service;

import java.util.List;
import java.util.Optional;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.rest.dto.EstoquePorNomeDTO;

public interface EstoqueService {

    Estoque salvar(Estoque estoque);
    Estoque salvar( EstoqueDTO dto );
    Optional<Estoque>  getEstoqueById(Integer id);
    void deletar (Estoque estoque);
    Estoque atualizar (Estoque estoqueExistente, Estoque novoEstoque);

    List<Estoque> listaTodos(Estoque filtro);

    List<Estoque> encontrarPorNome(EstoquePorNomeDTO dto);

}
