package com.jeanlima.springrestapiapp.rest.controllers;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.jeanlima.springrestapiapp.utils.Patcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.model.ItemPedido;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.repository.ClienteRepository;
import com.jeanlima.springrestapiapp.rest.dto.InformacaoItemPedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.InformacoesClienteComprasDTO;
import com.jeanlima.springrestapiapp.rest.dto.InformacoesPedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.InformacoesPedidoRelatorioDTO;
import com.jeanlima.springrestapiapp.service.PedidoService;

@RequestMapping("/api/clientes")
@RestController // anotação especializadas de controller - todos já anotados com response body!
public class ClienteController {

    @Autowired
    private ClienteRepository clientes;

    private Patcher patcher;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("{id}")
    public Cliente getClienteById(@PathVariable Integer id) {
        return clientes
                .findById(id)
                .orElseThrow(() -> // se nao achar lança o erro!
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente) {
        return clientes.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientes.findById(id)
                .map(cliente -> {
                    clientes.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
            @RequestBody Cliente cliente) {
        clientes
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody Cliente clienteIncompleto) {
        clientes
                .findById(id)
                .map(clienteExistente -> {
                    patcher.patchProperties(clienteIncompleto, clienteExistente);
                    clientes.save(clienteExistente);
                    return clienteExistente;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado."));
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return clientes.findAll(example);
    }

    @GetMapping("/relatorio/{id}")
    public InformacoesClienteComprasDTO getInformacoesClienteComprasDTO(@PathVariable Integer id){
        Cliente cliente = clientes.findClienteFetchPedidos(id);
        if(cliente == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
    
        return converter(cliente);

    }

    private InformacoesClienteComprasDTO converter(Cliente cliente) {
        return InformacoesClienteComprasDTO.builder()
        .codigo(cliente.getId())
        .cpf(cliente.getCpf())
        .nomeCliente(cliente.getNome())
        .pedidos(converter(cliente.getPedidos()))
        .build();
    }

    private List<InformacoesPedidoRelatorioDTO> converter(Set<Pedido> pedidos) {
        if(CollectionUtils.isEmpty(pedidos)){
            return Collections.emptyList();
        }
        return pedidos.stream().map(
                pedido -> {
                    Pedido pedidoCompleto = pedidoService.obterPedidoCompletoSemOptional(pedido.getId());
                    return InformacoesPedidoRelatorioDTO
                            .builder()
                            .codigo(pedidoCompleto.getId())
                            .dataPedido(pedidoCompleto.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                            .total(pedidoCompleto.getTotal())
                            .status(pedidoCompleto.getStatus().name())
                            .items(converter(pedidoCompleto.getItens()))
                            .build();
                }
                
                
        ).collect(Collectors.toList());
    }   

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                            .builder()
                            .descricaoProduto(item.getProduto().getDescricao())
                            .precoUnitario(item.getProduto().getPreco())
                            .quantidade(item.getQuantidade())
                            .build()
        ).collect(Collectors.toList());
    }

}
