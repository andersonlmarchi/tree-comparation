package avl;

public class ArvoreAvl {

    private class Nodo {

        private int chave, altd, alte, qtde;
        private Nodo esq, dir;

        public Nodo(int item) {
            this.chave = item;
            this.altd = 0;
            this.alte = 0;
            this.qtde = 1;
            this.esq = null;
            this.dir = null;
        }

    }

    Nodo raiz;

    public ArvoreAvl() {
        raiz = null;   
    }

    public void inserir(int chave) {
        raiz = inserirNodo(raiz, chave);
    }

    private Nodo inserirNodo(Nodo raiz, int chave) {
        if (raiz == null) {
            raiz = new Nodo(chave);
            return raiz;
        }

        if (chave < raiz.chave) {
            raiz.esq = inserirNodo(raiz.esq, chave);

            if (raiz.esq.altd > raiz.esq.alte) {
                raiz.alte = raiz.esq.altd + 1;
            } else {
                raiz.alte = raiz.esq.alte + 1;
            }

            raiz = balancear(raiz);

        } else if(chave > raiz.chave) {
            raiz.dir = inserirNodo(raiz.dir, chave);

            if (raiz.dir.altd > raiz.dir.alte) {
                raiz.altd = raiz.dir.altd + 1;
            } else {
                raiz.altd = raiz.dir.alte + 1;
            }

            raiz = balancear(raiz);

        } else {
            raiz.qtde++;
        }

        return raiz;
    }

    private Nodo balancear(Nodo raiz) {
        int fb = raiz.altd - raiz.alte;
        int fbs;

        if (fb == 2) {
            fbs = raiz.dir.altd - raiz.dir.alte;
            if (fbs >= 0) {
                raiz = rotacionarEsquerda(raiz);
            } else {
                raiz.dir = rotacionarDireita(raiz.dir);
                raiz = rotacionarEsquerda(raiz);
            }
        } else if (fb == -2) {
            fbs = raiz.esq.altd - raiz.esq.alte;
            if (fbs <= 0) {
                raiz = rotacionarDireita(raiz);
            } else {
                raiz.esq = rotacionarEsquerda(raiz.esq);
                raiz = rotacionarDireita(raiz);
            }
        }        
        return raiz;
    }

    private Nodo rotacionarEsquerda(Nodo raiz) {

        Nodo aux1, aux2;
        aux1 = raiz.dir;
        aux2 = aux1.esq;

        raiz.dir = aux2;
        aux1.esq = raiz;

        if (raiz.dir == null) {
            raiz.altd = 0;
        } else if (raiz.dir.alte > raiz.dir.altd) {
            raiz.altd = raiz.dir.alte + 1;
        } else {
            raiz.altd = raiz.dir.altd + 1;
        }

        if (aux1.esq.alte > aux1.esq.altd) {
            aux1.alte = aux1.esq.alte + 1;
        } else {
            aux1.alte = aux1.esq.altd + 1;
        }

        return aux1;
    }

    private Nodo rotacionarDireita(Nodo raiz) {
       Nodo aux1, aux2;
       aux1 = raiz.esq;
       aux2 = aux1.dir;
       
       raiz.esq = aux2;
       aux1.dir = raiz;

       if (raiz.esq == null) {
           raiz.alte = 0;
       } else if (raiz.esq.alte > raiz.esq.altd) {
           raiz.alte = raiz.esq.alte + 1;
       } else {
           raiz.alte = raiz.esq.altd + 1;
       }

       if (aux1.dir.alte > aux1.dir.altd) {
           aux1.altd = aux1.dir.alte + 1;
       } else {
           aux1.altd = aux1.dir.altd + 1;
       }

       return aux1;
       
    }

    public void visualizar () {
        visualizarOrdenado(raiz);
    }

    private void visualizarOrdenado(Nodo raiz) {
        if (raiz != null) {
            visualizarOrdenado(raiz.esq);
            System.out.print(raiz.chave + " ");
            visualizarOrdenado(raiz.dir);
        }
    }

    public Nodo buscar(int chave) {
        return buscarNodo(raiz, chave);
    }

    private Nodo buscarNodo(Nodo raiz, int chave) {
        if (raiz == null || raiz.chave == chave) {
            return raiz;
        }

        if (chave < raiz.chave) {
            return buscarNodo(raiz.esq, chave);
        } else {
            return buscarNodo(raiz.dir, chave);
        }
    }

    // 1) Desenhe a árvore AVL que resulta da inserção sucessiva das chaves Q-U-E-S-T-A-O-F-C-I-L. Após as inserções desenhe as árvores resultantes das retiradas dos elementos E e depois U.
    // Resolvido em ArvoreAvlChar

    // 2) Escreva o método de exclusão de um elemento da árvore AVL.
    public void remover(int chave) {
        raiz = removerNodo(raiz, chave);
    }

