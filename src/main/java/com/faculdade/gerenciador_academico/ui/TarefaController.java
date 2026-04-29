package com.faculdade.gerenciador_academico.ui;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import com.faculdade.gerenciador_academico.service.TarefaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @PostMapping
    public Tarefa criar(@RequestBody Tarefa tarefa) {
        return service.salvar(tarefa);
    }

    @GetMapping
    public List<Tarefa> listar() {
        return service.listarTodas();
    }

    // Endpoint específico para mudar apenas o status da tarefa (usando PATCH)
    @PatchMapping("/{id}/status")
    public Tarefa atualizarStatus(@PathVariable Long id, @RequestParam StatusTarefa status) {
        return service.atualizarStatus(id, status);
    }
}
