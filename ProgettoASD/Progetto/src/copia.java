import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;

public class copia {

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

        
        double sommaTotale = add(array, 0, array.length - 1);   //calcolo la somma di tutti gli elementi del vettore
        int arrayIndex[] = new int[2];
        int left_idx = 0;
        int right_idx = array.length - 1;
        int med = (right_idx - left_idx)/2; //indice elemento centrale
       

        int index = select(array, left_idx, right_idx, med);
        arrayIndex = findIndex(array, index, right_idx);
        
        double sumLeftPivot = add(array, 0, arrayIndex[0] - 1);
        double sumRightPivot = add(array, arrayIndex[1] + 1, right_idx);
        
        int result = principale(array, left_idx, right_idx,sumLeftPivot, sumRightPivot, sommaTotale, sommaTotale/2, arrayIndex);
       
        System.out.println("result = " + array[result]);

    }

    private static int principale(double array[], int left, int right, double sumLeftPivot, double sumRightPivot, double sumTotale, double sumTarget, int arrayIndex[]){

        if(sumLeftPivot < sumTarget && sumRightPivot <= sumTarget)  return arrayIndex[0];

        else{
            if(sumLeftPivot >= sumTarget){
                int tmpRight = arrayIndex[0];
                int med = left + (tmpRight-left)/2;
                int index = select(array, left, tmpRight, med);
                arrayIndex = findIndex(array, index, tmpRight);
                sumRightPivot = sumRightPivot + add(array, arrayIndex[1] + 1, tmpRight); //ricalcolo le somme
                sumLeftPivot = sumTotale - sumRightPivot - add(array, arrayIndex[0], arrayIndex[1]);
                return principale(array, left, tmpRight, sumLeftPivot, sumRightPivot, sumTotale, sumTarget, arrayIndex);
            } 
            else {
                int tmpLeft = arrayIndex[1];
                int med = tmpLeft + (right - tmpLeft)/2;
                int index = select(array, tmpLeft, right, med);
                arrayIndex = findIndex(array, index, right);
                sumLeftPivot = sumLeftPivot + add(array, tmpLeft, arrayIndex[0] - 1); //ricalcolo le somme
                sumRightPivot = sumTotale - sumLeftPivot - add(array, arrayIndex[0], arrayIndex[1]);
                return principale(array, tmpLeft, right, sumLeftPivot, sumRightPivot, sumTotale, sumTarget, arrayIndex);
            }
        }
    }


    private static int select(double array[], int left, int right, int num){

        int arrayIndex[] = new int[2];
        
        if(right == left)   return left; 

        int pivot_idx = pivot(array, left, right); //torna la posizione del mediano
        
        arrayIndex = threeWayPartition(array, left, right, pivot_idx);  //partiziono l'array e memorizzo nell'arrayIndex l'indice della prima occorenza di pivot e dell'ultima

        if(arrayIndex[0] == num)    return arrayIndex[0]; 

        else {

            if(arrayIndex[0] > num)    return select(array, left, arrayIndex[0], num);

                else 
                    if(arrayIndex[0] < num)    return select(array, arrayIndex[0] + 1  , right, num);

                        else    return -1;
        }
    }

    //procedura che divide l'array in blocchi da 5
    public static int pivot(double array[], int left, int right){
        int dim=right-left+1;

        if(dim<=5){
            return insertionSortMedian(array, left, right);
        }else{

        int start=left;
        int stop=left+5;
        int medianPos=left;
        int iterations=dim/5; //quanti gruppi da 5 ci saranno, quindi quante iterazioni


        for(int i=0; i<iterations; i++){
            int tmpMed = insertionSortMedian(array, start, stop-1); //ordino e prendo la mediana
           
            swap(array, tmpMed, medianPos); //scambio con gli elementi iniziali
            medianPos++;
            start=stop;
            stop+=5;
        }

        if(start<dim){
            int tmpMed = insertionSortMedian(array, start, dim-1);
            swap(array, tmpMed, medianPos);
            medianPos++;
        }

        medianPos--;
        int mid = left + (medianPos - left)/2;

        return select(array, left, medianPos, mid);
        }
    }


    
        
    public static int[] threeWayPartition(double array[], int start, int stop, int pivot_idx){


        swap(array,pivot_idx, stop);
        double pivot=array[stop];
        int min=start, max=stop-1;
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
        swap(array, stop, ++max);
        
        positions[0] = min;
        positions[1] = max;
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
