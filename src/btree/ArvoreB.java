package btree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class BNodo {
    List<Integer> chaves;
    List<BNodo> filhos;
    private boolean folha;

    public BNodo(boolean folha) {
        this.folha = folha;
        this.chaves = new ArrayList<>();
        this.filhos = new ArrayList<>();
    }

    public int getChave(int indice) {
        return chaves.get(indice);
    }

    public void setChave(int indice, int valor) {
        chaves.set(indice, valor);
    }

    public void adicionarChave(int indice, int valor) {
        chaves.add(indice, valor);
    }

    public void adicionarFilho(int indice, BNodo filho) {
        filhos.add(indice, filho);
    }

    public BNodo getFilho(int indice) {
        return filhos.get(indice);
    }

    public boolean ehFolha() {
        return folha;
    }

    public int tamanho() {
        return chaves.size();
    }
}

public class ArvoreB {
    private BNodo raiz;
    private int t;

    public ArvoreB(int t) {
        this.raiz = new BNodo(true);
        this.t = t;
    }

    public void inserir(int chave) {
        BNodo raiz = this.raiz;
        if (raiz.tamanho() == (2 * t) - 1) {
            BNodo novaRaiz = new BNodo(false);
            novaRaiz.adicionarFilho(0, this.raiz);
            dividirFilho(novaRaiz, 0);
            this.raiz = novaRaiz;
        }
        inserirNaoCheio(raiz, chave);
    }


    private void inserirNaoCheio(BNodo nodo, int chave) {
        while (true) {
            int i = nodo.tamanho() - 1;
            while (!nodo.ehFolha() && i >= 0 && chave < nodo.getChave(i)) {
                i--;
            }
            if (nodo.ehFolha()) {
                break;
            }
            BNodo filho = nodo.getFilho(i + 1);
            if (filho.tamanho() == (2 * t) - 1) {
                dividirFilho(nodo, i + 1);
                if (chave > nodo.getChave(i + 1)) {
                    i++;
                }
            }
            nodo = nodo.getFilho(i + 1);
        }
        int i = nodo.tamanho() - 1;
        while (i >= 0 && chave < nodo.getChave(i)) {
            i--;
        }
        nodo.adicionarChave(i + 1, chave);
    }

    private void dividirFilho(BNodo pai, int indice) {
        BNodo filho = pai.getFilho(indice);
        BNodo novoFilho = new BNodo(filho.ehFolha());
        pai.adicionarChave(indice, filho.getChave(t - 1));
        for (int j = 0; j < t - 1; j++) {
            novoFilho.adicionarChave(j, filho.getChave(j + t));
        }
        filho.chaves.subList(t - 1, filho.tamanho()).clear();
        if (!filho.ehFolha()) {
            for (int j = 0; j < t; j++) {
                novoFilho.adicionarFilho(j, filho.getFilho(j + t));
            }
            filho.filhos.subList(t, filho.tamanho() + 1).clear();
        }
        pai.adicionarFilho(indice + 1, novoFilho);
    }

    public void buscar(int chave) {
        buscarNodo(this.raiz, chave);
    }

    private void buscarNodo(BNodo nodo, int chave) {
        while (nodo != null) {
            int i = 0;
            while (i < nodo.tamanho() && chave > nodo.getChave(i)) {
                i++;
            }

            if (i < nodo.tamanho() && chave == nodo.getChave(i)) {
                System.out.println("Chave " + chave + " encontrada!");
                return;
            }
            
            if (nodo.ehFolha()) {
                break;
            }
            
            nodo = nodo.getFilho(i);
        }
        System.out.println("Chave " + chave + " não encontrada.");
    }

    public void remover(int chave) {
        removerNodo(raiz, chave);
        
        // Caso a raiz tenha se tornado vazia após a remoção
        if (raiz.tamanho() == 0 && !raiz.ehFolha()) {
            raiz = raiz.getFilho(0);
        }
    }

