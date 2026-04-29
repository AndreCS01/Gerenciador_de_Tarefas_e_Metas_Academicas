package com.faculdade.gerenciador_academico.service;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import com.faculdade.gerenciador_academico.infra.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @InjectMocks
    private TarefaService service;

    private Tarefa tarefaMock;

    @BeforeEach
    void setUp() {
        // Esse método roda antes de CADA teste para preparar os dados
        tarefaMock = new Tarefa();
        tarefaMock.setId(1L);
        tarefaMock.setTitulo("Fazer mini-akinator");
        tarefaMock.setDescricao("Implementar lógica de árvore de decisão para o projeto");
        tarefaMock.setStatus(StatusTarefa.A_FAZER);
    }

    @Test
    void deveSalvarTarefaComSucesso() {
        // Simula o comportamento do repositório
        when(repository.save(any(Tarefa.class))).thenReturn(tarefaMock);

        Tarefa tarefaSalva = service.salvar(tarefaMock);

        // Asserções (Validações)
        assertNotNull(tarefaSalva);
        assertEquals("Fazer mini-akinator", tarefaSalva.getTitulo());
        
        // Verifica se o método save do banco foi chamado exatamente 1 vez
        verify(repository, times(1)).save(tarefaMock);
    }

    @Test
    void deveBuscarTarefaPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefaMock));

        Tarefa encontrada = service.buscarPorId(1L);

        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarTarefaPorIdInexistente() {
        // Simula o banco não encontrando a tarefa
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Verifica se a exceção correta é lançada
        assertThrows(RuntimeException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void deveAtualizarStatusDaTarefaComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefaMock));
        when(repository.save(any(Tarefa.class))).thenReturn(tarefaMock);

        Tarefa atualizada = service.atualizarStatus(1L, StatusTarefa.EM_PROGRESSO);

        // Verifica se o status mudou antes de salvar
        assertEquals(StatusTarefa.EM_PROGRESSO, atualizada.getStatus());
        verify(repository, times(1)).save(tarefaMock);
    }
}