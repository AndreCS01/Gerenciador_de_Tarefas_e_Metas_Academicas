package com.faculdade.gerenciador_academico.service;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import com.faculdade.gerenciador_academico.domain.Usuario;
import com.faculdade.gerenciador_academico.infra.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    // Vincula a tarefa ao usuário antes de salvar
    public Tarefa salvar(Tarefa tarefa, Usuario usuario) {
        tarefa.setUsuario(usuario);
        return repository.save(tarefa);
    }

    // Lista apenas as tarefas do usuário logado
    public List<Tarefa> listarTodas(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    // Busca com filtros isolando os dados por usuário
    public List<Tarefa> buscarComFiltros(Long disciplinaId, StatusTarefa status, Long usuarioId) {
        if (disciplinaId != null && status != null) {
            return repository.findByDisciplinaIdAndStatusAndUsuarioId(disciplinaId, status, usuarioId);
        } else if (disciplinaId != null) {
            return repository.findByDisciplinaIdAndUsuarioId(disciplinaId, usuarioId);
        } else if (status != null) {
            return repository.findByStatusAndUsuarioId(status, usuarioId);
        }
        return repository.findByUsuarioId(usuarioId);
    }

    // Altera o status verificando se a tarefa pertence ao usuário logado
    public Tarefa atualizarStatus(Long id, StatusTarefa novoStatus, Long usuarioId) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
        
        if (!tarefa.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Acesso negado: esta tarefa não pertence a você.");
        }

        tarefa.setStatus(novoStatus);
        return repository.save(tarefa);
    }

    // Regra 1: Dashboard de pendências isolado por usuário
    public List<Tarefa> buscarPendenciasProximosSeteDias(Long usuarioId) {
        LocalDate hoje = LocalDate.now();
        LocalDate daquiSeteDias = hoje.plusDays(7);
        return repository.findByDataLimiteBetweenAndUsuarioId(hoje, daquiSeteDias, usuarioId);
    }

    // Regra 2: Varre o banco e atualiza atrasos isolando por usuário
    public void verificarEAtualizarTarefasAtrasadas(Long usuarioId) {
        LocalDate hoje = LocalDate.now();
        
        List<Tarefa> tarefasVencidas = repository
                .findByDataLimiteBeforeAndStatusNotAndUsuarioId(hoje, StatusTarefa.CONCLUIDO, usuarioId);

        for (Tarefa tarefa : tarefasVencidas) {
            tarefa.setStatus(StatusTarefa.ATRASADO);
        }
        
        if (!tarefasVencidas.isEmpty()) {
            repository.saveAll(tarefasVencidas);
        }
    }
}
