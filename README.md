# TRABALHO PARA DISCIPLINA GCC108 - TEORIA DA COMPUTAÇÃO

Equipe:

- Caio Henrique Torres
- Eduardo Oliveira Gomes
- Willian Brandão de Souza

## O objetivo do trabalho é desenvolver um simulador de Máquina de Turing Universal.

### DESCRIÇÃO DO TRABALHO

O usuário deve passar para o programa a descrição da MT através de um arquivo `.txt` seguindo o seguinte formato.
MT = (Q, ∑, Γ, δ, 𝑞₀, 𝑞<sub>aceita</sub>, 𝑞<sub>rejeita</sub>, _). Abaixo um formato do modelo de arquivo que deve ser passado no arquivo.

Sendo:

- Q: Estado da fita 
- ∑: Alfabeto de entrada
- Γ: Alfabeto da fita
- δ: Função de transição Transições do tipo  (qi, a)-> (qj,b,M)
- 𝑞₀: Estado inicial
- 𝑞<sub>aceita</sub>: Estados de Aceitação
- 𝑞<sub>rejeita</sub>: Estados de Rejeição
- _: Elemento representativo de vazio ou branco


```bash
(
   {q0,q1,q2,q3,q4},
   {a,b},
   {a,b,Y,_},
   {
      (q0,_)->(q1,_,D),
      (q1,Y)->(q1,Y,D),
      (q1,a)->(q2,X,D),
      (q1,_)->(q4,_,D),
      (q2,a)->(q2,a,D),
      (q2,Y)->(q2,Y,D),
      (q2,b)->(q3,Y,E),      
      (q3,Y)->(q3,Y,E),      
      (q3,a)->(q3,a,E),      
      (q3,X)->(q1,X,D)
   },
   q0,
   {q4},
   {qrej},
   _
)
 ```

 ### DESCRIÇÃO DE FUNCIONAMENTO DO ALGORITMO

O algoritmo é iniciado através da classe MT, na qual é passado a palavra a ser verificada e nome da descrição da Máquina de Turing que será utilizada. 

Assim é instanciado o objeto Veririficador palavra que é o responsável por realizar a manipulação de toda a fita. Assim nesse método é instanciado tanto um objeto LeitorMaquina quanto o objeto DadosMT, reponsáveis respectivamente por Realizar a leitura do arquivo passado pelo usuário e armazenar os dados da descrição da MT.

A palavra é verificada de acordo com as transições passadas na descrição, caso o algoritmo detecte que não há transição possível ele rejeite a palavra, não sendo necessário que na descrição da máquina seja passada todas as transições que poderiam gerar rejeição.

#### CLASSE LEITORMAQUINA
Dessa maneira, a classe `LeitorMaquina` recebe o arquivo como entrada e separa e posteriormente salvo os dados de acordo com o formatto MT = (Q, ∑, Γ, δ, 𝑞₀, 𝑞<sub>aceita</sub>, 𝑞<sub>rejeita</sub>, _). Dessa maneira fica mais fácil realizar a manipulação posterior dos dados.

Vale ressaltar que os dados são salvos em sua maioria em estrutura de dados do tipo lista, salvo excessão do "estado Inicial","elemento que representa o branco" e as "transiçoes". Sendo dessa maneira armazendas como String, Char e em um HashMap.

Foi decidido utlizar o Hashmap nas transições para ficar mais fácil de realizar as transiçoes, uma vez que ao se passar uma chave, ele retornaria um valor. Deste modo como a transição é do tipo `(q0,_)->(q1,_,D)`, ao se separar `(q0,_)` como a chave o retorno seria `(q1,_,D)`, facilitando assim as manipulações dos dados posteriormente.

#### CLASSE VERIFICADORPALAVRA

Com os dados todos armazendos na no objeto `DadosMT` pode se então realizar a manipulacao da fita. Deste modo, iniciou a palavra inicial inserindo os simbolos de branco no ínicio e no final da palavra e após isso o simbolo de estado inicial no inicio da fita representado a posição da cabeça de leitura.

