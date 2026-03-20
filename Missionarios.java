import busca.Estado;
import busca.Nodo;
import busca.BuscaLargura;

import java.util.ArrayList;
import java.util.List;

public class Missionarios implements Estado {

    private int missionariosEsquerda;
    private int canibaisEsquerda;
    private boolean barcoNaEsquerda;

    public Missionarios(int missionarios, int canibais, boolean barcoEsquerda) {
        this.missionariosEsquerda = missionarios;
        this.canibaisEsquerda = canibais;
        this.barcoNaEsquerda = barcoEsquerda;
    }

    public String getDescricao() {
        return "Problema dos Missionarios e Canibais";
    }

    public boolean ehMeta() {
        return missionariosEsquerda == 0 && canibaisEsquerda == 0;
    }

    public int custo() {
        return 1;
    }

    public boolean estadoValido() {
        int missionariosDireita = 3 - missionariosEsquerda;
        int canibaisDireita = 3 - canibaisEsquerda;

        if (missionariosEsquerda > 0 && canibaisEsquerda > missionariosEsquerda) return false;
        if (missionariosDireita > 0 && canibaisDireita > missionariosDireita) return false;

        return true;
    }

    public List<Estado> sucessores() {
        List<Estado> proximosEstados = new ArrayList<>();

        int[][] movimentosPossiveis = {
                {1,0}, {2,0},
                {0,1}, {0,2},
                {1,1}
        };

        for (int[] movimento : movimentosPossiveis) {
            int missionarios = movimento[0];
            int canibais = movimento[1];

            if (barcoNaEsquerda) {
                tentarCriarEstado(proximosEstados,
                        missionariosEsquerda - missionarios,
                        canibaisEsquerda - canibais,
                        false);
            } else {
                tentarCriarEstado(proximosEstados,
                        missionariosEsquerda + missionarios,
                        canibaisEsquerda + canibais,
                        true);
            }
        }

        return proximosEstados;
    }

    private void tentarCriarEstado(List<Estado> lista, int m, int c, boolean barcoEsquerda) {
        if (m >= 0 && m <= 3 && c >= 0 && c <= 3) {
            Missionarios novoEstado = new Missionarios(m, c, barcoEsquerda);
            if (novoEstado.estadoValido()) {
                lista.add(novoEstado);
            }
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof Missionarios)) return false;
        Missionarios outro = (Missionarios) o;
        return missionariosEsquerda == outro.missionariosEsquerda &&
               canibaisEsquerda == outro.canibaisEsquerda &&
               barcoNaEsquerda == outro.barcoNaEsquerda;
    }

    public int hashCode() {
        return (missionariosEsquerda + "-" + canibaisEsquerda + "-" + barcoNaEsquerda).hashCode();
    }

    public String toString() {
        return "Esquerda(M=" + missionariosEsquerda + ", C=" + canibaisEsquerda + ") | " +
               "Direita(M=" + (3 - missionariosEsquerda) + ", C=" + (3 - canibaisEsquerda) + ") | " +
               "Barco: " + (barcoNaEsquerda ? "Esquerda" : "Direita");
    }

    public static void main(String[] args) {

        Missionarios estadoInicial = new Missionarios(3, 3, true);

        Nodo resultado = new BuscaLargura().busca(estadoInicial);

        if (resultado != null) {
            System.out.println("\nSolucao:\n");
            System.out.println(resultado.montaCaminho().replace(";", "\n"));
        } else {
            System.out.println("Sem solucao");
        }
    }
}