    private void removerNodo(BNodo nodo, int chave) {

        if (nodo.tamanho() == 0) {
            System.out.println("A árvore está vazia, não há nada para remover.");
            return;
        }

        while (true) {
            int i = 0;
            while (i < nodo.tamanho() && chave > nodo.getChave(i)) {
                i++;
            }

            if (i < nodo.tamanho() && chave == nodo.getChave(i)) {
                if (nodo.ehFolha()) {
                    // Caso 1: O nó é uma folha
                    nodo.chaves.remove(i);
                    System.out.println("Chave " + chave + " removida.");
                    return;
                } else {
                    // Caso 2: O nó é um nodo interno
                    BNodo predecessor = obterPredecessor(nodo, i);
                    int novaChave = predecessor.getChave(predecessor.tamanho() - 1);
                    nodo.setChave(i, novaChave);
                    chave = novaChave;
                    nodo = predecessor;
                }
            } else {
                // Caso 3: A chave não está presente no nodo
                BNodo filho = nodo.getFilho(i);
                if (filho.tamanho() < t) {
                    // Caso 3a: O filho tem menos chaves que o mínimo necessário
                    balancearFilho(nodo, i);
                }
                nodo = filho;
            }
        }
    }
        
    private BNodo obterPredecessor(BNodo nodo, int indice) {
        BNodo atual = nodo.getFilho(indice);
        while (!atual.ehFolha()) {
            atual = atual.getFilho(atual.tamanho());
        }
        return atual;
    }

    private void balancearFilho(BNodo pai, int indiceFilho) {
        BNodo filho = pai.getFilho(indiceFilho);
        BNodo esquerda = (indiceFilho > 0) ? pai.getFilho(indiceFilho - 1) : null;
        BNodo direita = (indiceFilho < pai.tamanho()) ? pai.getFilho(indiceFilho + 1) : null;

        if (esquerda != null && esquerda.tamanho() > t - 1) {
            // Caso 3b(i): Redistribuição de chaves com irmão à esquerda
            int chaveTransferida = esquerda.getChave(esquerda.tamanho() - 1);
            esquerda.setChave(esquerda.tamanho() - 1, filho.getChave(0));
            filho.setChave(0, chaveTransferida);
            filho.adicionarFilho(0, esquerda.getFilho(esquerda.tamanho()));
            esquerda.filhos.remove(esquerda.tamanho());
        } else if (direita != null && direita.tamanho() > t - 1) {
            // Caso 3b(ii): Redistribuição de chaves com irmão à direita
            int chaveTransferida = direita.getChave(0);
            direita.setChave(0, filho.getChave(0));
            filho.setChave(0, chaveTransferida);
            filho.adicionarFilho(filho.tamanho(), direita.getFilho(0));
            direita.filhos.remove(0);
        } else if (esquerda != null) {
            // Caso 3c(i): Fusão com irmão à esquerda
            esquerda.adicionarChave(esquerda.tamanho(), pai.getChave(indiceFilho - 1));
            esquerda.chaves.addAll(filho.chaves);
            esquerda.filhos.addAll(filho.filhos);
            pai.chaves.remove(indiceFilho - 1);
            pai.filhos.remove(indiceFilho);
        } else if (direita != null) {
            // Caso 3c(ii): Fusão com irmão à direita
            filho.adicionarChave(filho.tamanho(), pai.getChave(indiceFilho));
            filho.chaves.addAll(direita.chaves);
            filho.filhos.addAll(direita.filhos);
            pai.chaves.remove(indiceFilho);
            pai.filhos.remove(indiceFilho + 1);
        }
    }
    

    // Deque -> interface em Java que representa uma "double-ended queue", ou seja, 
    // uma fila onde você pode adicionar e remover elementos tanto nodo início quanto nodo final. 
    // Utilizado para manter uma pilha de nós a serem processados.
    // https://docs.oracle.com/javase/7/docs/api/java/util/Deque.html

    public void imprimir() {
        Deque<BNodo> pilha = new ArrayDeque<>();
        Deque<Integer> niveis = new ArrayDeque<>();
        pilha.push(raiz);
        niveis.push(0);

        while (!pilha.isEmpty()) {
            BNodo nodo = pilha.pop();
            int nivel = niveis.pop();

            System.out.println("Nível " + nivel + " " + nodo.tamanho() + ": " + nodo.chaves);

            if (!nodo.ehFolha()) {
                for (int i = nodo.tamanho(); i >= 0; i--) {
                    pilha.push(nodo.getFilho(i));
                    niveis.push(nivel + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        ArvoreB arvoreB = new ArvoreB(3);
        
        int[] chaves = {3, 18, 7, 11, 1, 5, 15, 9, 2, 12, 19, 21, 8, 4, 6, 13, 10, 21 };
        for (int chave : chaves) {
            arvoreB.inserir(chave);
        }
        arvoreB.imprimir();
        arvoreB.buscar(14);
        arvoreB.buscar(10);
        arvoreB.remover(10);
        arvoreB.buscar(10);
        arvoreB.inserir(14);
        arvoreB.buscar(14);
    }
    
}
