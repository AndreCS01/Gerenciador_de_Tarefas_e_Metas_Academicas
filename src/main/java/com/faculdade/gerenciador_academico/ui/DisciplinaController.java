package com.faculdade.gerenciador_academico.ui;

import com.faculdade.gerenciador_academico.domain.Disciplina;
import com.faculdade.gerenciador_academico.service.DisciplinaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @PostMapping
    public Disciplina criar(@RequestBody Disciplina disciplina) {
        return service.salvar(disciplina);
    }

    @GetMapping
    public List<Disciplina> listar() {
        return service.listarTodas();
    }
}
