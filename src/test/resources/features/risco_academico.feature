# language: pt
Funcionalidade: Gerenciamento de Tarefas e Risco Academico

  # Cenário 1
  Cenario: Identificar e marcar uma tarefa vencida como atrasada
    Dado que existe uma tarefa cadastrada com o status "A_FAZER"
    E a data limite da tarefa foi ontem
    Quando a rotina de verificacao de atrasos for executada
    Entao o status da tarefa deve ser alterado para "ATRASADO"

  # Cenário 2
  Cenario: Atualizar o status de uma tarefa com sucesso
    Dado que o aluno possui uma tarefa cadastrada com o status "A_FAZER"
    Quando o aluno atualiza o status da tarefa para "CONCLUIDO"
    Entao o sistema deve salvar a tarefa com o status "CONCLUIDO"

  # Cenário 3
  Cenario: Dashboard de pendencias dos proximos 7 dias
    Dado que o aluno possui uma tarefa com vencimento para daqui a 3 dias
    Quando o aluno acessa o painel de pendencias
    Entao a tarefa deve ser retornada na lista

  # Cenário 4
  Cenario: Isolamento de dados entre usuarios diferentes
    Dado que existem tarefas de alunos diferentes no sistema
    Quando o primeiro aluno lista suas tarefas
    Entao o sistema deve retornar apenas as tarefas criadas por ele

  # Cenário 5
  Cenario: Filtrar tarefas especificas por status
    Dado que o aluno possui tarefas "A_FAZER" e "CONCLUIDO"
    Quando o aluno filtra as tarefas pelo status "CONCLUIDO"
    Entao o sistema deve retornar apenas a tarefa finalizada
