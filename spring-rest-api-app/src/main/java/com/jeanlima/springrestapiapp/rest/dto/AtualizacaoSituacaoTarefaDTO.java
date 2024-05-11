package com.jeanlima.springrestapiapp.rest.dto;

import com.jeanlima.springrestapiapp.enums.Situacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizacaoSituacaoTarefaDTO {
    private Situacao novaSituacao;
}