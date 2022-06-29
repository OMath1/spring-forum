package com.example.forum.controller;

import com.example.forum.modelo.Topico;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TopicoDto {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public static List<TopicoDto> paraTopico(List<Topico> topico) {
        return topico
                .stream()
                .map(TopicoDto::new)
                .collect(Collectors.toList());
    }
}
