package com.jeanlima.springrestapiapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.jeanlima.springrestapiapp.enums.Situacao;
import com.jeanlima.springrestapiapp.exception.PedidoNaoEncontradoException;
import com.jeanlima.springrestapiapp.exception.RegraNegocioException;
import com.jeanlima.springrestapiapp.model.Tarefa;
import com.jeanlima.springrestapiapp.model.Usuario;
import com.jeanlima.springrestapiapp.repository.TarefaRepository;
import com.jeanlima.springrestapiapp.repository.UsuarioRepository;
import com.jeanlima.springrestapiapp.rest.dto.TarefaDTO;
import com.jeanlima.springrestapiapp.service.TarefaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarefaServiceImpl implements TarefaService {
    private final TarefaRepository repository;

    private final UsuarioRepository usuarioRepository;

    @Override
    public Optional<Tarefa> encontrarPeloId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Tarefa salvar(TarefaDTO tarefaDTO) {
        Integer idUsuario = tarefaDTO.getUsuario();
        Usuario usuario = usuarioRepository
                    .findById(idUsuario)
                    .orElseThrow(() -> new RegraNegocioException("Código de usuário inválido."));

        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTitulo(tarefaDTO.getTitulo());
        novaTarefa.setDescricao(tarefaDTO.getDescricao());
        novaTarefa.setDeadLine(tarefaDTO.getDeadLine());
        novaTarefa.setPrioridade(tarefaDTO.getPrioridade());
        novaTarefa.setSituacao(Situacao.ANDAMENTO);
        novaTarefa.setUsuario(usuario);
        repository.save(novaTarefa);
        return novaTarefa;
    }

    @Override
    public void deletar(Tarefa tarefa) {
        repository.delete(tarefa);
    }

    @Override
    public List<Tarefa> encontrarTodos(Example example) {
        return repository.findAll(example);
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    @Override
    public void atualizarUsuario(Integer id, Integer idUsuario) {
        Usuario usuario = usuarioRepository
                    .findById(idUsuario)
                    .orElseThrow(() -> new RegraNegocioException("Código de usuário inválido."));

        repository
        .findById(id)
        .map( tarefa -> {
            tarefa.setUsuario(usuario);
            return repository.save(tarefa);
        }).orElseThrow(() -> new RegraNegocioException("Tarefa não encontrada") );
    }

    @Override
    public List<Tarefa> consultarTarefasUsuario(Integer id) {
        return repository.consultarTarefasUsuario(id);
    }


   
    
}
