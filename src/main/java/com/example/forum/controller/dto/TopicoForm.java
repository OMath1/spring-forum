package com.example.forum.controller.dto;

import com.example.forum.modelo.Curso;
import com.example.forum.modelo.Topico;
import com.example.forum.repository.CursoRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicoForm {
    private String titulo;
    private String mensagem;
    private String nomeCurso;

    public Topico paraTopico(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }
}
