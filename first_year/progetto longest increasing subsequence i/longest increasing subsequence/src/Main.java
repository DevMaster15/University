import java.util.Scanner;

public class Main {

    public static final int UNKNOWN = -1;

    public static void main(String[] args) {
        System.out.println(LLIS(new int[]{27, 90, 7, 29, 49, 8, 53, 1, 28, 6}));
    }

    private static int LLIS(int[] v) {

        int n = v.length;

        int s[] = new int[n + 1];
        int[][] answer = new int[n + 2][n + 2];


        for (int k = 0; k < n; k++) {
            s[k] = v[k];

        }

        s[n] = 0; //s[5] = 0;

        for (int x = 0; x <= n; x++) {
            for (int y = 0; y <= n; y++)
                answer[x][y] = UNKNOWN;

        }

        return LLISREC(s, answer, 0, n);

    }

    //LONGEST INCREASING SUBSEQUENCE UTILIZZANDO IL METODO TOP DOWN
    private static int LLISREC(int[] s, int[][] memo, int i, int j) {

        int q = s.length;

        if (memo[i][j] == UNKNOWN) {    //controllo se nella matrice la casella è "vuota"
            if (i == q) { //se lo è ed i = q, ovvero la lunghezza della matrice, allora metto in quqlla posizione il valore 0
                memo[i][j] = 0;
            } else if (s[i] <= s[j]) { //se il valore nella posizione (del vettore) i è minore o uguale del valore nella posizione j
                memo[i][j] = LLISREC(s, memo, i + 1, j); //memorizzo nella matrice il valore dato dalla ricorsione aumentando di 1 i per passare al valore successivo del vettore
            } else {
                memo[i][j] = Math.max(1 + LLISREC(s, memo, i + 1, i), LLISREC(s, memo, i + 1, j)); //se il valore in posizone i è maggiore allora significa che c'è una sotto sequenza
                                                                                                        //crescente per cui memorizzo il valore più grande dato dalla ricorsione aumentando di 1 i
                                                                                                        //nella prima ricorsione aumento di 1 il valore totale e salto il valore minore controllando quello ancora successivo

            }
        }
        return memo[i][j];
    }

}


