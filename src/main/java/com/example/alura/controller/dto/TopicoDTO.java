package com.example.alura.controller.dto;

import com.example.alura.modelo.Topico;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TopicoDTO {

    private Long id;

    private String titulo;

    private String mensagem;

    private LocalDateTime dataCriacao;

    public TopicoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public static Page<TopicoDTO> converter(Page<Topico> topicos) {
        //return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList()); ///pega todas os itens do topico e joga pra uma lista de topicodto
        return topicos.map(TopicoDTO::new); /// O retorno acima serve para listas (stream vem do List), porém para Page deve-se trocar para map
    }
}
