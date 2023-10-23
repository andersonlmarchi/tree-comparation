package avl;

class ArvoreAvlChar {
    
    String chave;
    int altura;
    ArvoreAvlChar esq, dir;

    public ArvoreAvlChar(String chave) {
        this.chave = chave;
        this.altura = 1;
    }

    public static void main(String[] args) {
        ArvoreAVL arvore = new ArvoreAVL();

        String[] chaves = {"Q", "U", "E", "S", "T", "A", "O", "F", "C", "I", "L"};

        for (String chave : chaves) {
            arvore.raiz = arvore.inserir(arvore.raiz, chave);
        }

        System.out.println("Árvore AVL após inserções:");
        arvore.imprimir(arvore.raiz);

        // Removendo E
        arvore.raiz = arvore.remover(arvore.raiz, "E");
        System.out.println("\n\nÁrvore AVL após remoção de E:");
        arvore.imprimir(arvore.raiz);

        // Removendo U
        arvore.raiz = arvore.remover(arvore.raiz, "U");
        System.out.println("\n\nÁrvore AVL após remoção de U:");
        arvore.imprimir(arvore.raiz);

        System.out.println("\n");
    }
}

class ArvoreAVL {
    ArvoreAvlChar raiz;
    
    // Método auxiliar para obter a altura de um nó
    private int altura(ArvoreAvlChar raiz) {
        return (raiz != null) ? raiz.altura : 0;
    }

    // Método auxiliar para atualizar a altura de um nó
    private void atualizarAltura(ArvoreAvlChar raiz) {
        raiz.altura = Math.max(altura(raiz.esq), altura(raiz.dir)) + 1;
    }

    // Método auxiliar para calcular o fator de balanceamento de um nó
    private int fatorBalanceamento(ArvoreAvlChar raiz) {
        return (raiz != null) ? altura(raiz.esq) - altura(raiz.dir) : 0;
    }

    // Método de rotação à esquerda
    private ArvoreAvlChar rotacaoEsquerda(ArvoreAvlChar raiz) {
        ArvoreAvlChar aux = raiz.dir;
        ArvoreAvlChar aux1 = aux.esq;

        aux.esq = raiz;
        raiz.dir = aux1;

        atualizarAltura(raiz);
        atualizarAltura(aux);

        return aux;
    }

    // Método de rotação à direita
    private ArvoreAvlChar rotacaoDireita(ArvoreAvlChar raiz) {
        ArvoreAvlChar aux = raiz.esq;
        ArvoreAvlChar aux1 = aux.dir;

        aux.dir = raiz;
        raiz.esq = aux1;

        atualizarAltura(raiz);
        atualizarAltura(aux);

        return aux;
    }

    // Método para inserir uma chave na árvore
    public ArvoreAvlChar inserir(ArvoreAvlChar raiz, String chave) {
        if (raiz == null)
            return new ArvoreAvlChar(chave);

        if (chave.compareTo(raiz.chave) < 0)
            raiz.esq = inserir(raiz.esq, chave);
        else if (chave.compareTo(raiz.chave) > 0)
            raiz.dir = inserir(raiz.dir, chave);
        else // Chaves iguais não são permitidas
            return raiz;

        // Atualiza a altura do nó atual
        atualizarAltura(raiz);

        // Verifica o fator de balanceamento e realiza as rotações, se necessário
        int balanceamento = fatorBalanceamento(raiz);

        if (balanceamento > 1 && chave.compareTo(raiz.esq.chave) < 0)
            return rotacaoDireita(raiz);

        if (balanceamento < -1 && chave.compareTo(raiz.dir.chave) > 0)
            return rotacaoEsquerda(raiz);

        if (balanceamento > 1 && chave.compareTo(raiz.esq.chave) > 0) {
            raiz.esq = rotacaoEsquerda(raiz.esq);
            return rotacaoDireita(raiz);
        }

        if (balanceamento < -1 && chave.compareTo(raiz.dir.chave) < 0) {
            raiz.dir = rotacaoDireita(raiz.dir);
            return rotacaoEsquerda(raiz);
        }

        return raiz;
    }

    // Método para encontrar o nó com a menor chave na subárvore
    private ArvoreAvlChar encontrarMenor(ArvoreAvlChar raiz) {
        ArvoreAvlChar atual = raiz;
        while (atual.esq != null)
            atual = atual.esq;
        return atual;
    }

    // Método para remover uma chave da árvore
    public ArvoreAvlChar remover(ArvoreAvlChar raiz, String chave) {
        if (raiz == null)
            return null;

        if (chave.compareTo(raiz.chave) < 0)
            raiz.esq = remover(raiz.esq, chave);
        else if (chave.compareTo(raiz.chave) > 0)
            raiz.dir = remover(raiz.dir, chave);
        else {
            if ((raiz.esq == null) || (raiz.dir == null)) {
                ArvoreAvlChar aux = null;
                if (aux == raiz.esq)
                    aux = raiz.dir;
                else
                    aux = raiz.esq;

                if (aux == null) {
                    aux = raiz;
                    raiz = null;
                } else
                    raiz = aux;
            } else {
                ArvoreAvlChar aux = encontrarMenor(raiz.dir);
                raiz.chave = aux.chave;
                raiz.dir = remover(raiz.dir, aux.chave);
            }
        }

        if (raiz == null)
            return null;

        // Atualiza a altura do nó atual
        atualizarAltura(raiz);

        // Verifica o fator de balanceamento e realiza as rotações, se necessário
        int balanceamento = fatorBalanceamento(raiz);

        if (balanceamento > 1 && fatorBalanceamento(raiz.esq) >= 0)
            return rotacaoDireita(raiz);

        if (balanceamento > 1 && fatorBalanceamento(raiz.esq) < 0) {
            raiz.esq = rotacaoEsquerda(raiz.esq);
            return rotacaoDireita(raiz);
        }

        if (balanceamento < -1 && fatorBalanceamento(raiz.dir) <= 0)
            return rotacaoEsquerda(raiz);

        if (balanceamento < -1 && fatorBalanceamento(raiz.dir) > 0) {
            raiz.dir = rotacaoDireita(raiz.dir);
            return rotacaoEsquerda(raiz);
        }

        return raiz;
    }

    // Método para imprimir a árvore em ordem
    public void imprimir(ArvoreAvlChar raiz) {
        if (raiz != null) {
            imprimir(raiz.esq);
            System.out.print(raiz.chave + " ");
            imprimir(raiz.dir);
        }
    }

    // Método para buscar um nó com uma determinada chave
    public ArvoreAvlChar buscar(ArvoreAvlChar raiz, String chave) {
        if (raiz == null || chave.equals(raiz.chave))
            return raiz;

        if (chave.compareTo(raiz.chave) < 0)
            return buscar(raiz.esq, chave);

        return buscar(raiz.dir, chave);
    }
}

