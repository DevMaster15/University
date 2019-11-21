import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Project {

    //questo è il progetto
    public static void main(String args[]) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        //prendo l'input da standard input, nella pratica reindirizzo da un file di testo
        try {
            input = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        input = input.replace(" ", ""); //rimpiazza gli spazi con nessuno spazio
        String[] str = input.split(",|.$", -1); //elimino dalla stringa le virgole ed i punti (se ci sono)

        double array[] = new double[str.length - 1];

        //converto uno ad uno gli elementi della stringa in elementi di tipo double
        //inserisco tali elementi nell'array
        for (int i = 0; i < str.length - 1; i++) {
            array[i] = Double.valueOf(str[i]);
        }

        double risultatoMaestro = start(array); //memorizzo il risultato finale nella variabile risultatoMaestro

        System.out.println("Risultato Maestro = " + risultatoMaestro);
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




