import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Project {

    //questo è il progetto
    public static void main(String args[]) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        System.out.println("Start");

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
        
        
        double result = weightedMedian(array); //memorizzo il risultato finale nella variabile risultatoMaestro

        System.out.println("Risultato = " + result);

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