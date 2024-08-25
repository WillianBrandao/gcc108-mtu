import java.util.*;

public class DadosMT {

  private List<String> estados;
  private List<String> alfabetoEntrada;
  private List<String> alfabetoFita;
  private Map<String, String> transicoes;
  private String estadoInicial;
  private List<String> estadosAceito;
  private List<String> estadosRejeicao;
  private char simboloBranco;

  /**
   * Construtor da classe DadosMT. Inicializa tods as listas e o Hashmap.
   * Ideia é que o HashMap seja responsável por identificar a saída de acordo com
   * a entrada
   */
  DadosMT() {
    estados = new ArrayList<>();
    alfabetoEntrada = new ArrayList<>();
    transicoes = new HashMap<>();
    alfabetoFita = new ArrayList<>();
    estadosAceito = new ArrayList<>();
    estadosRejeicao = new ArrayList<>();
  }

  /**
   * Adiciona um estado à lista de estados que existem na MT
   * 
   * @param estado O estado a ser adicionado.
   */
  public void adicionaEstados(String estado) {
    estados.add(estado);
  }

  /**
   * Adiciona um símbolo ao alfabeto de presente na MT
   * 
   * @param simbolo O símbolo a ser adicionado.
   */
  public void adicionaAlfabetoEntrada(String simbolo) {
    alfabetoEntrada.add(simbolo);
  }

  /**
   * Adiciona um símbolo ao alfabeto presente na fita da MT.
   * 
   * @param simbolo O símbolo a ser adicionado.
   */
  public void adicionaAlfabetoFita(String simbolo) {
    alfabetoFita.add(simbolo);
  }

  /**
   * Define o estado inicial da MT
   * 
   * @param estado O estado inicial.
   */
  public void defineEstadoInicial(String estado) {
    estadoInicial = estado;
  }

  /**
   * Adiciona um estado à lista de estados aceitos da MT
   * 
   * @param estado O estado a ser adicionado.
   */
  public void adicionaEstadoAceito(String estado) {
    estadosAceito.add(estado);
  }

  /**
   * Adiciona uma transição ao mapa de transições.
   * Exmploe de transicao (q0,_)->(q1,_,D)
   * Assim recebe "q0,_" como chave e armazena o valor "q1,_,D"
   * 
   * @param chave A chave da transição.
   * @param valor O valor da transição.
   */
  public void adicionaTransicoes(String chave, String valor) {
    transicoes.put(chave, valor);
  }

  /**
   * Adiciona um estado de rejeicao a lista de estados de rejeicao.
   * 
   * @param estado O estado a ser adicionado.
   */
  public void adicionaEstadosRejeicao(String estado) {
    estadosRejeicao.add(estado);
  }

  public void adicionaElementoBranco(char elementoBranco) {
    simboloBranco = elementoBranco;
  }

  /**
   * Retorna a transição associada a uma chave.
   * 
   * @param chave A chave da transição. Tipo "q0,_"
   * @return A transição associada à chave retorna "q1,_,D"
   */
  public String retornaTransicao(String chave) {
    return transicoes.get(chave);
  }

  /**
   * Retorna o estado inicial.
   * 
   * @return O estado inicial.
   */
  public String getEstadoInicial() {
    return estadoInicial;
  }

  /**
   * Verifica se um estado é um estado final.
   * 
   * @param estado O estado a ser verificado.
   * @return Verdadeiro se o estado for um estado final, falso caso contrário.
   */
  public boolean ehEstadoFinal(String estado) {
    for (String e : estadosAceito) {
      if (e.equals(estado)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Verifica se um estado é um estado de rejeicao.
   * 
   * @param estado O estado a ser verificado.
   * @return Verdadeiro se o estado for um estado final, falso caso contrário.
   */
  public boolean ehEstadoRejeicao(String estado) {
    for (String e : estadosRejeicao) {
      if (e.equals(estado)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Impressão de todos os dados armazenados na MT.
   */

  @Override
  public String toString() {
    return ("DadosMT{" +
        "estados='" +
        estados +
        '\'' +
        ", alfabetoEntrada='" +
        alfabetoEntrada +
        '\'' +
        ", alfabetoFita='" +
        alfabetoFita +
        '\'' +
        ", transicoes='" +
        transicoes +
        '\'' +
        ", estadoInicial='" +
        estadoInicial +
        '\'' +
        ", estadosAceito='" +
        estadosAceito +
        '\'' +
        ", estadosRejeicao='" +
        estadosRejeicao +
        '\'' +
        ", SimboloBranco='" +
        simboloBranco +
        '\'' +
        '}');
  }

  public char getSimboloBranco() {
    return simboloBranco;
  }
}