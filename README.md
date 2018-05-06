# Trabalho de Inteligência Artificial 1

## Executando o jogo dois 8 dígitos:
  - Primeiro é necessário importar o projeto: IA1
  - Antes de compilar e executar, deve ser colocado na "VM Arguments" o seguinte comando: -Xss515m, para que a pilha da Heap seja aumentada 
    já que a recursão no dfs pode ser muito grande.
  - Após isso, basta compilar e executar.
  - No começo do programa, basta colocar um caso solúvel (no lugar do 0 ou do vazio, colocar 9), por exemplo:
    1 2 3
    4 5 6
    7 8 9
    E ele irá rodar mostrando o número de iterações do programa (número de nós visitados) e a altura da árvore (que corresponde ao número
    de movimentos para resolver o caso dado).

## Executando o Motor de Inferência:
  - Primeiro é necessário importar o projeto InferenceMotor
  - Ao iniciar o programa, um menu será apresentado, digite:
    - 0 para **sair do programa**;
    
    - 1 para **listar as regras atuais**
    
    - 2 para **adicionar uma nova regra**
    - O programa pedirá uma nova regra, caso a regra não siga os padrões a serem estabelecidos neste documento, ela não será aceita e outra regra será pedida;

    - 3 para **deletar uma regra**
    - O programa listará todas as regras e perguntará qual deve ser removida, deve ser digitado o **número da linha** da regra a ser removida de acordo com as linhas impressas.
    
    - 4 para **entrar um caso de teste para as regras atuais**
    - Responder às perguntas necessárias para que a base de dados chegue a alguma conclusão.
    
    - 5 para **ligar ou desligar o modo de explicação de conclusões passo-a-passo** 
    - A cada resposta, as conclusões feitas a partir dela serão mostradas.

- Formato das Regras:
  - "SE", "ENTAO", "OU" e "E" são palavras reservadas, ou seja, regras não podem usá-las como variáveis nem partes de nomes de variáveis;

  - A forma geral das regras é "SE ... ENTAO ...";

  - Regras podem conter conjunções e disjunções nas formas de "E" e "OU", respectivamente;

  - Conjunções são aceitas tanto nas condições quanto nas conclusões;

  - Porém disjunções somente são aceitas nas condições, ou seja, após "ENTAO" não pode haver "OU".
