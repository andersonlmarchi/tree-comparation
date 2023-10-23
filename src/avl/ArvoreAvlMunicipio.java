package avl;

// 8) Monte uma arvore capaz de armazenar as seguintes informações: nome do município, área total do município (em km2) e população. A chave do nó para inserção deverá ser o nome do município. Implemente também as funções abaixo:
//     a. Contar o número de municípios, percorrendo os nós cadastrados na árvore.
//     b. Mostrar apenas os nomes dos municípios com mais de X habitantes. Por exemplo, X
//     pode ser 100.000 pessoas.
//     c. Mostrar a densidade demográfica de cada cidade. A densidade demográfica é a
//     relação entre a população e a área.
//     d. Mostrar o somatório de área em km2 de todas as cidades juntas em relação ao
//     território nacional (em porcentagem).
//     e. Mostrar o nome da cidade com a maior população.

class ArvoreAvlMunicipio {
    String nome;
    double areaTotal;
    int populacao;

    public ArvoreAvlMunicipio(String nome, double areaTotal, int populacao) {
        this.nome = nome;
        this.areaTotal = areaTotal;
        this.populacao = populacao;
    }

    public double getDensidadeDemografica() {
        return populacao / areaTotal;
    }

    public static void main(String[] args) {
        ArvoreMunicipios arvore = new ArvoreMunicipios();

        // Inserir municípios
        arvore.inserir(new ArvoreAvlMunicipio("Brasília", 5802.17, 3100000));
        arvore.inserir(new ArvoreAvlMunicipio("Rio de Janeiro", 1182.3, 6730000));
        arvore.inserir(new ArvoreAvlMunicipio("São Paulo", 1521.11, 12300000));
        arvore.inserir(new ArvoreAvlMunicipio("Belo Horizonte", 331.401, 2500000));
        arvore.inserir(new ArvoreAvlMunicipio("Salvador", 693.697, 2900000));
        arvore.inserir(new ArvoreAvlMunicipio("Porto Alegre", 496.827, 1480000));
        arvore.inserir(new ArvoreAvlMunicipio("Recife", 217.494, 1660000));
        arvore.inserir(new ArvoreAvlMunicipio("Fortaleza", 314.930, 2700000));
        arvore.inserir(new ArvoreAvlMunicipio("Manaus", 11401.06, 2250000));
        arvore.inserir(new ArvoreAvlMunicipio("Belém", 1054.84, 1480000));
        arvore.inserir(new ArvoreAvlMunicipio("Curitiba", 435.036, 1940000));
        arvore.inserir(new ArvoreAvlMunicipio("Goiânia", 739.492, 1530000));
        arvore.inserir(new ArvoreAvlMunicipio("Natal", 167.263, 890000));
        arvore.inserir(new ArvoreAvlMunicipio("Florianópolis", 675.409, 508000));
        arvore.inserir(new ArvoreAvlMunicipio("Teresina", 136.882, 864000));
        arvore.inserir(new ArvoreAvlMunicipio("Campo Grande", 809.593, 895000));
        arvore.inserir(new ArvoreAvlMunicipio("João Pessoa", 210.538, 811000));
        arvore.inserir(new ArvoreAvlMunicipio("Aracaju", 181.898, 657000));
        arvore.inserir(new ArvoreAvlMunicipio("Palmas", 2218.95, 306000));
        arvore.inserir(new ArvoreAvlMunicipio("Boa Vista", 5688.99, 399000));
        arvore.inserir(new ArvoreAvlMunicipio("Rio Branco", 8833.16, 413000));
        arvore.inserir(new ArvoreAvlMunicipio("Macapá", 6508.36, 503000));

        // Contar o número de municípios
        int totalMunicipios = arvore.contarMunicipios(arvore.raiz);
        System.out.println("Total de Municípios: " + totalMunicipios);
        System.out.println("---------------------------------------------------------");

        // Mostrar municípios com mais de X habitantes
        int x = 2000000;
        System.out.println("Municípios com mais de " + x + " habitantes:");
        arvore.mostrarMunicipiosComMaisDeXHabitantes(arvore.raiz, x);
        System.out.println("---------------------------------------------------------");

        // Mostrar densidade demográfica
        System.out.println("Densidade Demográfica:");
        arvore.mostrarDensidadeDemografica(arvore.raiz);
        System.out.println("---------------------------------------------------------");

        // Calcular porcentagem de área total em relação ao território nacional
        double areaTotalNacional = 8515767; // Exemplo fictício
        double porcentagemAreaTotal = arvore.calcularPorcentagemAreaTotal(arvore.raiz, areaTotalNacional);
        System.out.println("Porcentagem de Área Total: " + porcentagemAreaTotal + "%");
        System.out.println("---------------------------------------------------------");

        // Encontrar município com a maior população
        String municipioMaiorPopulacao = arvore.encontrarMunicipioComMaiorPopulacao(arvore.raiz);
        System.out.println("Município com a Maior População: " + municipioMaiorPopulacao);
        System.out.println("---------------------------------------------------------");
    }
}

class Arvore {
    ArvoreAvlMunicipio municipio;
    Arvore esq;
    Arvore dir;

    public Arvore(ArvoreAvlMunicipio municipio) {
        this.municipio = municipio;
        this.esq = null;
        this.dir = null;
    }
}

class ArvoreMunicipios {
    Arvore raiz;

    public void inserir(ArvoreAvlMunicipio municipio) {
        raiz = inserirRec(raiz, municipio);
    }

    private Arvore inserirRec(Arvore raiz, ArvoreAvlMunicipio municipio) {
        if (raiz == null) {
            return new Arvore(municipio);
        }

        if (municipio.nome.compareTo(raiz.municipio.nome) < 0) {
            raiz.esq = inserirRec(raiz.esq, municipio);
        } else if (municipio.nome.compareTo(raiz.municipio.nome) > 0) {
            raiz.dir = inserirRec(raiz.dir, municipio);
        }

        return raiz;
    }

    public int contarMunicipios(Arvore raiz) {
        if (raiz == null) {
            return 0;
        }

        return 1 + contarMunicipios(raiz.esq) + contarMunicipios(raiz.dir);
    }

    public void mostrarMunicipiosComMaisDeXHabitantes(Arvore raiz, int x) {
        if (raiz == null) {
            return;
        }

        if (raiz.municipio.populacao > x) {
            System.out.println(raiz.municipio.nome);
        }

        mostrarMunicipiosComMaisDeXHabitantes(raiz.esq, x);
        mostrarMunicipiosComMaisDeXHabitantes(raiz.dir, x);
    }

    public void mostrarDensidadeDemografica(Arvore raiz) {
        if (raiz == null) {
            return;
        }

        System.out.println(raiz.municipio.nome + ": " + raiz.municipio.getDensidadeDemografica());

        mostrarDensidadeDemografica(raiz.esq);
        mostrarDensidadeDemografica(raiz.dir);
    }

    public double calcularPorcentagemAreaTotal(Arvore raiz, double areaTotalNacional) {
        if (raiz == null) {
            return 0;
        }

        return (raiz.municipio.areaTotal / areaTotalNacional) * 100
                + calcularPorcentagemAreaTotal(raiz.esq, areaTotalNacional)
                + calcularPorcentagemAreaTotal(raiz.dir, areaTotalNacional);
    }

    public String encontrarMunicipioComMaiorPopulacao(Arvore raiz) {
        if (raiz == null) {
            return null;
        }

        Arvore raizAtual = raiz;
        while (raizAtual.dir != null) {
            raizAtual = raizAtual.dir;
        }

        return raizAtual.municipio.nome;
    }
}
