import java.util.Random;
import btree.ArvoreB;
import binary.ArvoreBinaria;
import avl.ArvoreAvl;

public class Teste {

    // Variáveis de instância para as árvores
    private static ArvoreB arvoreB;
    private static ArvoreBinaria arvoreBinaria;
    private static ArvoreAvl arvoreAVL;

    private static int N_ARIA = 5;

    public static void main(String[] args) {

        // Inicialização das árvores
        arvoreB = new ArvoreB(N_ARIA);
        arvoreBinaria = new ArvoreBinaria();
        arvoreAVL = new ArvoreAvl();

        int[] dataset1 = gerarConjuntoDados(100000);
        int[] dataset2 = gerarConjuntoDados(1000000);

        // Teste com dados inseridos de forma ordenada
        testarInsercaoOrdenada(dataset1, "Conjunto de 100000 dados ordenados");
        testarInsercaoOrdenada(dataset2, "Conjunto de 1000000 dados ordenados");

        // Limpar árvores entre os testes para garantir resultados independentes
        limparArvores();

        // Teste com dados inseridos de forma aleatória
        testarInsercaoAleatoria(dataset1, "Conjunto de 100000 dados aleatórios");
        testarInsercaoAleatoria(dataset2, "Conjunto de 1000000 dados aleatórios");

        // Comparativo de busca por chave maior que a maior presente
        testarBuscaMaiorQueMaiorChave(dataset1, "Conjunto de 100000 dados aleatórios");
        testarBuscaMaiorQueMaiorChave(dataset2, "Conjunto de 1000000 dados aleatórios");
    }

    public static void testarInsercaoOrdenada(int[] dataset, String descricao) {
        System.out.println("---- Testando inserção ordenada (" + descricao + ") ----");

        long tempoInicial, tempoFinal;
        
        // Inserção na árvore B
        tempoInicial = System.nanoTime();
        for (int chave : dataset) {
            arvoreB.inserir(chave);
        }
        tempoFinal = System.nanoTime();
        System.out.println("Tempo de inserção na árvore B: " + (tempoFinal - tempoInicial) + " ns");

        // Inserção na árvore binária
        tempoInicial = System.nanoTime();
        for (int chave : dataset) {
            // arvoreBinaria.inserirNovo(chave);
            arvoreBinaria.insert(chave);
        }
        tempoFinal = System.nanoTime();
        System.out.println("Tempo de inserção na árvore binária: " + (tempoFinal - tempoInicial) + " ns");

        // Inserção na árvore AVL
        tempoInicial = System.nanoTime();
        for (int chave : dataset) {
            arvoreAVL.inserir(chave);
        }
        tempoFinal = System.nanoTime();
        System.out.println("Tempo de inserção na árvore AVL: " + (tempoFinal - tempoInicial) + " ns");

        System.out.println();
    }

    public static void testarInsercaoAleatoria(int[] dataset, String descricao) {
        System.out.println("---- Testando inserção aleatória (" + descricao + ") ----");

        long tempoInicial, tempoFinal;
        Random random = new Random();

        // Embaralhar os dados
        for (int i = dataset.length - 1; i > 0; i--) {
            int indice = random.nextInt(i + 1);
            int temp = dataset[indice];
            dataset[indice] = dataset[i];
            dataset[i] = temp;
        }

        // Inserção na árvore B
        tempoInicial = System.nanoTime();
        for (int chave : dataset) {
            arvoreB.inserir(chave);
        }
        tempoFinal = System.nanoTime();
        System.out.println("Tempo de inserção na árvore B: " + (tempoFinal - tempoInicial) + " ns");

        // Inserção na árvore binária
        tempoInicial = System.nanoTime();
        for (int chave : dataset) {
            arvoreBinaria.insert(chave);
        }
        tempoFinal = System.nanoTime();
        System.out.println("Tempo de inserção na árvore binária: " + (tempoFinal - tempoInicial) + " ns");

        // Inserção na árvore AVL
        tempoInicial = System.nanoTime();
        for (int chave : dataset) {
            arvoreAVL.inserir(chave);
        }
        tempoFinal = System.nanoTime();
        System.out.println("Tempo de inserção na árvore AVL: " + (tempoFinal - tempoInicial) + " ns");

        System.out.println();
    }

    public static void testarBuscaMaiorQueMaiorChave(int[] dataset, String descricao) {
        System.out.println("---- Testando busca por chave maior que a maior valor presente (" + descricao + ") ----");

        Random random = new Random();
        int chaveMaiorQueMaiorChave = dataset[dataset.length - 1] + random.nextInt(1000);

        // Busca na árvore B
        for (int chave : dataset) {
            arvoreB.inserir(chave);
        }
        long tempoInicial = System.nanoTime();
        arvoreB.buscar(chaveMaiorQueMaiorChave);
        long tempoFinal = System.nanoTime();
        System.out.println("Tempo de busca na árvore B: " + (tempoFinal - tempoInicial) + " ns");

        // Busca na árvore binária
        for (int chave : dataset) {
            arvoreBinaria.inserir(chave);
        }
        tempoInicial = System.nanoTime();
        arvoreBinaria.buscar(chaveMaiorQueMaiorChave);
        tempoFinal = System.nanoTime();
        System.out.println("Tempo de busca na árvore binária: " + (tempoFinal - tempoInicial) + " ns");

        // Busca na árvore AVL
        for (int chave : dataset) {
            arvoreAVL.inserir(chave);
        }
        tempoInicial = System.nanoTime();
        arvoreAVL.buscar(chaveMaiorQueMaiorChave);
        tempoFinal = System.nanoTime();
        System.out.println("Tempo de busca na árvore AVL: " + (tempoFinal - tempoInicial) + " ns");

        System.out.println();
    }

    public static int[] gerarConjuntoDados(int tamanho) {
        int[] dataset = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dataset[i] = i + 1;
        }
        return dataset;
    }

    public static void limparArvores() {
        arvoreB = new ArvoreB(N_ARIA);
        arvoreBinaria = new ArvoreBinaria();
        arvoreAVL = new ArvoreAvl();        
    }
}
