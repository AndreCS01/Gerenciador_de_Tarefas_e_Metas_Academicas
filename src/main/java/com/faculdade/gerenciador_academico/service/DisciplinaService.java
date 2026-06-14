package com.faculdade.gerenciador_academico.service;

import com.faculdade.gerenciador_academico.domain.Disciplina;
import com.faculdade.gerenciador_academico.domain.Usuario;
import com.faculdade.gerenciador_academico.infra.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {
    private final DisciplinaRepository repository;

    public DisciplinaService(DisciplinaRepository repository) {
        this.repository = repository;
    }

    public Disciplina salvar(Disciplina disciplina, Usuario logado) {
        // Carimba a disciplina dizendo quem é o dono dela!
        disciplina.setUsuario(logado);
        
        // Agora sim, salva no banco de dados
        return repository.save(disciplina);
    }

    public List<Disciplina> listarTodas() {
        return repository.findAll();
    }

    public void deletar(Long id, Long usuarioId) {
        Disciplina disciplina = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
            
        // NOVA REGRA: Só bloqueia se a disciplina TIVER um dono, e esse dono for DIFERENTE do usuário atual.
        // Se a disciplina for "órfã" (null), ele deixa apagar para limparmos o banco!
        if (disciplina.getUsuario() != null && !disciplina.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Acesso negado: Você não tem permissão para excluir esta disciplina.");
        }
        
        repository.delete(disciplina);
    }
}
