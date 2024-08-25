import java.io.*;

public class LeitorMaquina {

    /**
     * Lê os dados do arquivo e retorna um objeto DadosMT.
     * 
     * @param nomeArquivo O nome do arquivo a ser lido.
     * @return Um objeto DadosMT contendo os dados lidos do arquivo.
     * @throws IOException Se ocorrer um erro ao ler o arquivo.
     */
    public DadosMT lerDados(String nomeArquivo) throws IOException {
        DadosMT dadosMT = new DadosMT();
        try (BufferedReader arq = new BufferedReader(new FileReader(nomeArquivo))) {
            String texto = "";
            String linha = arq.readLine();

            while (linha != null) {
                texto += linha;
                linha = arq.readLine();
            }
            // Divide a String com os arquivos de configuracao em blocos que vao ser usados
            // para armazenar os dados na classe DadosMT
            String[] blocos = texto.split("},");

            adiconaEstados(adicionaElementos(blocos[0]), dadosMT);
            adicionaAlfabetoEntrada(adicionaElementos(blocos[1]), dadosMT);
            adicionaAlfabetoFita(adicionaElementos(blocos[2]), dadosMT);
            adicionaTransicoes(adicionaElementosTransicao(blocos[3]), dadosMT);
            controleEstados(blocos[4], dadosMT);// Adiciona Estado inicial e estados finais
            adicionaEstadosRejeicao(adicionaElementos(blocos[5]), dadosMT);
            adicionaSimboloBranco(blocos[6], dadosMT);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
        }
        return dadosMT;
    }

    /**
     * Remove caracteres especiais de um linha e a divide em elementos.
     * 
     * @param linha A linha a ser processada.
     * @return Um array de Strings contendo os elementos extraídos da linha.
     */
    public String[] adicionaElementos(String linha) {
        String[] removido = linha.replaceAll("[\\{\\}\\(\\)\\s]", "").split(",");
        return removido;
    }

    /**
     * Adiciona estados ao objeto DadosMT.
     * 
     * @param elementos Os estados a serem adicionados.
     * @param dadosMT   O objeto DadosMT ao qual os estados serão adicionados.
     */
    public void adiconaEstados(String[] elementos, DadosMT dadosMT) {
        for (String elemento : elementos) {
            dadosMT.adicionaEstados(elemento);
        }
    }

    /**
     * Adiciona alfabeto de entrada ao objeto DadosMT.
     * 
     * @param elementos O alfabeto de entrada a ser adicionado.
     * @param dadosMT   O objeto DadosMT ao qual o alfabeto de entrada será
     *                  adicionado.
     */
    public void adicionaAlfabetoEntrada(String[] elementos, DadosMT dadosMT) {
        for (String elemento : elementos) {
            dadosMT.adicionaAlfabetoEntrada(elemento);
        }
    }

    /**
     * Adiciona alfabeto de fita ao objeto DadosMT.
     * 
     * @param elementos O alfabeto de fita a ser adicionado.
     * @param dadosMT   O objeto DadosMT ao qual o alfabeto de fita será adicionado.
     */
    public void adicionaAlfabetoFita(String[] elementos, DadosMT dadosMT) {
        for (String elemento : elementos) {
            dadosMT.adicionaAlfabetoFita(elemento);
        }
    }

    /**
     * Separa os estados em inicial e os finais, que são passados junto por
     * parametros e adiciona eles ao objeto DadosMT
     * 
     * @param linha   Estado inicial junto com os estados finais
     * @param dadosMT O objeto DadosMT ao qual o estado inicial e os estados finais
     *                pertencem
     */
    public void controleEstados(String linha, DadosMT dadosMT) {
        String[] estados = linha.replace(" ", "").split(",\\{");
        defineEstadoInicial(estados[0], dadosMT);
        adicionaEstadoAceito(estados[1], dadosMT);
    }

    /**
     * Adiciona estado aceito ao objeto DadosMT.
     * 
     * @param linha   A linha contendo o estado aceito.
     * @param dadosMT O objeto DadosMT
     */
    public void adicionaEstadoAceito(String linha, DadosMT dadosMT) {
        String[] removido = linha.replaceAll("[\\{\\}\\(\\)\\s]", "").split(",");
        for (String elemento : removido) {
            dadosMT.adicionaEstadoAceito(elemento);
        }
    }

    /**
     * Define o estado inicial do objeto DadosMT.
     * 
     * @param linha   A linha contendo o estado inicial.
     * @param dadosMT O objeto DadosMT cujo estado inicial será definido.
     */
    public void defineEstadoInicial(String linha, DadosMT dadosMT) {
        String inicial = linha.replaceAll("[\\{\\}\\(\\)\\s\\,]", "");
        dadosMT.defineEstadoInicial(inicial);
    }

    /**
     * Remove caracteres especiais de uma linha e a divide em elementos de
     * transição.
     * 
     * @param linha A linha a ser processada.
     * @return Um array de Strings contendo os elementos de transição extraídos da
     *         linha.
     */
    public String[] adicionaElementosTransicao(String linha) {
        String[] removido = linha.replaceAll("[\\{\\}\\s]", "").split("\\),");
        for (int i = 0; i < removido.length; i++) {
            removido[i] = removido[i].replaceAll("[\\(\\)]", "");
        }
        return removido;
    }

    /**
     * Adiciona transições ao objeto DadosMT.
     * 
     * @param elementos As transições a serem adicionadas.
     * @param dadosMT   O objeto DadosMT ao qual as transições serão adicionadas.
     */
    public void adicionaTransicoes(String[] elementos, DadosMT dadosMT) {
        for (String string : elementos) {
            String[] separaChaveValor = string.split("->");
            dadosMT.adicionaTransicoes(separaChaveValor[0], separaChaveValor[1]);
        }
    }

    /**
     * Adiciona simbolo correspondente a Branco aos dados da MT
     * 
     * @param elementoBranco
     * @param dadosMT
     */

    private void adicionaSimboloBranco(String elementoBranco, DadosMT dadosMT) {
        dadosMT.adicionaElementoBranco((elementoBranco.replaceAll("[\\)\\s]", "")).charAt(0));

    }

    /**
     * Metodo responsavel por adicionar os estados de Rejeicao passados
     * 
     * @param elementos Vetor de estados de rejeicao passados que foram separados
     * @param dadosMT
     */

    private void adicionaEstadosRejeicao(String[] elementos, DadosMT dadosMT) {
        for (String elemento : elementos) {
            dadosMT.adicionaEstadosRejeicao(elemento);
        }
    }
}