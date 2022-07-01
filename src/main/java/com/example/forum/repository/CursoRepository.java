package com.example.forum.repository;

import com.example.forum.modelo.Curso;
import com.example.forum.modelo.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);

}
