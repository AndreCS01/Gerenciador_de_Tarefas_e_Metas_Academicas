package com.faculdade.gerenciador_academico.infra;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    // Busca todas as tarefas de uma disciplina específica
    List<Tarefa> findByDisciplinaId(Long disciplinaId);
    
    // Busca todas as tarefas que estão em um status específico (ex: A_FAZER)
    List<Tarefa> findByStatus(StatusTarefa status);
    
    // Busca cruzando os dois filtros
    List<Tarefa> findByDisciplinaIdAndStatus(Long disciplinaId, StatusTarefa status);

    // Busca tarefas cujo prazo está entre hoje e os próximos 7 dias
    List<Tarefa> findByDataLimiteBetween(LocalDate dataInicio, LocalDate dataFim);
    
    // Busca tarefas com data limite menor que hoje e que não estejam concluídas
    List<Tarefa> findByDataLimiteBeforeAndStatusNot(LocalDate data, StatusTarefa status);
}
