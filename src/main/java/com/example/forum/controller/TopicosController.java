package com.example.forum.controller;

import com.example.forum.controller.dto.TopicoDto;
import com.example.forum.controller.dto.TopicoForm;
import com.example.forum.modelo.Curso;
import com.example.forum.modelo.Topico;
import com.example.forum.repository.CursoRepository;
import com.example.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

// Tras o comportamentos de um rest, assim o spring assume que todo metodo
// deve ser um @RequestBody

@RestController
@RequestMapping("/topicos")
public class TopicosController {
    // o  @Autowired facilita o acesso a metodos e funcionalidades
    // de metodos, permintindo acessar sem instancia-los.
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    // nao e uma boa pratica devolver o uma classe modelo num metodo request pois
    // nem sempre vc quer serializar todos os dados da classe
    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {
        // Com repository
        if (nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.paraTopico(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
            return TopicoDto.paraTopico(topicos);
        }
        /* Sem repository
        Topico topico =
                new Topico
                        ("Duvida", "Duvida com Spring",
                                new Curso("Spring", "Programaçao"));

        return TopicoDto.paraTopico(List.of(topico)); */
    }

    @PostMapping
    //@RequestBody liga o parametro da requisiçao com o parametro do metodo
    public ResponseEntity<TopicoDto> cadastrar(
            @RequestBody
            TopicoForm form,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Topico topico = form.paraTopico(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TopicoDto> detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(new TopicoDto(topico));
    }
}
