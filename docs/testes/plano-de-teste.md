# 📝 Plano de Teste - Gerenciador Acadêmico

## ☑ Identificação e contexto

| Campo | Preencher |
| :--- | :--- |
| **Nome do projeto** | Gerenciador Acadêmico |
| **Objetivo do sistema (resumo)** | Sistema web para gestão de rotina universitária, permitindo o cadastro de disciplinas, o registro de tarefas e a validação de regras de negócio atreladas a prazos e conclusão de atividades. |
| **Público-alvo** | Estudantes universitários que buscam organização acadêmica. |
| **Plataforma/Tipo** | Aplicação Web (Frontend Next.js + API Spring Boot). |
| **Repositório** | https://github.com/AndreCS01/Gerenciador_de_Tarefas_e_Metas_Academicas |
| **Time/Grupo** | André Custódio da Silva |

---

## ☑ Objetivo do teste

| Item | Descrição |
| :--- | :--- |
| **Objetivo geral** | Garantir o funcionamento correto do MVP em ambiente local, validando o fluxo de autenticação e as regras de negócio críticas (como bloqueio de tarefas com datas retroativas), assegurando a estabilidade da integração entre UI e API. |
| **Metas de cobertura** | 80% nas classes de regra de negócio e serviços do backend. |

---

## ☑ Escopo

| Categoria | 🟢 Em escopo | 🔴 Fora de escopo |
| :--- | :--- | :--- |
| **Funcionalidades** | Cadastro/Login de usuário, Criar/Listar Disciplinas, Criar/Listar/Concluir Tarefas. | Recuperação de senha por e-mail, edição de perfil de usuário. |
| **Regras de negócio** | Bloquear a criação de tarefas com datas no passado; validar senhas coincidentes no cadastro. | Notificações push ou por e-mail para prazos próximos. |
| **Integrações** | Integração via API REST local (Next.js comunicando com Spring Boot em `localhost`). | Integrações com APIs externas ou deploy em nuvem (Vercel/Render). |
| **Dados** | Dados determinísticos e controle via banco local (PostgreSQL). | Uso de dados reais ou migração de banco de dados legado. |
| **Não-funcionais** | Teste de usabilidade focado em interface com tarefas guiadas. | Teste de carga, stress e performance em alta escala. |

---

## ☑ Ambiente e ferramentas

| Item | Especificação |
| :--- | :--- |
| **SO** | Windows 10 |
| **Linguagem/Runtime** | Java 21 (Backend) / TypeScript e Node.js (Frontend) |
| **IDE** | VS Code |
| **Build** | Maven (Backend) / npm (Frontend) |
| **Framework de testes unitários** | JUnit 5 |
| **BDD (se houver)** | Cucumber |
| **Banco/Dados** | PostgreSQL (local) |

---

## ☑ Estratégia de testes (por tipo)

| Tipo de teste | Objetivo | Escopo | Ferramenta | Responsável | Saída/Evidência |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Unitário** | Garantir regras e serviços corretos | Regras de criação de tarefas e validações de usuário | JUnit 5 | André Custódio | Relatório `mvn test`, prints/logs |
| **Sistema / End-to-End** | Validar fluxos pela interface Web | Fluxos principais (MVP) | Manual (Roteiros) | André Custódio | RTs executados + prints da tela |
| **BDD** | Validar comportamento de negócio | Criar tarefa e validações de prazo | Cucumber | André Custódio | Relatório de execução + prints |
| **Usabilidade** | Medir clareza de uso da interface | 5 tarefas guiadas com 3 usuários leigos | Manual / Formulário | André Custódio | Tabela de resultados + melhorias |

---

## ☑ Rastreabilidade (Requisitos x Testes)

| ID Req | Requisito/Funcionalidade | Prioridade | Fonte (Issue) | IDs de testes | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **RF-01** | Cadastrar e Autenticar Usuário | Alta | *(Link da Issue)* | UT-01, RT-01 | Planejado |
| **RF-02** | Cadastrar Disciplina | Alta | *(Link da Issue)* | BDD-01, RT-02 | Planejado |
| **RF-03** | Cadastrar Tarefa (validação de data) | Alta | *(Link da Issue)* | UT-02, RT-03 | Planejado |
| **RF-04** | Listar Tarefas por Disciplina | Média | *(Link da Issue)* | RT-04 | Planejado |

*(Convenção: UT = Unitário, BDD = Behavior, RT = Roteiro Manual).*

---

## ☑ Casos de teste planejados (resumo)

| ID | Tipo | Título | Pré-condição | Entrada | Resultado esperado | Prioridade | Automatizado? |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **UT-01** | Unitário | Senhas divergentes | Nenhuma | senha="123", conf="321" | Retornar erro de validação (400) | Alta | Sim |
| **UT-02** | Unitário | Tarefa no passado | Disciplina existente | data="01/01/2000" | Retornar erro de data inválida | Alta | Sim |
| **BDD-01**| BDD | Cadastro de Disciplina | Usuário logado | nome="Lógica" | Disciplina salva com sucesso | Alta | Sim |
| **RT-01** | Manual | Fazer Login | Conta existente | E-mail e senha válidos | Redirecionar ao `/dashboard` | Alta | Não |
| **RT-02** | Manual | Proteção de Rota | Não estar logado | Acessar URL direta | Bloquear e mandar para `/login` | Média | Não |

---

## ☑ Dados de teste

| ID | Conjunto | Descrição | Como criar | Onde armazenar | Observações |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **DT-01** | Usuários | U1 Valido, U2 Senha Errada | Tela de Cadastro | Banco PostgreSQL | Limpar o banco antes da execução oficial |
| **DT-02** | Tarefas | T1 Valida, T2 Data Passada | Formulário Web | Banco PostgreSQL | Usar datas relativas ("amanhã", "ontem") |