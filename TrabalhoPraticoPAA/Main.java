/* @author Camila Hollerbach
 * @author Fabrizio Peragallo
 * @author Gabriel Pinto
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Main {
    // Criando Scanner para input do usuário
    public static Scanner sc = new Scanner(System.in);
    // Criando gerador de número randômicos caso seja escolhida opção de arquivo aleatório
    public static Random gerador = new Random();

    public static int LeituraTipoSolucao() {
        System.out.println("1 - Força Bruta");
        System.out.println("2 - Branch-and-Bound");
        System.out.print("Digite a opção: ");
        int op = sc.nextInt();
        return op;
    }

    public static int LeituraTipoArquivo() {
        System.out.println("1 - Arquivo pré definido");
        System.out.println("2 - Arquivo gerado aleatoriamente");
        System.out.print("Digite a opção: ");
        int op = sc.nextInt();
        return op;
    }

    public static String[] LeituraArquivo(String nome) {
        // String str armazenará todo conteúdo do arquivo
        String str = "";
        try {
            // Leitura do arquivo
            BufferedReader arq = new BufferedReader(new FileReader("franquias.txt"));
            while (arq.ready()) {
                str += arq.readLine();
                str += '\n';
                // System.out.println(str);
            }
            arq.close();
        } catch (Exception e) {
            System.err.println("Erro ao abrir arquivo!");
        }
        // Retorna um vetor de String, onde cada posição é uma linha do arquivo
        return str.split("\n");
    }

    public static String[] LeituraArquivo() {
        // String que será o arquivo
        String str = "";
        // Pegar o número de franquias
        System.out.print("Digite o número de franquias (entre 4 e 20): ");
        int n = sc.nextInt();
        // Gerando 2*n linhas no arquivo de forma aleatória
        for (int i = 1; i <= n; i++) {
            for (int m = 0; m < 2; m++) {
                str += i;
                str += " ";
                int X = (int) (gerador.nextInt(501));
                str += X;
                str += " ";
                int Y = (int) (gerador.nextInt(501));
                str += Y;
                str += " ";
                int custo = (int) (gerador.nextInt(2001));
                str += custo;
                // Se não for a última linha associada a uma franquia (i != n) ou não for a última linha em geral (m != 1)
                if (i != n || m != 1) {
                     // Adiciona caractere de nova linha ('Enter')
                    str += '\n';
                }
            }
        }
        // Salvar arquivo aleatório
        try {
            BufferedWriter arq = new BufferedWriter(new FileWriter("franquiasAleatorio.txt"));
            arq.write(str);
            arq.close();
        } catch (Exception e) {
            // Nunca vai entrar
        }
        // Mandando arquivo em String para main
        return str.split("\n");
    }

    public static int LeituraDistancia() {
        // Leitura da entrada dada pelo usuário
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a distância mínima entre cada franquia: ");
        int dist = sc.nextInt();
        sc.close();
        // Retorna entrada do usuário
        return dist;
    }

    public static int CalculaCustoSolucao(String[] solucao) {
        // Inicializa variável do custo
        int custo = 0;
        // Percorre todas lojas da solução
        for (String loja : solucao) {
            // Se loja estiver vazia, quebra loop
            if (loja == "") {
                break;
            }
            // Adiciona custo da loja atual
            custo += Integer.parseInt(loja.split(" ")[3]);
        }
        // Retorna o custo
        return custo;
    }

    public static void main(String[] args) {
        // Tipo da solução
        int tipoSolucao = LeituraTipoSolucao();
        // Tipo de leitura de arquivo
        int tipoArquivo = LeituraTipoArquivo();
        // Declarando vetor de Strings que receberá o arquivo
        String[] arquivo;
        // Declarando distância mínima entre 2 lojas
        int D;
        if (tipoArquivo == 1) {
            // Lendo arquivo em memória
            arquivo = LeituraArquivo("franquias.txt");
            // Lendo distância mínima das franquias pelo usuário (input)
            D = LeituraDistancia();
        } else if (tipoArquivo == 2) { 
            // Gerando arquivo aleatório
            arquivo = LeituraArquivo();
            // Setando distância mínima como 1
            D = 1;
        } else { // Opção inválida digitada (não foi 1 nem 2)
            System.err.println("Opção inválida!");
            arquivo = null;
            D = 0;
        }
        // Declarando variável que receberá a solução (inicializa com o tamanho N franquias)
        String solucao[] = new String[Integer.parseInt(arquivo[arquivo.length - 1].split(" ")[0])];
        // Inicializar cronômetro do tempo de execução do código
        long inicio = System.currentTimeMillis();
        if (tipoSolucao == 1) {
            // Caso usuário escolha pela força bruta
            solucao = ForcaBruta.SolucaoForcaBruta(arquivo, D);
        } else if (tipoSolucao == 2) {
            // Caso usuário escolha pelo Branch-And-Bound
            solucao = BranchAndBound.SolucaoBranchAndBound(arquivo, D);
        } else {
            // Caso usuário digite opção inexistente
            System.err.println("Opção inválida!");
        }
        // Imprime tempo para encontrar solução em mili segundos
        System.out.println("Tempo para encontrar solução: " + ((System.currentTimeMillis() - inicio)) + " ms");

        // Cria uma lista com todas as entradas advindas da solução
        List<MapEntry> listaSolucao = new ArrayList<>();
        // Popula a lista com os resultados vindos da solução (Força Bruta ou Branch & Bound)
        for (String loja : solucao) {
            // Quebra o 'for' caso chegue ao fim da solução
            if (loja.equals("")) {
                break;
            }
            // Adiciona loja da solução na lista
            listaSolucao.add(new MapEntry(Integer.parseInt(loja.split(" ")[0]), Integer.parseInt(loja.split(" ")[1]), Integer.parseInt(loja.split(" ")[2])));
        }
        // Cria uma janela
        JFrame frame = new JFrame("Mapa Visual");
        VisualizadorMapa mapaVisual = new VisualizadorMapa(listaSolucao, 500, 500);
        // Adiciona o mapa visual a janela
        frame.add(mapaVisual);
        // Define a resolução da janela
        frame.setSize(520, 545);
        // Sai ao fechar a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Mostra a janela
        frame.setVisible(true);

        // Imprime valor da solução final
        System.out.println("Custo final: " +CalculaCustoSolucao(solucao));
        // Imprime as lojas selecionadas na solução final
        System.out.println("Lojas selecionadas na solução final:");
        for (String loja : solucao) {
            // Se a loja não estiver vazia
            if (loja.equals("")) {
                // Se encontrar uma posição vazia, interrompe o loop
                break;
            } else {
                // Imprime as informações da loja
                System.out.println(loja);
            }
        }
    }
}
