package com.example.forum.controller;

import com.example.forum.controller.dto.DetalhesTopicoDto;
import com.example.forum.controller.dto.TopicoDto;
import com.example.forum.controller.form.AtualizacaoTopicoForm;
import com.example.forum.controller.form.TopicoForm;
import com.example.forum.modelo.Topico;
import com.example.forum.repository.CursoRepository;
import com.example.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

// Tras o comportamentos de um rest, assim o spring assume que todo  metodo
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

        /* ------------ Sem repository ----------------
        Topico topico =
                new Topico
                        ("Duvida", "Duvida com Spring",
                                new Curso("Spring", "Programaçao"));

        return TopicoDto.paraTopico(List.of(topico));
        */
    }

    @PostMapping
    //@RequestBody liga o parametro da requisiçao com o parametro do metodo
    public ResponseEntity<DetalhesTopicoDto> cadastrar(
            @RequestBody
            // Avisa ao spring que deve rodar as validaçoes no bean validation
            @Valid
            TopicoForm form,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Topico topico = form.paraTopico(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DetalhesTopicoDto(topico));
    }


    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        // tratando um status:
        return topico
                .map(value -> ResponseEntity.ok(new DetalhesTopicoDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


/*     ----------- ATUALIZA ACESSANDO O METODO NO FORM ---------------
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Topico topico = form.atualizar(id, topicoRepository);

        return ResponseEntity.ok(new TopicoDto(topico));
        }
*/

    //    ---------------- ATUALIZA VIA REPOSITORY --------------
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> topico = topicoRepository.findById(id);

//        if (topico.isPresent()) {
            topicoRepository.setTopicoInfoById(form.getTitulo(),
                    form.getMensagem(),
                    id);
            return ResponseEntity.ok(new TopicoDto(form));
//        }
//        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
