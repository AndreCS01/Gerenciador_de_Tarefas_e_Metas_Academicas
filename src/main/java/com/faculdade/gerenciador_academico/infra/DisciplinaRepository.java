package com.faculdade.gerenciador_academico.infra;

import com.faculdade.gerenciador_academico.domain.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByUsuarioId(Long usuarioId);
}
