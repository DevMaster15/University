public class bottomUp_llis {

    public static int llis(int[] s) {

        final int n = s.length;


        int[] z = new int[n+1]; //dichiaro un nuovo array con lunghezza n+1, all'ultima posizione (v[n+1]) gli metto uno 0

        for(int x=0; x<n; x++) {
            z[x] = s[x];
        }
        z[n] = 0;


        int[][] tab = new int[n+1][n+1]; //creo una matrice ed inizializzo solamente l'ultima riga
        for(int x=0; x<=n; x++){ //x = colonne
            tab[n][x] = 0;
        }

        //bottom up resolution
        for(int i=n-1; i>=0; i--) {
            for(int j=n; j>=0; j--) {
                if(z[i] <= z[j]) { //inizialmente controllo se è minore di 0, poi decremento j
                    tab[i][j] = tab[i+1][j];
                }else {
                    tab[i][j] = Math.max(1 + tab[i+1][i], tab[i+1][j]);
                }
            }
        }

        return tab[0][n];
    }

    public static void main(String[] args)
    {
        System.out.println("Il risultato e' : " + llis(new int[] {47, 38, 39, 25, 44}));
    }

}// end class
