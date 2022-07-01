package com.example.forum.repository;

import com.example.forum.modelo.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // o underline("_") indica ao spring que esta sendo acessada um atributo dentro de uma relaçao
    List<Topico> findByCurso_Nome(String nomeCurso);
}
