package com.jeanlima.springrestapiapp.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder 
public class InformacoesClienteComprasDTO {
    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private List<InformacoesPedidoRelatorioDTO> pedidos;
}
