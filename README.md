# Trabalho de Inteligência Artificial 1

#Executando o jogo dois 8 dígitos:
  - Primeiro é necessário importar o projeto: IA1/src/first
  - Antes de compilare executar, deve ser colocado na "VM Arguments" o seguinte comando: -Xss515m, para que a pilha da Heap seja aumentada 
    já que a recursão no dfs pode ser muito grande.
  - Após isso, basta compilar e executar.
  - No começo do programa, basta colocar um caso solúvel (no lugar do 0 ou do vazio, colocar 9), por exemplo:
    1 2 3
    4 5 6
    7 8 9
    E ele irá rodar mostrando o número de iterações do programa (número de nós visitados) e a altura da árvore (que corresponde ao número
    de movimentos para resolver o caso dado).

