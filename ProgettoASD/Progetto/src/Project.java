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

        double risultato = start(array); //memorizzo il risultato finale nella variabile risultatoMaestro

        System.out.println("Risultato = " + risultato);

    }

    //il programma parte da qua, calcolando per la prima volta tutti i valori
    public static double start(double array[]){
        int left = 0;
        int right = array.length - 1;
        int array_idx[] = new int[2];
        array_idx = threeWayPartition(array, left, right); //mi ritorna un array di due posizioni

        //calcolo le varie somme
        double sommaLeftPivot = add(array, 0, array_idx[0] - 1);
        double sommaRightPivot = add(array, array_idx[1] + 1, right );
        double sommaTotale = add(array, left, right); //modificato qua la somma

        //se la sommaTotale = 0 signfica che tutti gli elementi sono = 0, visto che non ci possono essere elementi negativi
        if(sommaTotale == 0)
            return 0;

        //richiamo weightedMedian con questi valori, e controllo se la condizione i base si verifica
        //se si verifica il problema è stato risolto e restituisco il valore
        //sennò mi sposto intorno al vettore in cerca del risultato
        return weightedMedian(array, left, right, sommaLeftPivot, sommaRightPivot, sommaTotale, sommaTotale/2, array_idx);
    }


    //metodo "cuore" del sistema
    public static double weightedMedian(double array[], int left, int right, double sommaLeftPivot,
                                        double sommaRightPivot, double sommaTotale, double sommaMezzi, int arrayIndici[]) {



        if (sommaLeftPivot < sommaMezzi && sommaRightPivot <= sommaMezzi) //controllo se la condizione è verificata, se lo è restituisco il valore
            return array[arrayIndici[0]];

        else {
            if (sommaLeftPivot >= sommaMezzi) { //se non lo è controllo se l'elemento si trova nella parte sinistra
                //se si trova a sinistra mi sposto, e devo escludere la parte destra dal resto dei controlli

                int tmpright = arrayIndici[0]; //tmpRight sarà il nuovo right, visto che devo escludere la parte a destra del pivot

                arrayIndici = threeWayPartition(array, left, tmpright);

                sommaRightPivot = sommaRightPivot + add(array, arrayIndici[1] + 1, tmpright); //ricalcolo le somme
                sommaLeftPivot = add(array, left, arrayIndici[0] - 1);


                //faccio la ricorsione passando i nuovi valori ed il "nuovo" sotto array con indici left e tmpRight
                return weightedMedian(array, left, tmpright, sommaLeftPivot, sommaRightPivot, sommaTotale, sommaMezzi, arrayIndici);

            } else {
                //se non si trova a sinistra vuol dire che si trova a destra
                //quindi mi sposto a destra e ricalcolo i valori escludendno la parte sinistra dal resto dei controlli

                int tmpleft = arrayIndici[1]; //tmpLeft sarà il nuovo left, visto che devo escludere la parte a sinistra del pivot

                arrayIndici = threeWayPartition(array, tmpleft, right);

                sommaLeftPivot = sommaLeftPivot + add(array, tmpleft, arrayIndici[0] - 1); //ricalcolo le somme
                sommaRightPivot = add(array, arrayIndici[1]+1, right);

                //faccio la ricorsione passando i nuovi valori ed il "nuovo" sotto array con indici temLeft e right
                return weightedMedian(array, tmpleft, right, sommaLeftPivot, sommaRightPivot, sommaTotale, sommaMezzi, arrayIndici);
            }
        }
    }

    public static int[] threeWayPartition(double array[], int start, int stop){
        double perno=array[stop-1], aux;
        int min=start, max=stop-2;
        int i=start;
        int[] posizioni=new int[2];
        while(i<=max){
            if(array[i]<perno){
                swap(array, i++, min++);
            }else if(array[i]>perno){
                swap(array, i, max--);
            }
            else{
                i++;
            }
        }
        swap(array, stop-1, ++max);
        posizioni[0]=min;
        posizioni[1]=max;
        return posizioni;
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






