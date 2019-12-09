import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CalcoloTempi {
    public static seedStorage seedList = new seedStorage();
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Inserisci la dimensione dell'array: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        int dim = 100000;


        String[] parts = input.split(" ");
        int[] inputFinal = new int[parts.length];

        for (int j = 0; j < inputFinal.length; j++) {
            int e = Integer.parseInt(parts[j]);
            inputFinal[j] = e;
        }

        int n = inputFinal[0];
        seedList.insert((double) System.currentTimeMillis() / 100000);
        double tMin = tMin();
        double result = misurazione(n, tMin);
        System.out.println("Misurazione pari a " + result * 1 / 1000 + " secondi.");




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

   //il programma parte da qua, calcolando per la prima volta tutti i valori
    public static double weightedMedian(double array[]){
        int left_idx = 0;
        int right_idx = array.length;
        int array_idx[] = new int[2];
        int pivot_idx = pivot(array, left_idx, right_idx-1);
        array_idx = threeWayPartition(array, left_idx, right_idx, pivot_idx); //mi ritorna un array di due posizioni

        //calcolo le varie somme
        double sumLeftPivot = add(array, 0, array_idx[0] - 1);
        double sumRightPivot = add(array, array_idx[1] + 1, right_idx-1);
        double sumTotale = add(array, left_idx, right_idx-1); //modificato qua la somma

        //se la sommaTotale = 0 signfica che tutti gli elementi sono = 0, visto che non ci possono essere elementi negativi
        if(sumTotale == 0)
            return 0;

        //richiamo weightedMedian con questi valori, e controllo se la condizione i base si verifica
        //se si verifica il problema è stato risolto e restituisco il valore
        //sennò mi sposto intorno al vettore in cerca del risultato
        return weightedMedianRec(array, left_idx, right_idx-1, sumLeftPivot, sumRightPivot, sumTotale, sumTotale/2, array_idx);
    }


    //metodo "cuore" del sistema
    public static double weightedMedianRec(double array[], int left_idx, int right_idx, double sumLeftPivot,
                                        double sumRightPivot, double sumTotale, double sumTarget, int arrayIndex[]) {



        if (sumLeftPivot < sumTarget && sumRightPivot <= sumTarget) //controllo se la condizione è verificata, se lo è restituisco il valore
            return array[arrayIndex[0]];

        else {
            if (sumLeftPivot >= sumTarget) { //se non lo è controllo se l'elemento si trova nella parte sinistra
                //se si trova a sinistra mi sposto, e devo escludere la parte destra dal resto dei controlli

                int tmpright = arrayIndex[0]; //tmpRight sarà il nuovo right, visto che devo escludere la parte a destra del pivot
                
                int pivot_idx = pivot(array, left_idx, tmpright);
                arrayIndex = threeWayPartition(array, left_idx, tmpright, pivot_idx);

                sumRightPivot = sumRightPivot + add(array, arrayIndex[1] + 1, tmpright); //ricalcolo le somme
                sumLeftPivot = sumTotale - sumRightPivot - add(array, arrayIndex[0], arrayIndex[1]);

                //faccio la ricorsione passando i nuovi valori ed il "nuovo" sotto array con indici left e tmpRight
                return weightedMedianRec(array, left_idx, tmpright, sumLeftPivot, sumRightPivot, sumTotale, sumTarget, arrayIndex);

            } else {
                //se non si trova a sinistra vuol dire che si trova a destra
                //quindi mi sposto a destra e ricalcolo i valori escludendno la parte sinistra dal resto dei controlli

                int tmpleft = arrayIndex[1]; //tmpLeft sarà il nuovo left, visto che devo escludere la parte a sinistra del pivot
                int pivot_idx = pivot(array, tmpleft, right_idx);
                arrayIndex = threeWayPartition(array, tmpleft, right_idx+1, pivot_idx); 

                sumLeftPivot = sumLeftPivot + add(array, tmpleft, arrayIndex[0] - 1); //ricalcolo le somme
                sumRightPivot = sumTotale - sumLeftPivot - add(array, arrayIndex[0], arrayIndex[1]);
                //faccio la ricorsione passando i nuovi valori ed il "nuovo" sotto array con indici temLeft e right
                return weightedMedianRec(array, tmpleft, right_idx, sumLeftPivot, sumRightPivot, sumTotale, sumTarget, arrayIndex);
            }
        }
    }

    public static int pivot(double array[], int st, int sp){
        int dim=sp-st;
        if(dim<=5){
            return insertionSortMedian(array, st, sp);
        }else{
        int start=st, stop=st+5, medianPos=st;
        int iterations=dim/5;
        for(int i=0; i<iterations; i++){
            int tmp = insertionSortMedian(array, start, stop-1);
            swap(array, tmp, medianPos);
            medianPos++;
            start=stop;
            stop+=5;
         }
        if(start<dim){
            int tmp = insertionSortMedian(array, start, dim-1);
            swap(array, tmp, medianPos);
            medianPos++;
        }
        return pivot(array, st, medianPos);
        }
    }


    public static int[] threeWayPartition(double array[], int start, int stop, int pivot_idx){
        
        swap(array, pivot_idx, stop-1);
        double pivot = array[stop-1];
        int min=start, max=stop-2;
        int i=start;
        int[] positions=new int[2];
        while(i<=max){
            if(array[i]<pivot){
                swap(array, i++, min++);
            }else if(array[i]>pivot){
                swap(array, i, max--);
            }
            else{
                i++;
            }
        }
        swap(array, stop-1, ++max);
        positions[0]=min;
        positions[1]=max;
        return positions;
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

        median = (right - left) / 2;
        return left + median;
    }

    //metodo che fa lo scambio
    public static void swap(double array[], int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    //metodo che fa la somma
    public static double add(double array[], int left, int right) {
        double somma = 0;

        for (int i = left; i <= right; i++) {
            somma += array[i];
        }

        return somma;
    }


}


