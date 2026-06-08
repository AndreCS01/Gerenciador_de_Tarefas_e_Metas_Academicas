package com.faculdade.gerenciador_academico.service;

import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import com.faculdade.gerenciador_academico.domain.Usuario;
import com.faculdade.gerenciador_academico.infra.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @InjectMocks
    private TarefaService service;

    private Tarefa tarefaMock;
    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        // Criamos um usuário mockado para os testes
        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setLogin("teste@aluno.com");
        usuarioMock.setSenha("123");

        tarefaMock = new Tarefa();
        tarefaMock.setId(1L);
        tarefaMock.setTitulo("Estudar Spring Security");
        tarefaMock.setStatus(StatusTarefa.A_FAZER);
        tarefaMock.setDataLimite(LocalDate.now().plusDays(3));
        tarefaMock.setUsuario(usuarioMock); // Vinculamos a tarefa ao usuário
    }

    @Test
    void deveSalvarTarefaComSucesso() {
        when(repository.save(any(Tarefa.class))).thenReturn(tarefaMock);
        Tarefa resultado = service.salvar(tarefaMock, usuarioMock);
        assertNotNull(resultado);
        assertEquals(usuarioMock, resultado.getUsuario());
        verify(repository, times(1)).save(tarefaMock);
    }

    @Test
    void deveListarTodasAsTarefas() {
        when(repository.findByUsuarioId(1L)).thenReturn(List.of(tarefaMock));
        List<Tarefa> resultado = service.listarTodas(1L);
        assertFalse(resultado.isEmpty());
        verify(repository, times(1)).findByUsuarioId(1L);
    }

    @Test
    void deveAtualizarStatusDaTarefaComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefaMock));
        when(repository.save(any(Tarefa.class))).thenReturn(tarefaMock);

        Tarefa resultado = service.atualizarStatus(1L, StatusTarefa.CONCLUIDO, 1L);

        assertEquals(StatusTarefa.CONCLUIDO, resultado.getStatus());
        verify(repository, times(1)).save(tarefaMock);
    }

    @Test
    void deveLancarExcecaoAoAtualizarTarefaDeOutroUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefaMock)); 

        // Tentando acessar com o usuário 2L (sendo que a tarefa é do 1L)
        assertThrows(RuntimeException.class, () -> {
            service.atualizarStatus(1L, StatusTarefa.CONCLUIDO, 2L);
        });

        verify(repository, never()).save(any(Tarefa.class));
    }

    @Test
    void deveBuscarTarefasPorDisciplinaEStatus() {
        when(repository.findByDisciplinaIdAndStatusAndUsuarioId(1L, StatusTarefa.A_FAZER, 1L))
                .thenReturn(List.of(tarefaMock));

        List<Tarefa> resultado = service.buscarComFiltros(1L, StatusTarefa.A_FAZER, 1L);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarTarefasSomentePorDisciplina() {
        when(repository.findByDisciplinaIdAndUsuarioId(1L, 1L)).thenReturn(List.of(tarefaMock));
        List<Tarefa> resultado = service.buscarComFiltros(1L, null, 1L);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarTarefasSomentePorStatus() {
        when(repository.findByStatusAndUsuarioId(StatusTarefa.A_FAZER, 1L)).thenReturn(List.of(tarefaMock));
        List<Tarefa> resultado = service.buscarComFiltros(null, StatusTarefa.A_FAZER, 1L);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarTodasAsTarefasQuandoSemFiltros() {
        when(repository.findByUsuarioId(1L)).thenReturn(List.of(tarefaMock));
        List<Tarefa> resultado = service.buscarComFiltros(null, null, 1L);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarPendenciasDosProximosSeteDias() {
        LocalDate hoje = LocalDate.now();
        LocalDate daquiSeteDias = hoje.plusDays(7);
        
        when(repository.findByDataLimiteBetweenAndUsuarioId(hoje, daquiSeteDias, 1L))
                .thenReturn(List.of(tarefaMock));

        List<Tarefa> resultado = service.buscarPendenciasProximosSeteDias(1L);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveAtualizarParaAtrasadoTarefasVencidasENaoConcluidas() {
        LocalDate hoje = LocalDate.now();
        
        when(repository.findByDataLimiteBeforeAndStatusNotAndUsuarioId(hoje, StatusTarefa.CONCLUIDO, 1L))
                .thenReturn(List.of(tarefaMock));

        service.verificarEAtualizarTarefasAtrasadas(1L);

        assertEquals(StatusTarefa.ATRASADO, tarefaMock.getStatus());
        verify(repository, times(1)).saveAll(anyList());
    }
}
