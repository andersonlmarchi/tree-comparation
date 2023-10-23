package binary;

import java.util.LinkedList;
import java.util.Queue;

import org.w3c.dom.Node;

public class ArvoreBinaria {

    private class Nodo {

        private int chave;
        private Nodo dir, esq;

        public Nodo(int item) {
            this.chave = item;
            this.dir = null;        
            this.esq = null;
        }

    }

    Nodo raiz = null;

    public void insert(int chave) {
        raiz = insertNew(raiz, chave);
    }

    public Nodo insertNew(Nodo raiz, int chave) {
 
        if (raiz == null) {
            raiz = new Nodo(chave);
            return raiz;
        }
        Queue<Nodo> fila = new LinkedList<Nodo>();
        fila.add(raiz);
 
        while (!fila.isEmpty()) {
            raiz = fila.peek();
            fila.remove();
 
            if (raiz.esq == null) {
                raiz.esq = new Nodo(chave);
                break;
            }
            else fila.add(raiz.esq);
 
            if (raiz.dir == null) {
                raiz.dir = new Nodo(chave);
                break;
            }
            else fila.add(raiz.dir);
        }
        return raiz;
    }

    public void inserirNovo(int chave) {
        raiz = inserirNovoNodo(raiz, chave);
    }

    private Nodo inserirNovoNodo(Nodo raiz, int chave) {
        if(raiz == null) {
            raiz = new Nodo(chave);
            return raiz;
        }

        Nodo aux = raiz;

        while (aux != null) {
            if (chave < aux.chave) {
                if (aux.esq == null) {
                    aux.esq = new Nodo(chave);
                    return aux;
                }
            } else {
                if (aux.dir == null) {
                    aux.dir = new Nodo(chave);
                    return aux;                    
                }
            }
        }

        return aux;
    }

    private Nodo inserirNodo(Nodo raiz, int chave) {
        if(raiz == null) {
            raiz = new Nodo(chave);
            return raiz;
        }

        if(chave < raiz.chave)
            raiz.esq = inserirNodo(raiz.esq, chave);
        else if (chave > raiz.chave)
            raiz.dir = inserirNodo(raiz.dir, chave);
        
        return raiz;

    }

    public void inserir(int chave) {
        raiz = inserirNodo(raiz, chave);
    }

    public void visualizar(Order order) {
        System.out.println("**********");
        visualizarOrdenado(raiz, order);
    }

    private void visualizarOrdenado(Nodo raiz, Order order) {
        if(raiz != null) {
            if(order == Order.ASC){
                visualizarOrdenado(raiz.esq, Order.ASC);
                System.out.println(raiz.chave);
                visualizarOrdenado(raiz.dir, Order.ASC);
            } else {
                visualizarOrdenado(raiz.dir, Order.DESC);
                System.out.println(raiz.chave);
                visualizarOrdenado(raiz.esq, Order.DESC);
            }
        }
    }

    // Mostrar somente os números pares
    public void visualizarPares() {
        System.out.println("**********");
        visualizarNumerosPares(raiz);
    }

    private void visualizarNumerosPares(Nodo raiz) {
        if (raiz != null) {
            visualizarNumerosPares(raiz.esq);
            if (raiz.chave % 2 == 0) {
                System.out.println(raiz.chave);
            }
            visualizarNumerosPares(raiz.dir);
        }
    }

