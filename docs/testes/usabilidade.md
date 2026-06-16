# 📝 Teste de Usabilidade - Gerenciador Acadêmico

## ☑ Objetivo do teste[cite: 1]

| Item | Preencher |
| :--- | :--- |
| **Objetivo** | Avaliar se usuários conseguem completar tarefas essenciais (cadastro, login, criar disciplina e criar tarefa) no Gerenciador Acadêmico com clareza e sem ajuda excessiva. |
| **Hipóteses (o que esperamos)** | (H1) Usuários concluem tarefas principais em até 2 minutos cada.<br>(H2) A navegação entre disciplinas e tarefas é intuitiva.<br>(H3) O sistema de datas para as tarefas é claro. |
| **Escopo (telas/funcionalidades)** | Telas de Cadastro, Login, Dashboard (Listagem), Criação de Disciplina e Criação de Tarefa. |

---

## ☑ Participantes (perfil)[cite: 1]

*(Recomendação: mínimo 3 participantes preferencialmente fora do grupo desenvolvedor)*

| ID | Perfil | Nível de experiência | Contexto (onde/como) | Observações |
| :--- | :--- | :--- | :--- | :--- |
| **P1** | Estudante Universitário | Avançado | Remoto (Discord) | Já entende de sistemas web, testou focando na velocidade. |

| **P2** | Prima | Leigo | Presencial | Pouca familiaridade com sistemas de gestão, testou no seu notebook. |

| **P3** | Estudante de Direito em Belo Horizonte | Médio | Presencial | Usa muitos apps no dia a dia, mas nunca usou um gerenciador acadêmico. |

---

## ☑ Ambiente e preparação[cite: 1]

| Item | Descrição |
| :--- | :--- |
| **Dispositivo** | Notebook |
| **Ambiente** | Casa (Localhost) |
| **Versão testada** | MVP Local (Frontend Next.js / Backend Spring Boot) |
| **Gravação** | Não (somente anotações e observação da tela) |
| **Evidências** | Notas do observador e formulário pós-teste |

---

## ☑ Tarefas (roteiro do teste)[cite: 1]

| ID | Descrição | Sucesso (critério) | Tempo alvo | Prioridade |
| :--- | :--- | :--- | :--- | :--- |
| **T1** | Criar uma nova conta no sistema. | Usuário cadastrado e redirecionado. | 02:00 | Alta |
| **T2** | Fazer o login na conta recém-criada. | Acesso concedido ao painel principal. | 01:00 | Alta |
| **T3** | Cadastrar uma nova disciplina. | Disciplina exibida na lista do painel. | 01:30 | Alta |
| **T4** | Criar uma tarefa para essa disciplina. | Tarefa criada com título e data válida. | 02:00 | Alta |
| **T5** | Tentar criar uma tarefa com data no passado. | Sistema deve impedir e mostrar erro claro. | 01:30 | Média |

---

## ☑ Resultados por participante e tarefa[cite: 1]

**Legenda:** ✔️ Concluiu | ⚠️ Concluiu com ajuda | ❌ Não concluiu

| Participante | Tarefa | Status | Tempo (min/seg) | Erros observados | Dificuldades observadas | Comentários do participante | Evidência |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **P1** | T1 | ✔️ | 00:45 | Nenhum | Nenhuma | "Cadastro bem direto ao ponto." | (print) |
| **P1** | T2 | ✔️ | 00:15 | Nenhum | Nenhuma | "Login sem segredos." | (print) |
| **P1** | T3 | ✔️ | 00:30 | Nenhum | Nenhuma | "Faltou só um aviso visual de 'Salvo com sucesso'." | (print) |
| **P1** | T4 | ✔️ | 00:50 | Nenhum | Nenhuma | N/A | (print) |
| **P1** | T5 | ✔️ | 00:20 | Nenhum | Nenhuma | "A validação do backend segurou a data retroativa, legal." | (print) |

