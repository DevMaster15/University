import com.sun.org.apache.xerces.internal.xs.StringList;
import queens.ChessboardView;

import javax.swing.plaf.SliderUI;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Board {


    private int numero; //dimensine scacchiera
    private int numeroRegine;
    private SList colonne; //lista di due colonne (c-c)
    private SList righe; //lista di due righe (r-r)
    private SList diagonaleDestra;
    private SList diagonaleSinistra;
    private String BoardConf;

    //private String COLS = "abcdefghijklmno";
    //private String ROWS = "123456789ABCDEF";


    //scacchiera vuota
    public Board(int n) {
        numero = n;
        numeroRegine = 0;
        righe = SList.lista; //lista di interi
        colonne = SList.lista; //lista di interi
        diagonaleSinistra = SList.lista; //lista di interi
        diagonaleDestra = SList.lista; //lista di inter
        BoardConf = "";

    }

    //devo dirgli dove mettere la regina
        public Board(int n, int nQ, SList listaRighe, SList listaColonne, SList listaDiagonaleSx, SList listaDiagonaleDx, String newBoard) {
        numero = n;
        numeroRegine = nQ;
        righe = listaRighe;
        colonne = listaColonne;
        diagonaleSinistra = listaDiagonaleSx;
        diagonaleDestra = listaDiagonaleDx;
        BoardConf = newBoard;


    }

    public int size() {
        int dim = numero;
        return dim;
    }

    public int queensOn() {
        return numeroRegine;

    }

    public boolean underAttack(int i, int j) //i: riga j: colonna
    {
        SList prova1 = righe;
        SList prova2 = colonne;
        SList prova3 = diagonaleSinistra;
        SList prova4 = diagonaleDestra;

        return control(i, j, prova1, prova2, prova3, prova4);

    }

    public boolean control(int i, int j, SList<Integer> r, SList<Integer> c, SList<Integer> ds, SList<Integer> dd) {
        //caso base
        if (r.isNull() && c.isNull() && ds.isNull() && dd.isNull())
            return false;


        else {

            if (i == r.car() || j == c.car() || (i + j) == ds.car() || (i - j) == dd.car())
                return true;
            else
                return control(i, j, r.cdr(), c.cdr(), ds.cdr(), dd.cdr());
        }
    }


    public Board addQueen(int i, int j) //aggiunge una nuova regina
    {
        return new Board(numero, numeroRegine + 1, righe.cons(i), colonne.cons(j), diagonaleSinistra.cons(i + j), diagonaleDestra.cons(i - j), (BoardConf + buildConf(i,j)) );
    }

    public String buildConf(int i, int j)
    {
        String si = Integer.toString(i);
        String sj= "";

        switch(j)
        {
            case 1:
                sj="a";
                break;
            case 2:
                sj="b";
                break;
            case 3:
                sj="c";
                break;
            case 4:
                sj="d";
                break;
            case 5:
                sj="e";
                break;
            case 6:
                sj="f";
                break;
            case 7:
                sj="g";
                break;
            case 8:
                sj="h";
                break;

        }

        return " " + sj + si + " ";
    }

    public String readConf()
    {
        return BoardConf;
    }
}