A ideia de manipulação da fita é trabalhar com 2 strings, chamadas no código de `palavraEsq` e `palavraDir` que representariam os dados a esquerda da cabeça de leitura e os dados a direita, sendo o primeiro dado nessa string onde a fita está posicionada. Assim, as manipulaçoes modificariam essas strings alterando de acordo com o valor de retorno passado ao se buscar pela chave no HashMap. No entando, caso essas strings tivessem vazias eram inseridos simbolos representativo do branco para que a leitura pudesse continuar sem maiores problemas, uma vez que a fita é infinita para ambos os lados.

### MANUAL DE USO DO ALGORITMO

Para realizar a verificação da palavra na MT fornecida basta substituir os atributos `palavraEntrada` com a palavra que se dejesa verificar e `MT` com local seguido pelo nome do arquivo. 

O arquivo `MT.java`, é mostrado abaixo,

```bash
public class MT {
    public static void main(String[] args) throws Exception {
        // DadosMT dadosMT = new DadosMT();
        String palavraEntrada = "aabb";
        String MT = "desc_mt1copy.txt";

        VerificadorPalavra verificadorPalavra = new VerificadorPalavra(palavraEntrada, MT);

        /**
         * Visualiza os dados da Maquina, basta descomentar os dados.
         */
        // dadosMT = verificadorPalavra.getDadosMT();
        // System.out.println(dadosMT);
    }
}
```



### ALGUMAS DESCRIÇÕES DE MTs

- `desc_mt1.txt` 
    Aceita somente palavras do formato a<sup>n</sup>b<sup>n</sup>
    - aabb 
    - aaabbb
    
