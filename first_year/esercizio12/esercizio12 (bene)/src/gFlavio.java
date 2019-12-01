import java.util.Scanner;

public class gFlavio {


    public static void main (String[] args){
        int numero;
        int ris;

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Inserisci il numero dei cavalieri: ");
        numero = keyboard.nextInt();

        ris = gFlavio(numero);
        System.out.println("Il risultato e': " + ris);


    }

    public static int gFlavio(int n) { //n > 0

        return gFlavioRec(new TavolaRotonda(n));
    }

    public static int gFlavioRec(TavolaRotonda t) {
        if (t.numberOfKnightsIn() == 1) {
            return t.knightWithJugIn();
        } else {
            return gFlavioRec(t.afterNextKnightQuits());
        }
    }
}



