package com.faculdade.gerenciador_academico.infra;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    // Busca todas as tarefas do usuário logado
    List<Tarefa> findByUsuarioId(Long usuarioId);
    
    // Filtros de busca protegidos
    List<Tarefa> findByDisciplinaIdAndUsuarioId(Long disciplinaId, Long usuarioId);
    List<Tarefa> findByStatusAndUsuarioId(StatusTarefa status, Long usuarioId);
    List<Tarefa> findByDisciplinaIdAndStatusAndUsuarioId(Long disciplinaId, StatusTarefa status, Long usuarioId);
    
    // Regras de Risco Acadêmico protegidas
    List<Tarefa> findByDataLimiteBetweenAndUsuarioId(LocalDate dataInicio, LocalDate dataFim, Long usuarioId);
    List<Tarefa> findByDataLimiteBeforeAndStatusNotAndUsuarioId(LocalDate data, StatusTarefa status, Long usuarioId);
}