- `desc_mt2.txt`
    Aceita somente palavras do tipo {w # w | w ∈ {0,1}*}
    - 01#01
    - 101#101
    - \#

- `desc_mt3`
    Aceita somente palavras do tipo {ww^R^ | w ∈ {0,1}}
    - 0110
    - 0000
    - 11100111

- `desc_mt4`
    Aceita somente palavras onde todo "0" é reguido por "1"
    - 11101
    - 1010111

### TESTES

#### Execução da maquina `desc_mt1.txt`(a^n^b^n^)
- palavras `aaabbb`

```bash
{q0}_aaabbb_
_{q1}aaabbb_
_X{q2}aabbb_
_Xa{q2}abbb_
_Xaa{q2}bbb_
_Xa{q3}aYbb_
_X{q3}aaYbb_
_{q3}XaaYbb_
_X{q1}aaYbb_
_XX{q2}aYbb_
_XXa{q2}Ybb_
_XXaY{q2}bb_
_XXa{q3}YYb_
_XX{q3}aYYb_
_X{q3}XaYYb_
_XX{q1}aYYb_
_XXX{q2}YYb_
_XXXY{q2}Yb_
_XXXYY{q2}b_
_XXXY{q3}YY_
_XXX{q3}YYY_
_XX{q3}XYYY_
_XXX{q1}YYY_
_XXXY{q1}YY_
_XXXYY{q1}Y_
_XXXYYY{q1}_
_XXXYYY_{q4}
ACEITA
```

- palavra `aaabbbb`

```bash
{q0}_aaabbbb_
_{q1}aaabbbb_
_X{q2}aabbbb_
_Xa{q2}abbbb_
_Xaa{q2}bbbb_
_Xa{q3}aYbbb_
_X{q3}aaYbbb_
_{q3}XaaYbbb_
_X{q1}aaYbbb_
_XX{q2}aYbbb_
_XXa{q2}Ybbb_
_XXaY{q2}bbb_
_XXa{q3}YYbb_
_XX{q3}aYYbb_
_X{q3}XaYYbb_
_XX{q1}aYYbb_
_XXX{q2}YYbb_
_XXXY{q2}Ybb_
_XXXYY{q2}bb_
_XXXY{q3}YYb_
_XXX{q3}YYYb_
_XX{q3}XYYYb_
_XXX{q1}YYYb_
_XXXY{q1}YYb_
_XXXYY{q1}Yb_
_XXXYYY{q1}b_
REJEITA
```

#### Execução da maquina `desc_mt2.txt`({w # w | w ∈ {0,1}*})

- palavra `0101#0101`

```bash
{q0}_0101#0101_
_{q1}0101#0101_
_X{q6}101#0101_
_X1{q6}01#0101_
_X10{q6}1#0101_
_X101{q6}#0101_
_X101#{q7}0101_
_X101{q4}#X101_
_X10{q5}1#X101_
_X1{q5}01#X101_
_X{q5}101#X101_
_{q5}X101#X101_
_X{q1}101#X101_
_XY{q2}01#X101_
_XY0{q2}1#X101_
_XY01{q2}#X101_
_XY01#{q3}X101_
_XY01#X{q3}101_
_XY01#{q4}XY01_
_XY01{q4}#XY01_
_XY0{q5}1#XY01_
_XY{q5}01#XY01_
_X{q5}Y01#XY01_
_XY{q1}01#XY01_
_XYX{q6}1#XY01_
_XYX1{q6}#XY01_
_XYX1#{q7}XY01_
_XYX1#X{q7}Y01_
_XYX1#XY{q7}01_
_XYX1#X{q4}YX1_
_XYX1#{q4}XYX1_
_XYX1{q4}#XYX1_
_XYX{q5}1#XYX1_
_XY{q5}X1#XYX1_
_XYX{q1}1#XYX1_
_XYXY{q2}#XYX1_
_XYXY#{q3}XYX1_
_XYXY#X{q3}YX1_
_XYXY#XY{q3}X1_
_XYXY#XYX{q3}1_
_XYXY#XY{q4}XY_
_XYXY#X{q4}YXY_
_XYXY#{q4}XYXY_
_XYXY{q4}#XYXY_
_XYX{q5}Y#XYXY_
_XYXY{q1}#XYXY_
_XYXY#{q8}XYXY_
_XYXY#X{q8}YXY_
_XYXY#XX{q8}XY_
_XYXY#XXX{q8}Y_
_XYXY#XXXX{q8}_
_XYXY#XXXX_{qf}
ACEITA
```
- palavra `01011#0101`

```bash
{q0}_01011#0101_
_{q1}01011#0101_
_X{q6}1011#0101_
_X1{q6}011#0101_
_X10{q6}11#0101_
_X101{q6}1#0101_
_X1011{q6}#0101_
_X1011#{q7}0101_
_X1011{q4}#X101_
_X101{q5}1#X101_
_X10{q5}11#X101_
_X1{q5}011#X101_
_X{q5}1011#X101_
_{q5}X1011#X101_
_X{q1}1011#X101_
_XY{q2}011#X101_
_XY0{q2}11#X101_
_XY01{q2}1#X101_
_XY011{q2}#X101_
_XY011#{q3}X101_
_XY011#X{q3}101_
_XY011#{q4}XY01_
_XY011{q4}#XY01_
_XY01{q5}1#XY01_
_XY0{q5}11#XY01_
_XY{q5}011#XY01_
_X{q5}Y011#XY01_
_XY{q1}011#XY01_
_XYX{q6}11#XY01_
_XYX1{q6}1#XY01_
_XYX11{q6}#XY01_
_XYX11#{q7}XY01_
_XYX11#X{q7}Y01_
_XYX11#XY{q7}01_
_XYX11#X{q4}YX1_
_XYX11#{q4}XYX1_
_XYX11{q4}#XYX1_
_XYX1{q5}1#XYX1_
_XYX{q5}11#XYX1_
_XY{q5}X11#XYX1_
_XYX{q1}11#XYX1_
_XYXY{q2}1#XYX1_
_XYXY1{q2}#XYX1_
_XYXY1#{q3}XYX1_
_XYXY1#X{q3}YX1_
_XYXY1#XY{q3}X1_
_XYXY1#XYX{q3}1_
_XYXY1#XY{q4}XY_
_XYXY1#X{q4}YXY_
_XYXY1#{q4}XYXY_
_XYXY1{q4}#XYXY_
_XYXY{q5}1#XYXY_
_XYX{q5}Y1#XYXY_
_XYXY{q1}1#XYXY_
_XYXYY{q2}#XYXY_
_XYXYY#{q3}XYXY_
_XYXYY#X{q3}YXY_
_XYXYY#XY{q3}XY_
_XYXYY#XYX{q3}Y_
_XYXYY#XYXY{q3}_
REJEITA

```

- palavra `#`

```bash
{q0}_#_
_{q1}#_
_#{q8}_
_#_{qf}
ACEITA

```

#### Execução da maquina `desc_mt3.txt`({ww^R^ | w ∈ {0,1}})

- palavra `11100111`
```bash
{q0}_11100111_
_{q1}11100111_
_Y{q2}1100111_
_Y1{q2}100111_
_Y11{q2}00111_
_Y110{q2}0111_
_Y1100{q2}111_
_Y11001{q2}11_
_Y110011{q2}1_
_Y1100111{q2}_
_Y110011{q3}1_
_Y11001{q4}1Y_
_Y1100{q4}11Y_
_Y110{q4}011Y_
_Y11{q4}0011Y_
_Y1{q4}10011Y_
_Y{q4}110011Y_
_{q4}Y110011Y_
_Y{q1}110011Y_
_YY{q2}10011Y_
_YY1{q2}0011Y_
_YY10{q2}011Y_
_YY100{q2}11Y_
_YY1001{q2}1Y_
_YY10011{q2}Y_
_YY1001{q3}1Y_
_YY100{q4}1YY_
_YY10{q4}01YY_
_YY1{q4}001YY_
_YY{q4}1001YY_
_Y{q4}Y1001YY_
_YY{q1}1001YY_
_YYY{q2}001YY_
_YYY0{q2}01YY_
_YYY00{q2}1YY_
_YYY001{q2}YY_
_YYY00{q3}1YY_
_YYY0{q4}0YYY_
_YYY{q4}00YYY_
_YY{q4}Y00YYY_
_YYY{q1}00YYY_
_YYYX{q5}0YYY_
_YYYX0{q5}YYY_
_YYYX{q6}0YYY_
_YYY{q4}XXYYY_
_YYYX{q1}XYYY_
_YYYXX{q7}YYY_
_YYYXXY{q7}YY_
_YYYXXYY{q7}Y_
_YYYXXYYY{q7}_
_YYYXXYY{q8}Y_
ACEITA
```

- palavra `101101`
```bash
{q0}_101101_
_{q1}101101_
_Y{q2}01101_
_Y0{q2}1101_
_Y01{q2}101_
_Y011{q2}01_
_Y0110{q2}1_
_Y01101{q2}_
_Y0110{q3}1_
_Y011{q4}0Y_
_Y01{q4}10Y_
_Y0{q4}110Y_
_Y{q4}0110Y_
_{q4}Y0110Y_
_Y{q1}0110Y_
_YX{q5}110Y_
_YX1{q5}10Y_
_YX11{q5}0Y_
_YX110{q5}Y_
_YX11{q6}0Y_
_YX1{q4}1XY_
_YX{q4}11XY_
_Y{q4}X11XY_
_YX{q1}11XY_
_YXY{q2}1XY_
_YXY1{q2}XY_
_YXY{q3}1XY_
_YX{q4}YYXY_
_YXY{q1}YXY_
_YXYY{q7}XY_
_YXYYX{q7}Y_
_YXYYXY{q7}_
_YXYYX{q8}Y_
ACEITA
```

- palavra `1010`
```bash
{q0}_1010_
_{q1}1010_
_Y{q2}010_
_Y0{q2}10_
_Y01{q2}0_
_Y010{q2}_
_Y01{q3}0_
_Y0{qrej}10_
REJEITA
```

#### Execução da maquina `desc_mt4.txt`(palavras onde todo "0" é seguido por "1")

- palavra `1010111001`
```bash
{q0}_1010111001_
_{q1}1010111001_
_1{q1}010111001_
_10{q2}10111001_
_101{q1}0111001_
_1010{q2}111001_
_10101{q1}11001_
_101011{q1}1001_
_1010111{q1}001_
_10101110{q2}01_
_101011100{qrej}1_
REJEITA
```

- palavra `1010111`
```bash
{q0}_1010111_
_{q1}1010111_
_1{q1}010111_
_10{q2}10111_
_101{q1}0111_
_1010{q2}111_
_10101{q1}11_
_101011{q1}1_
_1010111{q1}_
ACEITA

```
