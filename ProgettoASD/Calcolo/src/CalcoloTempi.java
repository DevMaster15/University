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
        System.out.println("Tempo: " + result * 1 / 1000 + " secondi.");




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
            
            d[i] = pseudoRandom();
            
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

   
   private static int start(double array[]){
        
        double sommaTotale = add(array, 0, array.length - 1);
        if(sommaTotale == 0) //se uguale a 0 significa che ho tutti 0
            return 0;

        else{
        double sumTarget = sommaTotale/2;
        double sumCarry = 0; //somma riporto
        return findWk(array, 0, array.length - 1, sumCarry, sumTarget);
        
        }
    }

    private static int findWk(double array[], int left, int right, double sumCarry, double sumTarget){

        int arrayIndex[] = new int[2];

        if(left < right){
            int med = (right + left)/2;
            int index = select(array, left, right, med);
            arrayIndex = findIndex(array, index, right);
            double sum =  add(array, left, arrayIndex[0]);
           

            if(sum + sumCarry < sumTarget){
                
                left = arrayIndex[0] + 1;
                sumCarry = sumCarry + sum;

                return findWk(array, left, right, sumCarry, sumTarget);

            } else if(sum + sumCarry > sumTarget){

                right = arrayIndex[0];
        
               
                return findWk(array, left, right, sumCarry, sumTarget);

            } else  return arrayIndex[0];
        }

        return left;
       
    }
    /**
        ritorna l'elemento il num-esimo elemento più piccolo. In questo caso num = (right - left)/2
     */
    private static int select(double array[], int left, int right, int num){

        int arrayIndex[] = new int[2];
        
        if(right == left)   return left; 

        int pivot_idx = mediansOfMedians(array, left, right); //torna la posizione del mediano
        
        arrayIndex = threeWayPartition(array, left, right, pivot_idx);  //partiziono l'array e memorizzo nell'arrayIndex l'indice della prima occorenza di pivot e dell'ultima

        if(arrayIndex[0] == num)    return arrayIndex[0]; 

        else {

           if(arrayIndex[0] > num)    return select(array, left, arrayIndex[0], num);

                else 
                    if(arrayIndex[0] < num)    return select(array, arrayIndex[0] + 1 , right, num);

                        else    return -1;
        }
    }

    //calcola la mediana delle mediane dividendo l'array in n/5 blocchi da al massimo 5 elementi ciascuno
    private static int mediansOfMedians(double[] array, int left, int right) {
        if ((right - left) <= 5) {  //se ci sono meno di 5 elementi calcola la mediana direttamente
	    return insertionSortMedian(array, left, right);
        }
        
        int count = left;   //contatore per spostare le mediane all'inizio dell'array

        for (int i = left; i <= right; i = i + 5) {
            int tmpRight = i + 4;		

	    if (tmpRight > right) { //controllo se ci sono meno di 5 elementi
                tmpRight = right;
	    }

	    int median;
	    median = insertionSortMedian(array, i, tmpRight);  //indice mediana
	    swap(array, median, count); //sposto la mediana all'inizio dell'array
	    count++;    //incremento contatore per mediana

        }

        count--;
        int med = left + (count - left)/2;
        return select(array, left, count, med);		
    }

   
    /**
        Metodo che ritorna l'array partizionato in 3 parti:
            - elementi minori   perno
            - elementi uguali   perno
            - elementi maggiori perno
        
     */
    public static int[] threeWayPartition(double array[], int start, int stop, int pivot_idx){


        swap(array,pivot_idx, stop);

        double pivot=array[stop];
        int min=start, max=stop-1;
        int i=start;
        int[] positions=new int[2]; //array per posizione iniziale e finale perno
                                    //se non ci sono elementi uguali avrà lo stesso valore

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

        swap(array, stop, ++max);
        
        positions[0] = min;
        positions[1] = max;

        return positions;

    }

    /**
        metodo che ordina l'array che viene passato come argomento. ritorna l'elemento centrale
     */
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

        median = ((right - left) / 2);
     
        return left + median; //elemento mediano
        
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

    /**
        metodo che dato un array e un indice di un elemento, conta l'intervallo di elementi uguali 
        a quello corrispondente a quell'indice
     */
    private static int[] findIndex(double array[], int index, int right){ //left è il valore da cui parte l'index

        int arrayIndex[] = new int[2];
        int x = index;
        
        while(x < right && array[x+1] == array[index]){
            x++;
        }

        arrayIndex[0] = index;
        arrayIndex[1] = x;
        return arrayIndex;
    }

    private static void printArray(double array[], int left, int right){
        for(int i=0;i<=right;i++){
            System.out.println("array["+i+"] = " + array[i]);
        }
    }

}

