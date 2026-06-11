package com.faculdade.gerenciador_academico.bdd;

import com.faculdade.gerenciador_academico.GerenciadorAcademicoApplication;
import com.faculdade.gerenciador_academico.domain.Disciplina;
import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import com.faculdade.gerenciador_academico.domain.Usuario;
import com.faculdade.gerenciador_academico.infra.DisciplinaRepository;
import com.faculdade.gerenciador_academico.infra.TarefaRepository;
import com.faculdade.gerenciador_academico.infra.UsuarioRepository;
import com.faculdade.gerenciador_academico.service.TarefaService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(classes = GerenciadorAcademicoApplication.class)
public class RiscoAcademicoSteps {

    @Autowired
    private TarefaService service;
    @Autowired
    private TarefaRepository repository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private Tarefa tarefaTeste;
    private Usuario usuarioTeste;
    private Disciplina disciplinaTeste;
    private Usuario usuarioSecundario;
    private List<Tarefa> resultadoBusca;

    // --- CENÁRIO 1 e 2 ---
    @Dado("que existe uma tarefa cadastrada com o status {string}")
    @Dado("que o aluno possui uma tarefa cadastrada com o status {string}")
    public void prepararTarefaComStatus(String statusTexto) {
        limparBanco();
        criarUsuarioEDisciplinaPadrao();
        
        tarefaTeste = new Tarefa();
        tarefaTeste.setTitulo("Atividade de BDD");
        tarefaTeste.setStatus(StatusTarefa.valueOf(statusTexto));
        tarefaTeste.setUsuario(usuarioTeste);
        tarefaTeste.setDisciplina(disciplinaTeste); // Vínculo adicionado!
        repository.save(tarefaTeste);
    }

    @E("a data limite da tarefa foi ontem")
    public void a_data_limite_da_tarefa_foi_ontem() {
        tarefaTeste.setDataLimite(LocalDate.now().minusDays(1));
        repository.save(tarefaTeste);
    }

    @Quando("a rotina de verificacao de atrasos for executada")
    public void a_rotina_de_verificacao_de_atrasos_for_executada() {
        service.verificarEAtualizarTarefasAtrasadas(usuarioTeste.getId());
    }

    @Quando("o aluno atualiza o status da tarefa para {string}")
    public void atualizarStatusDaTarefa(String novoStatus) {
        service.atualizarStatus(tarefaTeste.getId(), StatusTarefa.valueOf(novoStatus), usuarioTeste.getId());
    }

    @Entao("o status da tarefa deve ser alterado para {string}")
    @Entao("o sistema deve salvar a tarefa com o status {string}")
    public void verificarStatus(String statusEsperado) {
        Tarefa tarefaAtualizada = repository.findById(tarefaTeste.getId()).get();
        assertEquals(StatusTarefa.valueOf(statusEsperado), tarefaAtualizada.getStatus());
    }

    // --- CENÁRIO 3 ---
    @Dado("que o aluno possui uma tarefa com vencimento para daqui a 3 dias")
    public void prepararTarefaProximosDias() {
        prepararTarefaComStatus("A_FAZER");
        tarefaTeste.setDataLimite(LocalDate.now().plusDays(3));
        repository.save(tarefaTeste);
    }

    @Quando("o aluno acessa o painel de pendencias")
    public void acessarPainelPendencias() {
        resultadoBusca = service.buscarPendenciasProximosSeteDias(usuarioTeste.getId());
    }

    @Entao("a tarefa deve ser retornada na lista")
    public void verificarTarefaNaLista() {
        assertFalse(resultadoBusca.isEmpty());
        assertEquals(tarefaTeste.getId(), resultadoBusca.get(0).getId());
    }

    // --- CENÁRIO 4 ---
    @Dado("que existem tarefas de alunos diferentes no sistema")
    public void prepararTarefasAlunosDiferentes() {
        limparBanco();
        criarUsuarioEDisciplinaPadrao(); 
        
        usuarioSecundario = new Usuario();
        usuarioSecundario.setLogin("aluno2@teste.com");
        usuarioSecundario.setSenha("123");
        usuarioSecundario = usuarioRepository.save(usuarioSecundario);

        Disciplina d2 = new Disciplina();
        d2.setNome("Outra Disciplina");
        d2.setProfessor("Outro Professor");
        d2.setUsuario(usuarioSecundario);
        d2 = disciplinaRepository.save(d2);

        Tarefa t1 = new Tarefa();
        t1.setTitulo("Tarefa do Aluno 1");
        t1.setStatus(StatusTarefa.A_FAZER);
        t1.setUsuario(usuarioTeste);
        t1.setDisciplina(disciplinaTeste); // Vínculo adicionado!
        repository.save(t1);

        Tarefa t2 = new Tarefa();
        t2.setTitulo("Tarefa do Aluno 2");
        t2.setStatus(StatusTarefa.A_FAZER);
        t2.setUsuario(usuarioSecundario);
        t2.setDisciplina(d2); // Vínculo adicionado!
        repository.save(t2);
    }

    @Quando("o primeiro aluno lista suas tarefas")
    public void listarTarefasPrimeiroAluno() {
        resultadoBusca = service.listarTodas(usuarioTeste.getId());
    }

    @Entao("o sistema deve retornar apenas as tarefas criadas por ele")
    public void verificarIsolamentoDeDados() {
        assertEquals(1, resultadoBusca.size());
        assertEquals(usuarioTeste.getId(), resultadoBusca.get(0).getUsuario().getId());
    }

    // --- CENÁRIO 5 ---
    @Dado("que o aluno possui tarefas {string} e {string}")
    public void prepararTarefasMultiplosStatus(String status1, String status2) {
        limparBanco();
        criarUsuarioEDisciplinaPadrao();

        Tarefa t1 = new Tarefa();
        t1.setTitulo("Atividade 1");
        t1.setStatus(StatusTarefa.valueOf(status1));
        t1.setUsuario(usuarioTeste);
        t1.setDisciplina(disciplinaTeste); // Vínculo adicionado!
        repository.save(t1);

        Tarefa t2 = new Tarefa();
        t2.setTitulo("Atividade 2");
        t2.setStatus(StatusTarefa.valueOf(status2));
        t2.setUsuario(usuarioTeste);
        t2.setDisciplina(disciplinaTeste); // Vínculo adicionado!
        repository.save(t2);
    }

    @Quando("o aluno filtra as tarefas pelo status {string}")
    public void filtrarPorStatus(String statusBusca) {
        resultadoBusca = service.buscarComFiltros(null, StatusTarefa.valueOf(statusBusca), usuarioTeste.getId());
    }

    @Entao("o sistema deve retornar apenas a tarefa finalizada")
    public void verificarRetornoFiltro() {
        assertEquals(1, resultadoBusca.size());
        assertEquals(StatusTarefa.CONCLUIDO, resultadoBusca.get(0).getStatus());
    }

    // --- MÉTODOS AUXILIARES ---
    private void limparBanco() {
        repository.deleteAll();
        disciplinaRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    private void criarUsuarioEDisciplinaPadrao() {
        usuarioTeste = new Usuario();
        usuarioTeste.setLogin("aluno_bdd@teste.com");
        usuarioTeste.setSenha("123");
        usuarioTeste = usuarioRepository.save(usuarioTeste);

        // A criação da disciplina que estava faltando!
        disciplinaTeste = new Disciplina();
        disciplinaTeste.setNome("Inteligência Artificial");
        disciplinaTeste.setProfessor("Professor Padrão");
        disciplinaTeste.setUsuario(usuarioTeste);
        disciplinaTeste = disciplinaRepository.save(disciplinaTeste);
    }
}