| **P2** | T1 | ✔️ | 01:20 | Digitação | Demorou a ver o padrão da senha | "Achei a letra do formulário um pouco pequena." | (nota) |
| **P2** | T2 | ✔️ | 00:30 | Nenhum | Nenhuma | N/A | (nota) |
| **P2** | T3 | ⚠️ | 01:45 | Clique fora | Não sabia se tinha salvo | "Eu cliquei, mas não vi a tela mudar de cara logo de início." | (nota) |
| **P2** | T4 | ⚠️ | 02:10 | Formato data | Tentou digitar a data com barras | "Queria um calendário para clicar no dia, digitar é chato." | (nota) |
| **P2** | T5 | ✔️ | 01:00 | Nenhum |Assustou com o erro | "Apareceu o erro de data inválida, funcionou." | (print) |

| **P3** | T1 | ✔️ | 01:00 | Nenhum | Nenhuma | "Tranquilo de criar a conta." | (print) |
| **P3** | T2 | ✔️ | 00:25 | Nenhum | Nenhuma | N/A | (print) |
| **P3** | T3 | ✔️ | 00:55 | Nenhum | Nenhuma | N/A | (print) |
| **P3** | T4 | ✔️ | 01:10 | Nenhum | Procurou botão de voltar | "Fiquei em dúvida de como voltar para a lista depois de criar." | (print) |
| **P3** | T5 | ✔️ | 00:40 | Nenhum | Nenhuma | "Não deixou criar com a data de ontem, certinho." | (print) |

---

## ☑ Questionário rápido (pós-teste)[cite: 1]

| Participante | Facilidade (0-10) | Clareza (0-10) | Velocidade (0-10) | O que mais gostou? | O que melhoraria? |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **P1** | 9 | 9 | 10 | "O sistema é bem responsivo e rápido." | "Adicionaria notificações do tipo Toast (pop-ups) ao salvar itens." |
| **P2** | 7 | 6 | 8 | "Achei simples, não tem um milhão de botões na tela." | "Colocaria um calendário para escolher a data da tarefa." |
| **P3** | 8 | 8 | 9 | "Gostei da divisão clara entre disciplinas e tarefas." | "Um botão mais claro para voltar ao menu principal." |

---

## ☑ Achados e melhorias (priorização)[cite: 1]

| Achado (O que foi observado) | Onde ocorreu (tarefa) | Impacto | Frequência (P1/P2/P3) | Prioridade | Ação recomendada | Issue (link) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| Ausência de feedback visual ao salvar dados | T3/T4 | Médio | 2/3 | Média | Implementar alertas (Toast) de sucesso após salvar Disciplina/Tarefa. | https://github.com/AndreCS01/Gerenciador_de_Tarefas_e_Metas_Academicas/issues/13 |

| Dificuldade na inserção de datas | T4 | Alto | 1/3 | Alta | Trocar o input de texto de data para um componente de Calendário (Date Picker). | https://github.com/AndreCS01/Gerenciador_de_Tarefas_e_Metas_Academicas/issues/14 |

| Navegação entre criação e listagem confusa | T4 | Baixo | 1/3 | Baixa | Adicionar um botão de "Voltar" ou "Cancelar" nas telas de formulário. | https://github.com/AndreCS01/Gerenciador_de_Tarefas_e_Metas_Academicas/issues/15 |

---

## ☑ Conclusão e decisão[cite: 1]

| Item | Resultado |
| :--- | :--- |
| **Principais pontos positivos** | O fluxo de cadastro e login funcionou sem gargalos para todos os perfis. As regras de negócio (bloqueio de data retroativa) foram acionadas corretamente. |
| **Principais dificuldades** | A falta de um seletor visual de datas (calendário) e a ausência de mensagens de sucesso deixaram usuários leigos ligeiramente confusos sobre o status de suas ações. |
| **Top 3 melhorias** | 1) Adicionar Date Picker no formulário de tarefas.

2) Implementar Toasts de feedback visual.

3) Melhorar botões de navegação (Voltar). |
| **Go/No-Go para entrega** | Go. O sistema atende perfeitamente ao escopo do MVP, garantindo a organização acadêmica básica de forma estável. |
| **Links de evidência** | (pasta /docs/testes/evidencias/) |