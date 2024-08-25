public class MT {
    public static void main(String[] args) throws Exception {
        // DadosMT dadosMT = new DadosMT();
        String palavraEntrada = "1010";
        String MT = "desc_mt3.txt";

        VerificadorPalavra verificadorPalavra = new VerificadorPalavra(palavraEntrada, MT);

        /**
         * Visualiza os dados da Maquina, basta descomentar os dados.
         */
        // dadosMT = verificadorPalavra.getDadosMT();
        // System.out.println(dadosMT);
    }

}
