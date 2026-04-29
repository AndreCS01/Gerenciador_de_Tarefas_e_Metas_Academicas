package com.faculdade.gerenciador_academico.infra;

import com.faculdade.gerenciador_academico.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    // Esse método mágico do Spring já busca as tarefas filtrando pela disciplina
    List<Tarefa> findByDisciplinaId(Long disciplinaId);
}
