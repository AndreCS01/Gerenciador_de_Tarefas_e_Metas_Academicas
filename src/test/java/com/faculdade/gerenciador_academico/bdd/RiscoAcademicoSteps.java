package com.faculdade.gerenciador_academico.bdd;

import com.faculdade.gerenciador_academico.GerenciadorAcademicoApplication;
import com.faculdade.gerenciador_academico.domain.Disciplina;
import com.faculdade.gerenciador_academico.domain.StatusTarefa;
import com.faculdade.gerenciador_academico.domain.Tarefa;
import com.faculdade.gerenciador_academico.infra.DisciplinaRepository;
import com.faculdade.gerenciador_academico.infra.TarefaRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(classes = GerenciadorAcademicoApplication.class)
public class RiscoAcademicoSteps {

    @Autowired
    private TarefaService service;

    @Autowired
    private TarefaRepository repository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    private Tarefa tarefaTeste;

    @Dado("que existe uma tarefa cadastrada com o status {string}")
    public void que_existe_uma_tarefa_cadastrada_com_o_status(String statusTexto) {
        // Limpa o banco antes do teste para garantir isolamento
        repository.deleteAll();
        disciplinaRepository.deleteAll();
        
        // 1. Cria e salva uma disciplina primeiro para satisfazer o banco
        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Inteligência Artificial");
        disciplina.setProfessor("Professor Padrão");
        disciplina.setSemestre("Atual");
        disciplina = disciplinaRepository.save(disciplina);

        // 2. Cria a tarefa e faz o vínculo
        tarefaTeste = new Tarefa();
        tarefaTeste.setTitulo("Atividade de BDD");
        tarefaTeste.setStatus(StatusTarefa.valueOf(statusTexto));
        tarefaTeste.setDisciplina(disciplina); // <--- A MÁGICA ACONTECE AQUI
    }

    @E("a data limite da tarefa foi ontem")
    public void a_data_limite_da_tarefa_foi_ontem() {
        tarefaTeste.setDataLimite(LocalDate.now().minusDays(1));
        repository.save(tarefaTeste); // Agora o banco vai aceitar salvar!
    }

    @Quando("a rotina de verificacao de atrasos for executada")
    public void a_rotina_de_verificacao_de_atrasos_for_executada() {
        service.verificarEAtualizarTarefasAtrasadas();
    }

    @Entao("o status da tarefa deve ser alterado para {string}")
    public void o_status_da_tarefa_deve_ser_alterado_para(String statusEsperado) {
        List<Tarefa> tarefasNoBanco = repository.findAll();
        Tarefa tarefaAtualizada = tarefasNoBanco.get(0);
        
        assertEquals(StatusTarefa.valueOf(statusEsperado), tarefaAtualizada.getStatus());
    }
}