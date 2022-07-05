package com.example.forum.controller.dto;

import com.example.forum.controller.form.AtualizacaoTopicoForm;
import com.example.forum.modelo.Topico;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
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

    public TopicoDto(AtualizacaoTopicoForm topico) {
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
    }

    public static List<TopicoDto> paraTopico(List<Topico> topico) {
        // Lista de topicos recebida no param
        return topico
                // retornando o fluxo de dados sem precisar iterar manualmente
                .stream()
                // mapeando o fluxo de TopicoDto vindo do topico
                .map(TopicoDto::new)
                // transforma em uma collection e passa pra lista
                .collect(Collectors.toList());
    }
}
