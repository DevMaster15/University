import queens.*;

import java.util.Scanner;

public class Queens {


    public static final SList<Board> NULL_BOARDLIST = new SList<Board>();


    /*
     * I. Numero di soluzioni:
     *
     *
     * Il numero di modi diversi in cui si possono disporre n regine
     *
     *   numberOfSolutions( n )
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

    private static int numberOfCompletions( Board b ) {

        int n = b.size();
        int q = b.queensOn();

        if ( q == n ) {

            return 1;

        } else {

            int i = q + 1;
            int count = 0;

            for ( int j=1; j<=n; j=j+1 ) {
                if ( !b.underAttack(i,j) ) {

                    count = count + numberOfCompletions( b.addQueen(i,j) );
                }}
            return count;
        }
    }


    /*
     * II. Lista delle soluzioni:
     *
     * Confronta il programma precedente!
     */

    public static SList<Board> listOfAllSolutions( int n, ChessboardView view ) {

        return listOfAllCompletions( new Board(n), view);
    }


    private static SList<Board> listOfAllCompletions( Board b, ChessboardView view) {

        int n = b.size(); //dimensione scacchiera
        int q = b.queensOn(); //numero regine sulla scacchiera

        if ( q == n ) {

            view.setQueens(b.readConf());
            return ( NULL_BOARDLIST.cons(b) );




        } else {

            int i = q + 1;
            SList<Board> solutions = NULL_BOARDLIST;

            for ( int j=1; j<=n; j=j+1 ) {
                if ( !b.underAttack(i,j) ) {

                    solutions = solutions.append( listOfAllCompletions(b.addQueen(i,j), view));

                }
            }

            return solutions;
        }
    }




    // Eventuale programma principale

    public static void main( String args[] ) {

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Inserisci la dimensione della scacchiera: ");
        int n = keyboard.nextInt();

        ChessboardView view = new ChessboardView(n);

        System.out.println( numberOfSolutions(n) );
        System.out.println( listOfAllSolutions(n, view) );

    }




}  // class Queens


