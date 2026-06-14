package com.faculdade.gerenciador_academico.ui;

import com.faculdade.gerenciador_academico.domain.Disciplina;
import com.faculdade.gerenciador_academico.domain.Usuario;
import com.faculdade.gerenciador_academico.service.DisciplinaService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Disciplina> criar(@RequestBody Disciplina disciplina, @AuthenticationPrincipal Usuario logado) {
        Disciplina novaDisciplina = service.salvar(disciplina, logado);
        return ResponseEntity.ok(novaDisciplina);
    }

    @GetMapping
    public List<Disciplina> listar() {
        return service.listarTodas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, @AuthenticationPrincipal Usuario logado) {
        service.deletar(id, logado.getId());
        return ResponseEntity.noContent().build();
    }
}
