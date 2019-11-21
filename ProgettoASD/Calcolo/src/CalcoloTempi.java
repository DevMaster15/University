import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalcoloTempi {
    public static seedStorage seedList = new seedStorage();

    public static void main(String[] args) throws IOException {
        System.out.println("Inserisci la dimensione dell'array: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        String[] parts = input.split(" +");
        int[] inputFinal = new int[parts.length];

        for (int j = 0; j < inputFinal.length; j++) {
            int e = Integer.parseInt(parts[j]);
            inputFinal[j] = e;
        }

        int n = inputFinal[0];
        seedList.insert( (double) System.currentTimeMillis()/100000);
        double tMin = tMin();
        double result = misurazione(n, tMin);
        System.out.println("Misurazione pari a " + result * 1/1000 + " secondi.");

    }

    public static long granularita() {
        long t0 = System.currentTimeMillis();
        long t1 = System.currentTimeMillis();
        while (t0 == t1) {
            t1 = System.currentTimeMillis();
        }
        return (t1 - t0);
    }

    public static double tMin() {
        return granularita() * 20;
    }

    public static int calcolaRipP(int n, double tMin) {
        double t0 = 0;
        double t1 = 0;
        int rip = 1;
        double[] e;
        while (t1 - t0 <= tMin) {
            rip = 2 * rip;
            t0 = System.currentTimeMillis();
            for (int i = 0; i < rip; i++) {
                e = prepara(n);
            }
            t1 = System.currentTimeMillis();
        }
        int max = rip;
        int min = rip / 2;
        int cicliErrati = 5;
        while (max - min >= cicliErrati) {
            rip = (max + min) / 2;
            t0 = System.currentTimeMillis();
            for (int i = 0; i < rip; i++) {
                e = prepara(n);
            }
            t1 = System.currentTimeMillis();
            if (t1 - t0 <= tMin) {
                min = rip;
            } else {
                max = rip;
            }
        }
        return max;
    }

    public static int calcolaRipPW(int n, double tMin) {
        double t0 = 0;
        double t1 = 0;
        int rip = 1;
        double[] e;
        while (t1 - t0 <= tMin) {
            rip = 2 * rip;
            t0 = System.currentTimeMillis();
            for (int i = 0; i < rip; i++) {
                e = prepara(n);
                start(e);
            }
            t1 = System.currentTimeMillis();
        }
        int max = rip;
        int min = rip / 2;
        int cicliErrati = 5;
        while (max - min >= cicliErrati) {
            rip = (max + min) / 2;
            t0 = System.currentTimeMillis();
            for (int i = 0; i < rip; i++) {
                e = prepara(n);
                start(e);
            }
            t1 = System.currentTimeMillis();
            if (t1 - t0 <= tMin) {
                min = rip;
            } else {
                max = rip;
            }
        }
        return max;
    }

    public static double[] prepara(int n) {
        double[] d = new double[n];
        for (int i = 0; i < n; i++) {
            //d[i] = (int) (pseudoRandom() * 50 + 1);
            d[i] = pseudoRandom();
            //d[i] = 0.23;
        }
        return d;
    }

    public static double pseudoRandom() {
        double seed = seedList.getSeed();
        int a = 16807;
        int m = 2147483647;
        int q = 127773;
        int r = 2836;
        int hi = (int) (seed / q);
        double lo = seed - q * hi;
        double test = a * lo - r * hi;
        if (test < 0) {
            seedList.delete();
            seedList.insert(test + m);
        } else {
            seedList.delete();
            seedList.insert(test);
        }
        return seed/m;
    }

    public static double tempoMedioNetto(int n, double tMin) {
        int ripTara = calcolaRipP(n, tMin);
        int ripLordo = calcolaRipPW(n, tMin);
        double[] e = new double[n];
        double t0 = System.currentTimeMillis();
        for (int i = 0; i < ripTara; i++) {
            e = prepara(n);
        }
        long t1 = System.currentTimeMillis();
        double tTara =  t1 - t0;
        t0 = System.currentTimeMillis();
        for (int i = 0; i < ripLordo; i++) {
            e = prepara(n);
            start(e);
        }
        t1 = System.currentTimeMillis();
        double tLordo = t1 - t0;
        double tMedio = (tLordo / ripLordo) - (tTara / ripTara);
        return tMedio;
    }

    public static double misurazione(int n, double tMin) {
        int c = 10;
        double t = 0;
        double sum2 = 0;
        double cn = 0;
        double delta = Double.MAX_VALUE;
        double e = 0;
        while (!(delta < (e / 20))) {
            for (int i = 0; i < c; i++) {
                double m = tempoMedioNetto(n, tMin);
                t = t + m;
                sum2 = sum2 + (m * m);
            }
            cn = cn + c;
            e = t / cn;
            double s = Math.sqrt(sum2 / cn - (e * e));
            delta = ( 1 / Math.sqrt(cn)) * s * 1.96;
        }
        return e;
    }


    /**
     * Start è la funzione da cui parte l'intera procedura
     * c'è la prima chiamata a pivot e partition per sistemare l'array attorno ad un valore
     * le somme degli elementi a sinistra e destra del pivot
     * la somma di tutti gli elementi
     *
     * e la chiamata alla procedura medianaPesata
     *
     * @param array di valori double
     * @return 0 se la sommaTotale (di tutti gli elementi = 0)
     * @return il risultato della procedua medianaPesata() che sarà la mediana pesata inferiore
     */

    public static double start(double array[]) {
        int left = 0;
        int right = array.length - 1;
        int p = pivot(array, left, right);
        int pivotIndex = partition(array, left, right, p);

        double sommaLeftPivot = add(array, 0, pivotIndex - 1); //somma da  fino all'elemento
        double sommaRightPivot = add(array, pivotIndex + 1, array.length - 1); //ok
        double sommaTotale = sommaRightPivot + sommaLeftPivot + array[pivotIndex];
        double sommaMezzi = sommaTotale/2;

        if(sommaTotale == 0)
            return 0;
        else{
            while(!(sommaLeftPivot < sommaMezzi && sommaRightPivot <= sommaMezzi)){
                if(sommaLeftPivot >= sommaMezzi){
                    right = pivotIndex;
                    p = pivot(array, left, right);
                    pivotIndex = partition(array, left, right, p);
                    sommaRightPivot = sommaRightPivot + add(array, pivotIndex + 1, right);
                    sommaLeftPivot = sommaTotale - sommaRightPivot - array[pivotIndex];
                } else {
                    left = pivotIndex;
                    p = pivot(array, left, right);
                    pivotIndex = partition(array, left, right, p);
                    sommaLeftPivot = sommaLeftPivot + add(array, left, pivotIndex - 1);
                    sommaRightPivot = sommaTotale - sommaLeftPivot - array[pivotIndex];
                }
            }

        }

        return array[pivotIndex];
    }

    /**
     * Prende in input un intero left che indica l'indice di inizio
     *     dell'array/sottoarray, e un right per la fine, e un array di double.
     *     Divide l'array in gruppi di 5 elementi ciascuno, e ne calcola la mediana.
     *     Ricorsivamente poi calcola la mediana delle mediane:
     *     1)  A intervalli di 5 in 5, applica median ai sottoarray, calcolandone
     *         le mediane
     *     2)  Le raggruppa tramite swap, a sinistra dell'array.
     *     3)  Quando ha trovato le n/5 mediane, calcola ricorsivamente le medinae
     *         sul sottoarray di inizi left e fine count.
     *     4)  Quando il gruppo di mediane a inizio array è minore o uguale a 5,
     *         ne calcola la mediana e ne restituisce l'indice.
     *
     * @param array
     * @param left
     * @param right
     * @return
     */

    private static int pivot(double array[], int left, int right) {

        if ((right - left) <= 5) {
            return insertionSortMedian(array, left, right);
        }

        int count = left;

        for (int i = left; i <= right; i += 5) {
            int tmpRight = i + 4;

            if (tmpRight > right) {
                tmpRight = right;
            }

            int tmpMed;
            tmpMed = insertionSortMedian(array, i, tmpRight);
            swap(array, tmpMed, count);
            count++;
        }
        count--;
        return pivot(array, left, count);
    }

    /**
     * prende come parametri l'array di double e gli indice left e right che indicano rispettivamente
     * l'indice più a sinistra e più a destra dell'array/sottoarray, e l'indice p che è l'indice del pivot
     *
     * questa procedura "divide" l'array in tre parti da left
     *
     * @param array
     * @param left
     * @param right
     * @param p
     * @return
     */

    public static int partition(double[] array, int left, int right, int p) {
        int i = left;
        int j = left;
        int n = right;
        while (j <= n) {
            if (array[j] < array[p]) {
                swap(array, i, j);
                i++;
                j++;
            } else if (array[j] > array[p]) {
                swap(array, j, n);
                n = n - 1;
            } else {
                j = j + 1;
            }
        }
        return ((i + n) / 2);
    }


    public static int insertionSortMedian(double array[], int left, int right) {

        int median;

        for (int i = left + 1; i <= right; i++) {
            double key = array[i];
            int j = i - 1;
            while (j >= left && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }

        median = (right + left) / 2;
        return median;
    }

    public static void swap(double array[], int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static double add(double array[], int left, int right) {
        double somma = 0;

        for (int i = left; i <= right; i++) {
            somma += array[i];
        }

        return somma;
    }

}
