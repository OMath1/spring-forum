package com.example.forum.controller;

import com.example.forum.modelo.Curso;
import com.example.forum.modelo.Topico;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Tras o comportamentos de um rest, assim o spring asssume que todo metodo
// deve ser um @RequestBody

@RestController
public class TopicosController {

    @RequestMapping("/topicos")
    // nao e uma boa pratica devolver o uma classe modelo num metodo request pois
    // nem sempre vc quer serializar todos os dados da classe
    public List<TopicoDto> lista() {
        Topico topico =
                new Topico ("Duvida", "Duvida com Spring",
                new Curso("Spring", "Programaçao"));

        return TopicoDto.paraTopico(List.of(topico));
    }
}
