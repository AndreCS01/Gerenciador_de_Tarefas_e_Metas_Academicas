package com.faculdade.gerenciador_academico.ui;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import com.faculdade.gerenciador_academico.domain.Usuario;
import com.faculdade.gerenciador_academico.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService service;

    // Salva a tarefa vinculando-a diretamente ao usuário logado
    @PostMapping
    public ResponseEntity<Tarefa> cadastrar(@RequestBody Tarefa tarefa, @AuthenticationPrincipal Usuario logado) {
        Tarefa novaTarefa = service.salvar(tarefa, logado);
        return ResponseEntity.ok(novaTarefa);
    }

    // Lista e filtra as tarefas garantindo o isolamento do usuário logado
    @GetMapping
    public List<Tarefa> listar(
            @RequestParam(required = false) Long disciplinaId,
            @RequestParam(required = false) StatusTarefa status,
            @AuthenticationPrincipal Usuario logado) {
        
        return service.buscarComFiltros(disciplinaId, status, logado.getId());
    }

    // Atualiza o status da tarefa validando a propriedade antes da alteração
    @PutMapping("/{id}/status")
    public ResponseEntity<Tarefa> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusTarefa status,
            @AuthenticationPrincipal Usuario logado) {
        
        Tarefa tarefaAtualizada = service.atualizarStatus(id, status, logado.getId());
        return ResponseEntity.ok(tarefaAtualizada);
    }

    // Endpoint para o Dashboard de 7 dias do usuário autenticado
    @GetMapping("/pendencias")
    public List<Tarefa> listarPendenciasDaSemana(@AuthenticationPrincipal Usuario logado) {
        return service.buscarPendenciasProximosSeteDias(logado.getId());
    }

    // Endpoint para rodar a rotina de risco acadêmico apenas para as tarefas do usuário logado
    @PostMapping("/verificar-atrasos")
    public ResponseEntity<Void> forcarVerificacaoDeAtrasos(@AuthenticationPrincipal Usuario logado) {
        service.verificarEAtualizarTarefasAtrasadas(logado.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, @AuthenticationPrincipal Usuario logado) {
        service.deletar(id, logado.getId());
        return ResponseEntity.noContent().build();
    }
}
