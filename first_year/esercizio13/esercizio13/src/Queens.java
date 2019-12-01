/*
 * Rompicapo delle "n regine"
 *
 * Ultimo aggiornamento: 12/04/2018
 *
 *
 * Dato astratto "configurazione della scacchiera":  Board
 *
 * Operazioni:
 *
 *   new Board( int n )           :  costruttore (scacchiera vuota)
 *
 *   size()                       :  int
 *
 *   queensOn()                   :  int
 *
 *   underAttack( int i, int j )  :  boolean
 *
 *   addQueen( int i, int j )     :  Board
 *
 *   arrangement()                :  String
 *
 *
 * Board b;
 *
 *   new Board(n)           costruttore della scacchiera n x n vuota;
 *   b.size()               dimensione n della scacchiera b;
 *   b.queensOn()           numero di regine collocate nella scacchiera b;
 *   b.underAttack(i,j)     la posizione <i,j> e' minacciata?
 *   b.addQueen(i,j)        scacchiera che si ottiene dalla configurazione b
 *                          aggiungendo una nuova regina in posizione <i,j>
 *                          (si assume che la posizione non sia minacciata);
 *   b.arrangement() :      descrizione "esterna" della configurazione
 *                          (convenzioni scacchistiche).
 */


import java.util.Scanner;

public class Queens {


    /*
     * I. Numero di soluzioni:
     *
     *
     * Il numero di modi diversi in cui si possono disporre n regine
     *
     *   numberOfSolutions( n )
     *
     * in una scacchiera n x n e' dato dal numero di modi diversi in
     * cui si puo' completare la disposizione delle regine a partire
     * da una scacchiera n x n inizialmente vuota
     *
     *   numberOfCompletions( new Board(n) )
     */

    public static int numberOfSolutions( int n ) {

        return numberOfCompletions( new Board(n) );
    }


    /*
     * Il numero di modi in cui si puo' completare la disposizione
     * a partire da una scacchiera b parzialmente configurata
     *
     *   numberOfCompletions( b )   : int
     *
     * dove k regine (0 <= k < n) sono collocate nelle prime k righe
     * di b, si puo' determinare a partire dalle configurazioni
     * che si ottengono aggiungendo una regina nella riga k+1 in tutti
     * i modi possibili (nelle posizioni che non sono gia' minacciate)
     *
     *   for ( int j=1; j<=n; j=j+1 ) {
     *     if ( !b.underAttack(i,j) ) { ... b.addQueen(i,j) ... }
     *   }
     *
     * calcolando ricorsivamente per ciascuna di queste il numero
     * di modi in cui si puo' completare la disposizione
     *
     *   numberOfCompletions( b.addQueen(i,j) )
     *
     * e sommando i valori che ne risultano
     *
     *   count = count + numberOfCompletions( ... )
     *
     * Se invece la scacchiera rappresenta una soluzione (q == n)
     * c'e' un solo modo (banale) di completare la disposizione:
     * lasciare le cose come stanno!
     */


    //procedura che ha come input una scacchiera vuota
    private static int numberOfCompletions( Board b ) {

        int n = b.size(); //dimensione della scacchiera

        int q = b.queensOn(); //numero di regine sulla scacchiera





        if ( q == n ) {

            return 1;



        } else {

            int i = q + 1; //valore riga
            int count = 0;

            for ( int j=1; j<=n; j=j+1 ) //j: valore colonna
            {
                if ( !b.underAttack(i, j)) //se non è sottoattacco esegui l'operazione all'interno dell'if
                {
                    count = count + numberOfCompletions( b.addQueen(i,j) ); //addQueen(i,j) scacchiera aggiungendo una nuova regina
                }

            }


            return count;



        }



    }

    public static void main( String args[] )
    {

        Scanner keyboard = new Scanner(System.in);

        System.out.print("Inserisci la dimensione della scacchiera: ");
        int n = keyboard.nextInt();


        System.out.println( "Il numero delle soluzioni e': " + numberOfSolutions(n) );



        }



}  // class Queens
