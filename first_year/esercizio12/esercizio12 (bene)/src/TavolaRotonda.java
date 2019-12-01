public class TavolaRotonda {

    private IntSList tav;
    private int num;

    public TavolaRotonda(int n)
    {
        tav = range(1, n);
    }

    private TavolaRotonda(IntSList il)
    {
        tav = il;
    }

    public TavolaRotonda afterNextKnightQuits (){
        //dichiaro la variabile lista il
        IntSList il = (tav.cdr().cdr()).append(IntSList.NULL_INTLIST.cons(tav.car()));
        return new TavolaRotonda (il);
    }

    public int knightWithJugIn() {

        return tav.car();
    }

    public int numberOfKnightsIn()
    {
        return tav.length();

    }


    private static IntSList range(int inf, int sup) {
        if (inf > sup) {
            return (IntSList.NULL_INTLIST);
        } else {
            return range(inf + 1, sup).cons(inf);
        }
    }
}


