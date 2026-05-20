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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void deveBuscarTarefasPorDisciplinaEStatus() {
        // Simula o banco retornando uma lista com a nossa tarefa mockada
        when(repository.findByDisciplinaIdAndStatus(1L, StatusTarefa.A_FAZER))
                .thenReturn(List.of(tarefaMock));

        List<Tarefa> resultado = service.buscarComFiltros(1L, StatusTarefa.A_FAZER);

        // Validações
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(StatusTarefa.A_FAZER, resultado.get(0).getStatus());
        
        // Verifica se o método correto do repositório foi chamado
        verify(repository, times(1)).findByDisciplinaIdAndStatus(1L, StatusTarefa.A_FAZER);
    }

    @Test
    void deveBuscarTarefasSomentePorDisciplina() {
        when(repository.findByDisciplinaId(1L)).thenReturn(List.of(tarefaMock));

        List<Tarefa> resultado = service.buscarComFiltros(1L, null);

        assertFalse(resultado.isEmpty());
        verify(repository, times(1)).findByDisciplinaId(1L);
        verify(repository, never()).findAll(); // Garante que não buscou tudo atoa
    }

    @Test
    void deveBuscarTarefasSomentePorStatus() {
        when(repository.findByStatus(StatusTarefa.A_FAZER)).thenReturn(List.of(tarefaMock));

        List<Tarefa> resultado = service.buscarComFiltros(null, StatusTarefa.A_FAZER);

        assertFalse(resultado.isEmpty());
        verify(repository, times(1)).findByStatus(StatusTarefa.A_FAZER);
    }

    @Test
    void deveBuscarTodasAsTarefasQuandoSemFiltros() {
        when(repository.findAll()).thenReturn(List.of(tarefaMock));

        List<Tarefa> resultado = service.buscarComFiltros(null, null);

        assertFalse(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }
}