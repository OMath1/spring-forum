package com.example.forum.controller.dto;

import com.example.forum.modelo.Curso;
import com.example.forum.modelo.Topico;
import com.example.forum.repository.CursoRepository;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TopicoForm {

    @NotEmpty
    @Length(min = 5)
    private String titulo;
    @NotEmpty
    @Length(min = 5)
    private String mensagem;
    @NotEmpty
    private String nomeCurso;

    public Topico paraTopico(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }
}
