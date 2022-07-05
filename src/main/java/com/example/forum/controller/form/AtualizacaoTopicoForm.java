package com.example.forum.controller.form;

import com.example.forum.modelo.Topico;
import com.example.forum.repository.TopicoRepository;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class AtualizacaoTopicoForm {
    @NotEmpty
    @Length(min = 5)
    private String titulo;
    @NotEmpty
    @Length(min = 5)
    private String mensagem;

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getReferenceById(id);

        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);

        return topico;
    }
}
