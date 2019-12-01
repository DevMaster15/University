public class lisSequence {

    public static String lis(int[] s) {

        final int n = s.length;

        //array reconstruction
        int[] z = new int[n + 1];
        for (int x = 0; x < n; x++) {
            z[x] = s[x];
        }
        z[n] = 0;

        //mem table, creation + initialization only of the lower row
        int[][] tab = new int[n + 1][n + 1];
        for (int x = 0; x <= n; x++) {
            tab[n][x] = 0;
        }

        //bottom up resolution
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n; j >= 0; j--) {
                if (z[i] <= z[j]) {
                    tab[i][j] = tab[i + 1][j];
                } else {
                    tab[i][j] = Math.max(1 + tab[i + 1][i], tab[i + 1][j]);
                }
            }
        }

        int length = tab[0][n]; //saving length of the llis

        //retrieve one of the lis using the matrix
        int[] seq = new int[length];
        int c = 0; //indice del vettore seq
        int i = 0;
        int j = n;
        String risultato = "";
        while(i<n) {
            if(tab[i][j] != tab[i+1][j]) {

                seq[c] = z[i];
                c++;
                j=i; //update j to i before update i
                i++;

            } else {
                i++;
            }
        }

        for(int t=0;t<seq.length;t++)
        {
            risultato = risultato + seq[t] + " ";
        }

        return risultato;
    }

    public static void main(String[] args)
    {
        System.out.println("Il risultato e' : " + lis(new int[] {47, 38, 39, 25, 44}));
    }



} //end class

