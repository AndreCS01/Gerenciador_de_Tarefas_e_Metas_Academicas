# 📝 Roteiros de Testes - Gerenciador Acadêmico

## Convenções e status

| Item | Padrão |
| :--- | :--- |
| **ID do teste** | RT-XX (ex.: RT-01, RT-02...) |
| **Status** | Planejado, Em execução, Passou, Falhou, Bloqueado |
| **Prioridade** | Alta, Média, Baixa |
| **Evidência** | Print/log/vídeo / link do PR/Issue |

---

## Execução dos Testes

| ID | Prioridade | Funcionalidade | Objetivo | Pré-condição | Dados de teste | Passos | Resultado esperado | Resultado obtido | Status | Evidência |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **RT-01** | Alta | Cadastro de Disciplina | Cadastrar nova disciplina com sucesso (Fluxo Principal 1) | Estar logado no sistema | Nome: "Matemática Discreta", Prof: "João" | 1) Acessar `/disciplinas`. <br>2) Preencher nome e professor. <br>3) Clicar em salvar. | A disciplina deve ser salva no banco e aparecer imediatamente na lista da tela. | (preencher após execução) | Passou | /docs/testes/evidencias/ |
| **RT-02** | Alta | Cadastro de Tarefa | Criar tarefa vinculada a uma disciplina (Fluxo Principal 2) | Ter uma disciplina já cadastrada | Título: "Lista 1", Data: "Amanhã" | 1) Acessar `/tarefas`. <br>2) Selecionar a disciplina. <br>3) Preencher título e data futura. <br>4) Salvar. | Tarefa cadastrada e exibida na listagem da disciplina correspondente. | (preencher após execução) | Passou | /docs/testes/evidencias/ |
| **RT-03** | Média | Cadastro de Usuário | Validar erro de senhas divergentes (Validação 1) | App rodando na rota `/cadastro` | Senha: "123", Confirmação: "321" | 1) Acessar `/cadastro`. <br>2) Preencher e-mail. <br>3) Preencher senhas diferentes. <br>4) Clicar em cadastrar. | O sistema deve bloquear a requisição e mostrar a mensagem "As senhas não coincidem". | (preencher após execução) | Passou | /docs/testes/evidencias/ |
| **RT-04** | Alta | Autenticação (Login) | Validar bloqueio com credenciais inválidas (Validação 2) | App rodando na rota `/login` | E-mail: "teste@teste.com", Senha: "errada" | 1) Acessar `/login`. <br>2) Inserir e-mail válido. <br>3) Inserir senha incorreta. <br>4) Clicar em Entrar. | O sistema deve negar o login e exibir notificação de "Credenciais inválidas" ou "Erro 403/401". | (preencher após execução) | Passou | /docs/testes/evidencias/ |
| **RT-04** | Alta | Autenticação (Login) | Validar bloqueio com credenciais inválidas (Validação 2) | App rodando na rota `/login` | E-mail: "teste@teste.com", Senha: "errada" | 1) Acessar `/login`. <br>2) Inserir e-mail válido. <br>3) Inserir senha incorreta. <br>4) Clicar em Entrar. | O sistema deve negar o login e exibir notificação de "Credenciais inválidas" ou "Erro 403/401". | (preencher após execução) | Passou | /docs/testes/evidencias/ |
| **RT-05** | Média | Cadastro de Tarefa | Impedir criação de tarefa com prazo vencido (Limites/Bordas) | Estar logado e ter disciplina cadastrada | Data: "Ontem" (ex: 01/01/2000) | 1) Acessar `/tarefas`. <br>2) Preencher os dados. <br>3) Colocar uma data no passado. <br>4) Tentar salvar. | O sistema deve aplicar a regra de negócio e impedir o cadastro, retornando erro de data inválida. | (preencher após execução) | Passou | /docs/testes/evidencias/ |