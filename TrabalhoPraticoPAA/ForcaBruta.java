class ForcaBruta {

    public static boolean NaoTemFranquiaRepetida (String lojaAtual, String[] solucaoAtual) {
        // Pega número da franquia da loja atual
        int franquiaLojaAtual = Integer.parseInt(lojaAtual.split(" ")[0]);
        for (String loja : solucaoAtual) {
            // Se posição da solução estiver vazia
            if (loja.equals("")) {
                // Não há mais lojas a serem analidas, vetor solução ainda está incompleto
                break;
            }
            // Pega número da franquia de uma loja da solução
            int franquia = Integer.parseInt(loja.split(" ")[0]);
            // Compara se a franquia da loja atual existe na solução
            if (franquia == franquiaLojaAtual) {
                // Retorna falso pois há franquia repetida
                return false;
            }
        }
        // Retorna verdadeiro se não há franquia repetida a ser inserida na solução
        return true;
    }

    public static boolean RespeitaDistanciaMinima (String lojaAtual, String[] solucaoAtual, int distanciaMinima) {
        // Pega X da loja atual
        int lojaAtualX = Integer.parseInt(lojaAtual.split(" ")[1]);
        // Pega Y da loja atual
        int lojaAtualY = Integer.parseInt(lojaAtual.split(" ")[2]);
        for (String loja : solucaoAtual) {
            // Se posição da solução estiver vazia
            if (loja.equals("")) {
                // Não há mais lojas a serem analidas, vetor solução ainda está incompleto
                break;
            }
            // Pega X da loja no indíce atual da solução (solução tem várias lojas)
            int lojaComparadaX = Integer.parseInt(loja.split(" ")[1]);
            // Pega X da loja no indíce atual da solução (solução tem várias lojas)
            int lojaComparadaY = Integer.parseInt(loja.split(" ")[2]);
            // Checa se a distância entre esses 2 lojas é válida
            if (DistanciaEntreLojas(lojaAtualX, lojaAtualY, lojaComparadaX, lojaComparadaY) < distanciaMinima) {
                // Se não for válida, retorna falso
                return false;
            }
        }
        // Se distância é válida, retorna true
        return true;
    }

    public static int DistanciaEntreLojas (int x1, int y1, int x2, int y2) {
        // Calcula distância entre os eixos X e Y entre cada cidade (operador ternário para evitar distância negativa)
        int distX = (x1>x2) ? x1 - x2 : x2 - x1;
        int distY = (y1>y2) ? y1 - y2 : y2 - y1;
        // Retorna distância heurística entre as cidades (será precisa pois o padrão será seguido para todas franquias)
        return (distX + distY);
    }

    public static void InserirNaSolucaoAtual (String lojaAtual, String[] solucaoAtual) {
        // Percorre solução atual procurando um espaço vazio
        for (int i=0; i < solucaoAtual.length; i++) {
            // Se encontrar espaço vazio
            if (solucaoAtual[i].equals("")) {
                // Preenche espaço vazio
                solucaoAtual[i] = lojaAtual;
                break;
            }
        }
    }

    public static void RemoverLojaAtual (String lojaAtual, String[] solucaoAtual) {
        // Percorre solução atual procurando elemento a ser removido
        for(int i=0; i<solucaoAtual.length; i++) {
            // Checa se é o elemento a ser removido
            if (solucaoAtual[i].equals(lojaAtual)) {
                // Remove elemento
                solucaoAtual[i] = "";
                // Quebra loop
                break;
            }
        }
    }

    public static int CalculaCusto (String loja) {
        // Calcula custo da loja
        int custo = Integer.parseInt(loja.split(" ")[3]);
        // Retorna esse custo
        return custo;
    }

    public static int CalculaTamanhoSolucao (String[] solucao) {
        // Variável que guarda tamanho da solução
        int tamanhoSolucao = 0;
        // Calcula tamanho da solução
        for(String loja : solucao) {
            // Se posição da solução for vazia
            if (loja.equals("")) {
                // Quebra o FOR (não há mais lojas)
                break;
            }
            // Incrementa contador
            tamanhoSolucao++;
        }
        // Retorna tamanho da solução
        return tamanhoSolucao;
    }

    public static boolean AvaliaSolucaoAtual (String[] solucaoAtual, String[] solucaoFinal) {
        // Testar se não existe alguma solução final (caso base é custo 99999)
        if (CalculaCusto(solucaoFinal[0]) == 99999) {
            // Retorna true, solução atual será a melhor por ser a primeira
            return true;
        }
        // Cria variáveis para armazenar o tamanho de cada solução
        int tamanhoSolucaoAtual=CalculaTamanhoSolucao(solucaoAtual);
        int tamanhoSolucaoFinal=CalculaTamanhoSolucao(solucaoFinal);
        // Se tamanho da solução atual for maior que o da solução final, solução atual é melhor
        if (tamanhoSolucaoAtual > tamanhoSolucaoFinal) {
            // Retorna true
            return true;
        }
        // Se tamanho da solução final for maior que o da solução atual, solução final é melhor
        if (tamanhoSolucaoFinal > tamanhoSolucaoAtual) {
            // Retorna false
            return false;
        }
        // Se as 2 soluções tiverem tamanho igual, calcularemos o custo de cada uma
        int custoSolucaoAtual=0, custoSolucaoFinal=0;
        // Calcula custo das 2 soluções
        for (int i=0; i<tamanhoSolucaoAtual; i++) {
            custoSolucaoAtual += CalculaCusto(solucaoAtual[i]);
            custoSolucaoFinal += CalculaCusto(solucaoFinal[i]);
        }
        // Se solução atual tiver menor custo, ela é melhor
        if (custoSolucaoAtual < custoSolucaoFinal) {
            return true;
        } 
        // Senão, a solução final é melhor ou tão boa quanto a solução atual 
        else { 
            return false;
        }
    }

    public static void AtualizaSolucao (String[] solucaoAtual, String[] solucaoFinal) {
        // Cria variável para armazenar o tamanho da solução
        int tamanhoSolucaoAtual=CalculaTamanhoSolucao(solucaoAtual);
        // Atualiza solução final
        for (int i=0; i<tamanhoSolucaoAtual; i++) {
            solucaoFinal[i] = solucaoAtual[i];
        }
    }

    public static String[] SolucaoForcaBruta (String[] arquivo, int distanciaMinima) {
        // Pega o total de franquias (arquivo sempre vai estar ordenado em ordem crescente inicialmente)
        int n = Integer.parseInt(arquivo[arquivo.length-1].split(" ")[0]);
        // Armazena a solução de momento na recursão
        String[] solucaoAtual = new String[n];
        for (int i=0; i<n; i++) {
            // Inicializa todas posições dela como 'vazio'
            solucaoAtual[i] = "";
        }
        // Salva a melhor solução até o momento
        String[] solucaoFinal = new String[n];
        // Inicializa solução final como custo altíssimo
        solucaoFinal[0] = "0 0 0 99999";
        for (int i=1; i<n; i++) {
            // Inicializa as outras posições como 'vazio'
            solucaoFinal[i] = "";
        }
        // Indica a linha atual do arquivo
        int contador = 0;
        // Calcula a melhor solução 
        CalculaSolucaoForcaBruta(arquivo, distanciaMinima, solucaoAtual, contador, solucaoFinal);
        // Retorna solução final para o main
        return solucaoFinal;
    }

    public static void CalculaSolucaoForcaBruta (String[] arquivo, int distanciaMinima, String[] solucaoAtual, int contador, String[] solucaoFinal) {
        if (contador == arquivo.length || CalculaTamanhoSolucao(solucaoAtual) == solucaoAtual.length) {
            if (AvaliaSolucaoAtual(solucaoAtual, solucaoFinal)) {
                AtualizaSolucao(solucaoAtual, solucaoFinal);
            }
        }

        else if (NaoTemFranquiaRepetida(arquivo[contador], solucaoAtual) && 
            RespeitaDistanciaMinima(arquivo[contador], solucaoAtual, distanciaMinima)) 
        {
            InserirNaSolucaoAtual(arquivo[contador], solucaoAtual);
            CalculaSolucaoForcaBruta(arquivo, distanciaMinima, solucaoAtual, (contador+1), solucaoFinal);
            RemoverLojaAtual(arquivo[contador], solucaoAtual);
            CalculaSolucaoForcaBruta(arquivo, distanciaMinima, solucaoAtual, (contador+1), solucaoFinal);
        } 
        else {
            CalculaSolucaoForcaBruta(arquivo, distanciaMinima, solucaoAtual, (contador+1), solucaoFinal);
        }
    }
}