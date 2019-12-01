    public class StringSList {

            public static final StringSList lista = new StringSList();


            boolean empty;
            String first;
            StringSList resto;

            public StringSList()
            {
                empty = true;
                first = " ";
                resto = null;
            }

            public StringSList(String s, StringSList li)
            {
                empty = false;
                first = s;
                resto = li;
            }

            //Lista vuota
            public boolean isNull()
            {
                return empty; //dice se la lista è vuota o no
            }

            public String car() //ritorna il primo elemento della lista che è una stringa
            {
                return first;
            }

            public StringSList cdr()
            {
                return resto;
            }

            public int length()
            {
                if(isNull()) //se è true ritorna la lista vuota
                    return 0;
                else
                    return lista.length();
            }

            public StringSList cons(String e)
            {
                    return new StringSList(e, this);

            }

            public String listRef(int k) //k: indice
            {
                if(k == 0)
                    return car();
                else
                    return cdr().listRef(k-1);
            }

            public boolean equals(StringSList sl)
            {
                if ( isNull() || sl.isNull() ) //se una delle due lista è vuota
                {
                    return ( isNull() && sl.isNull() ); //ritorna entrambe le liste

                } else
                    if ( car() == sl.car() ) { //sennò controlla il primo elemento di entrambe

                    return cdr().equals( sl.cdr() ); //fai la ricorsione controllando sempre il primo elemento

                } else {
                    return false;
                }


            }

            public StringSList append(StringSList sl)
            {
                if(isNull())
                {
                    return sl;
                }

                else
                    return cdr().append(sl).cons(car()); //fai il car e lo unisci alla lista sl dopo di chè fai il cdr

            }

            public StringSList reverse()
            {
                return (new StringSList());
            }

            public StringSList revRec(StringSList r)
            {
                if(isNull())
                    return r;
                else
                    return cdr().revRec(cons(car()));

            }


        public String toString() {               // ridefinizione del metodo generale
            // per la visualizzazione testuale
            if ( empty ) {
                return "()";
            } else if ( resto.isNull() ) {
                return "(" + first + ")";
            } else {
                String rep = "(" + first;
                StringSList r = resto;
                while ( !r.isNull() ) {
                    rep = rep + ", " + r.car();
                    r = r.cdr();
                }
                return ( rep + ")" );
            }
        }


    }