    public void visualizarPorNivel(){
        if(raiz == null) {
            System.out.println("Empty binary tree!");
            return;
        }

        Queue<Nodo> queue = new LinkedList<>();
        queue.add(raiz);

        System.out.println("------------------");

        while(!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Nodo node = queue.poll();
                if(node != null) {
                    System.out.print(node.chave + " ");
                    queue.add(node.esq);
                    queue.add(node.dir);
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }        
        System.out.println("------------------");

    }

    public void remover(int chave) {
        raiz = removerNodo(raiz, chave);
    }

    private Nodo removerNodo(Nodo raiz, int chave) {
        Nodo p, p2;
		if (raiz.chave == chave){
			if (raiz.esq == raiz.dir){
				//o elemento a ser removido não tem filhos
				return null;
			}
			else if(raiz.esq == null){
				//o elemento a ser removido não tem filho à esquerda
				return raiz.dir;
			}
			else if (raiz.dir == null){
				//o elemento a ser removido não tem filho à direita
				return raiz.esq;
			}
			else{
				//o elemento a ser removido tem filhos em ambos os lados
				p2 = raiz.dir;
				p = raiz.dir;
				while (p.esq != null){
					p = p.esq;
				}
				p.esq = raiz.esq;
				return p2;
			}
		}
		else if (raiz.chave < chave){
			raiz.dir = removerNodo(raiz.dir,chave);
		}
		else{
			raiz.esq = removerNodo(raiz.esq,chave);
		}

		return raiz;
    }

    // a) Mostrar o maior número
    public int maiorNodo(Nodo raiz){
		while(raiz.dir != null){
			raiz = raiz.dir;
		}
		return raiz.chave;
	}	

    // b) Mostrar o menor número
	public int menorNodo(Nodo raiz){
		while(raiz.esq != null){
			raiz = raiz.esq;
		}
		return raiz.chave;
	}	

    // c) Mostrar os nós folhas
    public void mostrarFolhas(Nodo raiz){
		if (raiz != null){
			mostrarFolhas(raiz.esq);
			if (raiz.dir == null && raiz.esq == null){
				System.out.print(raiz.chave + " ");
			}
			mostrarFolhas(raiz.dir);
		}
	}

    public boolean consultar(Nodo raiz, int valor){
        if (raiz != null){
            if (raiz.chave == valor)
                return true;
            else if (valor < raiz.chave)
                return consultar(raiz.esq, valor);
            else
                return consultar(raiz.dir, valor);	
        }
        return false;
    }

    public Nodo buscar(int valor) {
        return buscarNodo(raiz, valor);
    }

    public Nodo buscarNodo(Nodo raiz, int valor) {
        if (raiz == null || raiz.chave == valor) {
            return raiz;
        }
    
        Nodo esquerda = buscarNodo(raiz.esq, valor);
        if (esquerda != null) {
            return esquerda;
        }
    
        Nodo direita = buscarNodo(raiz.dir, valor);
        return direita;
    }    

    // d) Mostrar os nós ancestrais de um nó
    public void mostrarAncestrais(Nodo raiz, int valor){
		if (raiz != null && raiz.chave != valor) {
            if (!consultar(raiz, valor))
                throw new Error("Valor não encontrado na árvore!");
                
			if (valor < raiz.chave){
				System.out.print(raiz.chave + " ");
				mostrarAncestrais(raiz.esq, valor);
			} else {
                System.out.print(raiz.chave + " ");
                mostrarAncestrais(raiz.dir, valor);	
            }
		}
	}

    public void descendentes(int valor) {
        mostrarDescendentes(raiz, valor);
    }

    // e) Mostrar os nós descendentes de um nó
    private void mostrarDescendentes(Nodo raiz, int valor) {
        if (raiz != null && raiz.chave != valor) {
            if (!consultar(raiz, valor))
                throw new Error("Valor não encontrado na árvore!");
            
            if (valor < raiz.chave){
                mostrarDescendentes(raiz.esq, valor);
            } else if (valor > raiz.chave) {
                mostrarDescendentes(raiz.dir, valor);	
            } else {
                visualizarOrdenado(raiz.esq, Order.ASC);
                visualizarOrdenado(raiz.dir, Order.ASC);
            }
        }
    }

    // k) Mostrar a altura da árvore
    public void altura(){
        System.out.println("Altura da árvore é: " + alturaArvore(raiz));
    }

    private int alturaArvore(Nodo raiz) {
        return (raiz == null) ? -1 : 1 + Math.max(alturaArvore(raiz.esq), alturaArvore(raiz.dir));
    }

    // j) Mostrar o nível de um nodo
    public void nivel(int chave) {
        System.out.println("Nível do nodo " + chave + " é: " + nivelNodo(raiz, chave, 1));        
    }

    private int nivelNodo(Nodo raiz, int valor, int nivel) {
        if (!consultar(raiz, valor))
            throw new Error("Valor não encontrado na árvore!");

        if (raiz == null)
            return 0;

        if (raiz.chave == valor)
            return nivel;
        
        if (valor < raiz.chave) {
            return nivelNodo(raiz.esq, valor, nivel + 1);
        } else {
            return nivelNodo(raiz.dir, valor, nivel + 1);
        }
        
    }

    // l) Mostra o tamanho da árvore
    public void tamanho() {
        System.out.println("O tamanho da árvore é: " + mostrarTamanho(raiz));
    }

    private int mostrarTamanho(Nodo raiz) {
        return (raiz == null) ? 0 : mostrarTamanho(raiz.esq) + 1 + mostrarTamanho(raiz.dir);
    }

    // f) Mostrar a subárvore da direita de um nó
    public void mostrarArvoreEsquerda(int valor) {
        visualizarOrdenado(buscarNodo(raiz, valor).esq, Order.ASC);
    }

    // g) Mostrar a subárvore da esquerda de um nó
    public void mostrarArvoreDireita(int valor) {
        visualizarOrdenado(buscarNodo(raiz, valor).dir, Order.ASC);        
    }

    // h) Transformar a árvore numa lista encadeada
    public void converteEmLista() {
        imprimeListaDuplamenteEncadeada(converteEmListaDuplamenteEncadeada(raiz));
    }

    private Nodo converteEmListaDuplamenteEncadeada(Nodo raiz) {
        if (raiz == null) {
            return null;
        }
    
        // Converte a sub árvore a esquerda
        Nodo esquerda = converteEmListaDuplamenteEncadeada(raiz.esq);
    
        // Obtem o predecessor (mais a direita do nodo na sub árvore à esquerda)
        Nodo predecessor = esquerda;
        while (predecessor != null && predecessor.dir != null) {
            predecessor = predecessor.dir;
        }
    
        // Connecta o predecessor no nodo atual
        if (predecessor != null) {
            predecessor.dir = raiz;
            raiz.esq = predecessor;
        }
    
        // Converte a sub árvore à direita
        Nodo direita = converteEmListaDuplamenteEncadeada(raiz.dir);
        if (direita != null) {
            direita.esq = raiz;
            raiz.dir = direita;
        }
    
        return esquerda != null ? esquerda : raiz;
    }
    
    private void imprimeListaDuplamenteEncadeada(Nodo raiz) {
        Nodo atual = raiz;
        System.out.println("-------------------");
        while (atual != null) {
            System.out.print(atual.chave + " ");
            atual = atual.dir;
        }
        System.out.println("\n-------------------");
    }
    
}
