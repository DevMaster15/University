import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;

public class progetto{

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
        double arrayFinale[] = new double[str.length-1];
        //converto uno ad uno gli elementi della stringa in elementi di tipo double
        //inserisco tali elementi nell'array
        for (int i = 0; i < str.length - 1; i++) {
            array[i] = Double.valueOf(str[i]);
        }

        int result = start(array);
        System.out.println("result = " + array[result]);

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


    /*
        metodo principale in cui si richiamano le varie procedure necessarie alla risoluzione del problema
        restituisce il risultato oppure richiama se stessa ricorsivamente spostandosi a sinistra o destra
        dell'array

    */
    private static int findWk(double array[], int left, int right, double sumCarry, double sumTarget){

        int arrayIndex[] = new int[2];

        if(left < right){
            int med = (right + left)/2; //indice elemento mediano

            int index = select(array, left, right, med); //index contiene l'indice del mediano reale
            arrayIndex = findIndex(array, index, right);
            double sum =  add(array, left, arrayIndex[0]);  //somma fino ad index
           

            if(sum + sumCarry < sumTarget){ //l'elemento si trova a destra del vettore
                
                left = arrayIndex[0] + 1;
                sumCarry = sumCarry + sum;

                return findWk(array, left, right, sumCarry, sumTarget);

            } else if(sum + sumCarry > sumTarget){  //l'elemento si trova a sinistra

                right = arrayIndex[0];
        
               
                return findWk(array, left, right, sumCarry, sumTarget);

            } else  return arrayIndex[0];
        }

        return left;    //se nessuna delle precedenti condizioni si è verificata significa che c'è un solo elemento
       
    }

    /*
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
