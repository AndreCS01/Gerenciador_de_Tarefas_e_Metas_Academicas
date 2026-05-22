# language: pt
Funcionalidade: Alerta de Risco Acadêmico
  Como um sistema de gerenciamento
  Eu quero verificar as tarefas atrasadas
  Para alertar o aluno sobre o Risco Acadêmico

  Cenário: Identificar e marcar uma tarefa vencida como atrasada
    Dado que existe uma tarefa cadastrada com o status "A_FAZER"
    E a data limite da tarefa foi ontem
    Quando a rotina de verificacao de atrasos for executada
    Entao o status da tarefa deve ser alterado para "ATRASADO"