    private Nodo removerNodo(Nodo raiz, int chave) {
        if (raiz == null) {
            return raiz;
        }
        
        if (chave < raiz.chave) {
            raiz.esq = removerNodo(raiz.esq, chave);
        } else if (chave > raiz.chave) {
            raiz.dir = removerNodo(raiz.dir, chave);
        } else {
            if (raiz.esq == null && raiz.dir == null) {
                raiz = null;
            } else if (raiz.esq == null) {
                raiz = raiz.dir;
            } else if (raiz.dir == null) {
                raiz = raiz.esq;
            } else {
                raiz.chave = sucessor(raiz);
                raiz.dir = removerNodo(raiz.dir, raiz.chave);
            }
        }

        return raiz;
    }

    private int sucessor(Nodo raiz) {
        Nodo aux = raiz.esq;
        while (aux.dir != null) {
            aux = aux.dir;
        }
        return aux.chave;
    }

    // 3) Escreva um algoritmo que retorna true se uma determinada árvore é uma árvore AVL e false caso contrário.
    public boolean verificaArvore() {
        return ehArvoreAVL(raiz);
    }

    private boolean ehArvoreAVL(Nodo raiz) {

        if (raiz == null) {
            return true;
        }

        if (raiz.altd - raiz.alte > 1 || raiz.altd - raiz.alte < -1){
            return false;
        } else {
            return ehArvoreAVL(raiz.esq) && ehArvoreAVL(raiz.dir);
        }
    }

    // 4) Faça uma função que, dada uma árvore AVL, retorne à quantidade de nós que guardam números primos.
    public int contarFolhasComNumerosPrimos() {
        return quantasFolhasSaoPrimos(raiz);
    }

    private int quantasFolhasSaoPrimos(Nodo raiz) {
        int count = 0;

        if (raiz == null) {
            System.out.println("Arvore está vazia!");
            return 0;
        }

        if (raiz.esq == null && raiz.dir == null) {
            if(ehPrimo(raiz.chave)) count++;
        } else {
            count += (raiz.esq != null) ? quantasFolhasSaoPrimos(raiz.esq) : 0;
            count += (raiz.dir != null) ? quantasFolhasSaoPrimos(raiz.dir) : 0;
        }

        return count;
    }

    private boolean ehPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i*i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // 5) Modificar o código da Árvore AVL para permitir a inserção de chaves duplicadas. Para fazer isso, você deve acrescentar um atributo adicional à classe Nodo que armazena quantas vezes uma chave específica foi inserida na árvore. Modifique também os métodos de mostrar a árvore e remoção para levar em consideração a contagem das chaves. Por exemplo, ao mostrar os nós da árvore, o método deve mostrar ou retornar, além da chave do nó, o número de ocorrências da chave.
    public void visualizarComQtde() {
        visualizarComQtdeOrdenado(raiz);
    }

    private void visualizarComQtdeOrdenado(Nodo raiz) {
        if (raiz != null) {
            visualizarComQtdeOrdenado(raiz.esq);
            System.out.print("Chave: " + raiz.chave + " - Qtde: " + raiz.qtde + " | ");
            visualizarComQtdeOrdenado(raiz.dir);
        }
    }

    // 6) Escreva um método que receba um nível da arvore e mostre todos os nodos nesse nível
    public void mostrarNodosDoNivel(int nivel) {
        listarNodosDoNivel(raiz, nivel);
    }

    private void listarNodosDoNivel(Nodo raiz, int nivel) {
        if (raiz == null) {
            return;
        }
    
        if (nivel == 1) {
            System.out.print(raiz.chave + " ");
        }
        
        if (nivel > 1) {
            if (raiz.esq != null) {
                listarNodosDoNivel(raiz.esq, nivel - 1);
            }
            if (raiz.dir != null) {
                listarNodosDoNivel(raiz.dir, nivel - 1);
            }
        }
    }

    // 7) Faça um método para somar os nós presentes nos níveis ímpares de uma árvore AVL. (Quantidade de Nodos)
    public int somarNodos() {
        return somarNodosImpares(raiz);                
    }

    private int somarNodosImpares(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
    
        int soma = 0;
    
        if (raiz.chave % 2 != 0) {
            soma += raiz.chave;
        }
    
        soma += somarNodosImpares(raiz.esq);
        soma += somarNodosImpares(raiz.dir);
    
        return soma;
    }
    

    // 8) Monte uma arvore capaz de armazenar as seguintes informações: nome do município, área total do município (em km2) e população. A chave do nó para inserção deverá ser o nome do município. Implemente também as funções abaixo:
        // a. Contar o número de municípios, percorrendo os nós cadastrados na árvore.
        // b. Mostrar apenas os nomes dos municípios com mais de X habitantes. Por exemplo, X
        // pode ser 100.000 pessoas.
        // c. Mostrar a densidade demográfica de cada cidade. A densidade demográfica é a
        // relação entre a população e a área.
        // d. Mostrar o somatório de área em km2 de todas as cidades juntas em relação ao
        // território nacional (em porcentagem).
        // e. Mostrar o nome da cidade com a maior população.
        // Resolvido em ArvoreAvlMunicipio

}
