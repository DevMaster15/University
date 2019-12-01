public class Board {

    private int numero; //dimensine scacchiera
    private int numeroRegine;
    private SList<Integer> colonne; //lista di due colonne (c-c)
    private SList<Integer> righe; //lista di due righe (r-r)
    private SList<Integer> diagonaleDestra;
    private SList<Integer> diagonaleSinistra;
    private SList<String> config;
    private static final String ROWS = "123456789ABCDEF";
    private static final String COLS = "abcdefghijklmno";




    //scacchiera vuota
    public Board(int n) {
        numero = n;
        numeroRegine = 0;
        righe = SList.NULL_LIST;
        colonne = SList.NULL_LIST;
        diagonaleSinistra = SList.NULL_LIST;
        diagonaleDestra = SList.NULL_LIST;
        config = SList.NULL_STRING;


    }

    //devo dirgli dove mettere la regina
    public Board(int n, int nQ, SList<Integer> listaRighe, SList<Integer> listaColonne, SList<Integer> listaDiagonaleSx, SList<Integer> listaDiagonaleDx, SList<String> newBoard) {
        numero = n;
        numeroRegine = nQ;
        righe = listaRighe;
        colonne = listaColonne;
        diagonaleSinistra = listaDiagonaleSx;
        diagonaleDestra = listaDiagonaleDx;
        config = newBoard;
        arrangiament();

    }

    public Board(int n, int nQ, SList<Integer> r, SList<Integer> c, SList<Integer> Dx, SList<Integer> Sx, SList<String> a, String s)
    {
        numero = n;
        numeroRegine = nQ;
        righe = r;
        colonne = c;
        diagonaleSinistra = Sx;
        diagonaleDestra = Dx;
        config = a;
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
        SList<Integer> prova1 = righe;
        SList<Integer> prova2 = colonne;
        SList<Integer> prova3 = diagonaleSinistra;
        SList<Integer> prova4 = diagonaleDestra;

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


        return new Board(numero, numeroRegine + 1, righe.cons(i), colonne.cons(j), diagonaleSinistra.cons(i + j), diagonaleDestra.cons(i - j), config);
    }


    public Board arrangiament(){

        String alfabeto[] = {"a","b","c","d","e","f","g","h","i","l","m","n","o","p","q","r","s","m"}; //vettore di stringhe con le lettere dell'alfabeto
        StringBuilder str = new StringBuilder (); //dichiar un oggetto stringBuilder

        for (int i = 0; i < numeroRegine; i++) { //in questo ci metto per quanto riguarda le colonne la lettera corrispondente al numeo della colonna
            str.append(alfabeto[colonne.listRef(i)-1] + (righe.listRef(i)) + " "); //per quanto riguarda le righe il numero della riga
        }

        String sout = str.toString(); //trasformo StringBuilder in una stringa normale e la "appendo" alla lista di stringhe confi dichiarato all'inizio
        SList<String> t = config.append(SList.NULL_STRING.cons(sout)); //do il suo valore ad una nuova lista di stringhe

        System.out.println(sout);
        System.out.println(config);
        return new Board(numero, numeroRegine, righe, colonne, diagonaleSinistra, diagonaleDestra, t, sout); //ritorno la nuova disposizione al terzo costruttore


    }





}
