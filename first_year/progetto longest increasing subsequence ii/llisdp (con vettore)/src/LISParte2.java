public class LISParte2 {


    public static void main(String[] args)
    {
        System.out.print(Bottom_up(new  int[]{47, 38, 39, 25, 44}));
    }

    private static int Bottom_up(int[] v) {

        int T[] = new int[v.length];
        
        for(int i=0;i<v.length;i++)
        {
            T[i] = 1;

        }
        for(int i = 1;i<v.length;i++)
            for(int j = 0;j<i;j++)
            {
                if(v[i] > v[j])
                {
                    if(T[j]+1 > T[i])
                    {
                        T[i] = T[j]+1;
                        }
                    }
            }

            //cerco la posizione nel vettore con il valore maggiore ed infine stampo il valore relativo a tale posizione
        int maxIndex = 0;

        for(int i=0;i<v.length;i++)
        {
            if(T[i] > T[maxIndex])
            {
                maxIndex = i;
            }
        }

        return T[maxIndex];
    }



}
