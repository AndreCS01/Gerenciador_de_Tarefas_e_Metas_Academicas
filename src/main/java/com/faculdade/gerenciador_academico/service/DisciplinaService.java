package com.faculdade.gerenciador_academico.service;

import com.faculdade.gerenciador_academico.domain.Disciplina;
import com.faculdade.gerenciador_academico.infra.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {
    private final DisciplinaRepository repository;

    public DisciplinaService(DisciplinaRepository repository) {
        this.repository = repository;
    }

    public Disciplina salvar(Disciplina disciplina) {
        return repository.save(disciplina);
    }

    public List<Disciplina> listarTodas() {
        return repository.findAll();
    }
}
