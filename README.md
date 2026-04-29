# 📚 Gerenciador de Tarefas e Metas Acadêmicas

**Resumo:** O Gerenciador de Tarefas e Metas Acadêmicas é um sistema focado em organizar entregas e rotinas de estudantes universitários. A aplicação permite cadastrar disciplinas, gerenciar tarefas, monitorar prazos e aplicar regras de negócio para identificar riscos de atraso, garantindo que o estudante mantenha o controle do seu semestre.

## 🎯 Problema e Público-Alvo
Estudantes universitários frequentemente lidam com múltiplas disciplinas e dezenas de prazos simultâneos, o que pode gerar perda de datas de entrega e queda no rendimento. O público-alvo central são acadêmicos que precisam de uma ferramenta simples, mas rigorosa, para gerenciar suas pendências e evitar o acúmulo de tarefas (Risco Acadêmico).

## ✨ Funcionalidades
1. **Cadastrar Disciplina:** Registro de matérias, professor responsável e semestre.
2. **Cadastrar Tarefa/Meta:** Criação de tarefas vinculadas a uma disciplina, com título, descrição e data limite.
3. **Alterar Status:** Atualização do ciclo de vida da tarefa (A Fazer, Em Progresso, Concluído).
4. **Filtrar Tarefas:** Visualização segmentada por disciplina ou status.
5. **Resumo de Pendências:** Dashboard simples com tarefas que vencem nos próximos 7 dias.
6. **Arquivamento e Análise de Risco (Regra de Negócio):** O sistema verifica automaticamente tarefas com prazos estourados, aplicando a flag de "Atrasado" e "Risco Acadêmico" na disciplina. Tarefas antigas não são apagadas, apenas arquivadas para histórico.

## 🧰 Tecnologias Utilizadas
* **Linguagem:** Java 17+
* **Framework Backend:** Spring Boot
* **Frontend:** Next.js
* **Banco de Dados:** PostgreSQL
* **Build e Dependências:** Maven
* **Testes Unitários:** JUnit 5 / Mockito
* **Testes BDD:** Cucumber
* **Versionamento e Gestão:** GitHub (Git, Issues, Projects)

## 🧱 Estrutura de Pastas (Arquitetura)
O projeto segue a arquitetura de 4 camadas:
```text
/src
 ├── domain     # Entidades principais (Tarefa, Disciplina, Enums)
 ├── service    # Casos de uso e regras de negócio complexas
 ├── infra      # Persistência de dados (Repositories JPA) e configurações
 └── ui         # Controladores REST (Controllers) e tratamento de exceções
/docs
 ├── testes     # Plano de teste, roteiros e evidências
/slides         # Apresentação final do projeto

LINK DO PRÉ-PROJETO: https://1drv.ms/w/c/173998cff18a50ec/IQDQm3aOfTfUR4vm56oZIFMoAdiDm8KfdyohKAXmvOCt2PM?e=gNOVaZ

LINK DO CANVAS: https://1drv.ms/p/c/173998cff18a50ec/IQAvjh9iHGz-Qb8YxDG7iGP-ASAwixbN-LaIcYBoFet9cPU?e=u9GJyx