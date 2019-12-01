
import java.util.Scanner;

public class BaTeRe
{
    public static void main(String[] args)
    {
        String btr;
        int n;
        StringSList tot; //tot -> lista
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Inserisci la stringa: ");
        btr = keyboard.next();

        System.out.print("Inserisci il valore numerico: ");
        n = keyboard.nextInt();

        tot = proc(btr, n-1).cons(btr);
        System.out.println(tot.toString());



    }

    public static StringSList proc(String val, int n)
    {

        String nuovoValore = funz(val);

        if(n == 0)

            return StringSList.lista; //se n = 0 ritorna la lista

        else
        {
            return proc(nuovoValore, n-1).cons(nuovoValore);

        }
    }

    public static String funz(String val)
    {
        int lunghezza = val.length();

        char ultimoCarattere = val.charAt(lunghezza - 1);

        //System.out.println(val);

        if( lunghezza == 1)
        {
            if(ultimoCarattere == '+')
                val = "+-";

            else val = "+";
        }

        else
        {
            {
                if (ultimoCarattere == '+') {
                    val = funz(val.substring(0, lunghezza - 1)) + "-";

                }
                else {
                    if(ultimoCarattere == '-')
                    {
                        val = val.substring(0, lunghezza - 1) + ".";
                    }

                    if(ultimoCarattere == '.')
                    {
                        val = val.substring(0, lunghezza - 1) + "+";
                    }
                }

            }

        }


        return val;
    }
}
