package com.faculdade.gerenciador_academico.infra;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    // Busca todas as tarefas de uma disciplina específica
    List<Tarefa> findByDisciplinaId(Long disciplinaId);
    
    // Busca todas as tarefas que estão em um status específico (ex: A_FAZER)
    List<Tarefa> findByStatus(StatusTarefa status);
    
    // Busca cruzando os dois filtros
    List<Tarefa> findByDisciplinaIdAndStatus(Long disciplinaId, StatusTarefa status);
}
