package com.faculdade.gerenciador_academico.service;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import com.faculdade.gerenciador_academico.infra.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {
    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public Tarefa salvar(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return repository.findAll();
    }

    // Novo método para buscar com filtros
    public List<Tarefa> buscarComFiltros(Long disciplinaId, StatusTarefa status) {
        if (disciplinaId != null && status != null) {
            return repository.findByDisciplinaIdAndStatus(disciplinaId, status);
        } else if (disciplinaId != null) {
            return repository.findByDisciplinaId(disciplinaId);
        } else if (status != null) {
            return repository.findByStatus(status);
        }
        return repository.findAll(); // Se não passar nenhum filtro, traz todas
    }

    public Tarefa buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public Tarefa atualizarStatus(Long id, StatusTarefa novoStatus) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setStatus(novoStatus);
        return repository.save(tarefa);
    }
}
