package com.example.forum.repository;

import com.example.forum.controller.dto.TopicoDto;
import com.example.forum.modelo.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // o underline("_") indica ao spring que esta sendo acessada um atributo dentro de uma relaçao
    List<Topico> findByCurso_Nome(String nomeCurso);

    @Modifying
    @Query("UPDATE Topico t SET t.mensagem  = ?1, t.titulo = ?2 WHERE t.id = ?3")
    void setTopicoInfoById(String titulo, String mensagem, Long userId);
}
