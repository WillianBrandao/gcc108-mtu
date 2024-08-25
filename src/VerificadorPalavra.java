import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerificadorPalavra {

    private DadosMT dadosMT; // MT completa
    private String palavraEntrada; // palavra de entrada
    private boolean continuarLoop;
    private String palavraAtual;
    private String palavraEsq; // Seleciona parte esquerda da fita
    private String palavraDir; // Selecionar parte direita da fita
    private List<String> passos; // Armazena os passos
    private String estadoAtual;
    private char simboloBranco;

    /**
     * Construtor
     * Chama os metodos para verificar se a palavra passada é aceita para a MT
     * passada
     */
    VerificadorPalavra(String palavraEntrada, String nomeArquivo) throws IOException {
        this.continuarLoop = true; // atributo para permitir continuidade do loop até identificar que a palavra
                                   // terminou
        armazenarDadosMT(palavraEntrada, nomeArquivo); // Salva os dados da MT
        this.simboloBranco = dadosMT.getSimboloBranco();// Armazena o simbolo branco da MT nos atributos para
                                                        // verificacao
        this.palavraEntrada = palavraEntrada;// Armazena a palavra de entrada nos atributos
        this.estadoAtual = dadosMT.getEstadoInicial(); // Armazena o estado inicial da MT nos atributos
        this.palavraEsq = ""; // Inicia a palavra esquerda como vazia
        this.palavraDir = simboloBranco + palavraEntrada + simboloBranco; // Adequa a palavra no formato inicial
        this.palavraAtual = palavraComEstado(); // Inicia situacao da fita
        passos = new ArrayList<>();// Instancia passos para armazenar os passos

        while (continuarLoop) {
            // IMPRIME A PALAVRA COM ESTADOS
            System.out.println(palavraComEstado());
            // SALVA OS PASSOS EM UMA LISTA
            passos.add(palavraComEstado());
            realizaTransicao();
        }

        if (aceitaPalavra()) {
            System.out.println("ACEITA");
        }

    }

    /**
     * Metodo para Instanciar todas as informacoes da MT
     * 
     * @param palavraEntrada Palavra a ser verificada
     * @param nomeArquivo    Nome do arquivo Txt com os dados da MT
     * @throws IOException
     */

    private void armazenarDadosMT(String palavraEntrada, String nomeArquivo) throws IOException {
        LeitorMaquina maquinaTuring = new LeitorMaquina(); // Inicializa Leitor da MT
        this.dadosMT = maquinaTuring.lerDados(nomeArquivo); // Le os dados do arquivo passado por parametro
    }

    /**
     * Metodo para retornar os dados da MT
     * 
     * @return Dados da MT
     */
    public DadosMT getDadosMT() {
        return dadosMT;
    }

    /**
     * MEtodo para escrever a palavra do tipo {q0}BaaabbbB
     * 
     * @return retorna a palavra manterndo o padrao {q0}BaaabbbB
     */
    public String palavraComEstado() {
        return palavraEsq + "{" + estadoAtual + "}" + palavraDir;
    }

    public String getPalavraAtual() {
        return palavraAtual;
    }

    /**
     * Método para identificar se existe uma transicao no ponto onde está a cabeça
     * de leitura na fita
     * 
     * @return String do tipo "Novo estado", "o que será escrito", "deslocamento na
     *         fita" Exemplo q3,Y,E
     */

    public String identificaTransicao() {
        char proximoElemento;
        if (palavraDir.length() == 0) {
            proximoElemento = simboloBranco;

        } else {
            proximoElemento = palavraDir.charAt(0);
        }
        String chave = estadoAtual + "," + proximoElemento;
        String transicaoResultado = dadosMT.retornaTransicao(chave);
        return transicaoResultado;
    }

    /**
     * Método que realiza a Transicao na fita, chamando os metodos responsaveis
     * identificaTransicao e movimentaFita
     */
    public void realizaTransicao() {
        String transicao = identificaTransicao();
        if (!transicaoValida(identificaTransicao())) {
            if (!aceitaPalavra()) {
                System.out.println("REJEITA");
            }

        } else {

            String[] resultadoTransicao = transicao.split(",");
            estadoAtual = resultadoTransicao[0];
            movimentaFita(resultadoTransicao[1], resultadoTransicao[2]);
        }

    }

    /**
     * Realiza a modificacao da fita de acordo com a movimentacao da cabeça de
     * leitura.
     * Exempplo (q3,Y,E)
     * 
     * @param elementoParaEscritaNaFita Elemento a ser escrito na fita, no exemplo
     *                                  acima seria o Y
     * @param deslocamento              Elemento para deslocamento, sendo E ou D
     */

    private void movimentaFita(String elementoParaEscritaNaFita, String deslocamento) {
        if (deslocamento.equals("D")) {
            deslocaDireitaEscreve(elementoParaEscritaNaFita);
        } else if (deslocamento.equals("E")) {
            deslocaEsquerdaEscreve(elementoParaEscritaNaFita);
        }

    }

    /**
     * Metodo usado para escrita na fita quando o elemento de uma transição
     * movimenta fita para esquerda
     * 
     * @param elementoParaEscritaNaFita Elemento que será escrito na fita vindo de
     *                                  uma transicao. Exemplo (q2,b)->(q3,Y,E).
     *                                  O Y é o elemento que deve ser escrito
     */

    private void deslocaEsquerdaEscreve(String elementoParaEscritaNaFita) {
        int tamanhoPalavraEsq = palavraEsq.length();
        char ultimoElemento;
        if (tamanhoPalavraEsq == 0) {
            palavraEsq = "";
            ultimoElemento = simboloBranco;
            // Continua inserindo Branco na String a esquerda para representar vazio

        } else {
            ultimoElemento = palavraEsq.charAt(tamanhoPalavraEsq - 1);
            palavraEsq = palavraEsq.substring(0, tamanhoPalavraEsq - 1);
        }

        palavraDir = ultimoElemento + elementoParaEscritaNaFita
                + palavraDir.substring(1, palavraDir.length());

    }

    /**
     * Metodo usado para escrita na fita quando o elemento de uma transição
     * movimenta fita para direita
     * 
     * @param elementoParaEscritaNaFita Elemento que será escrito na fita vindo de
     *                                  uma transicao. Exemplo(q0,B)->(q1,B,D).
     *                                  O B é o elemento que deve ser escrito
     * 
     */

    private void deslocaDireitaEscreve(String elementoParaEscritaNaFita) {
        int tamanhoPalavraDir = palavraDir.length();
        if (tamanhoPalavraDir == 0) {
            palavraDir += simboloBranco; // Continua inserindo Branco na String a direita para representar vazio

        } else {
            palavraDir = palavraDir.substring(1, tamanhoPalavraDir);
        }
        palavraEsq = palavraEsq + elementoParaEscritaNaFita;

    }

    /**
     * Metodo para identificar se o estado em que a fita esta é um estado final ou
     * não é um estado de rejeicao.
     * 
     * @return True se o estado em que a fita se encontra for final na MT analisada
     *         e false caso contrário
     */
    private boolean aceitaPalavra() {
        if (dadosMT.ehEstadoRejeicao(estadoAtual)) {
            return false;

        }

        return dadosMT.ehEstadoFinal(estadoAtual);
    }

    /**
     * Verifica se existe uma transicao, caso nao exista a transicao terá valor
     * null.
     * 
     * @param transicao Transicao do tipo (q3,Y,E) ou null, caso nao exista uma
     *                  transicao valida para os dados passados em
     *                  identificaTransicao
     * @return Caso a transicao tenha valor null o atributo continuarLoop altera seu
     *         valor para null e retorna ele.
     */

    private boolean transicaoValida(String transicao) {
        if (transicao == null) {
            continuarLoop = false;
        }
        return continuarLoop;
    }

}
