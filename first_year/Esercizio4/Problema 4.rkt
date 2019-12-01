;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Problema 4|) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss" "installed-teachpacks")) #f)))
(define normalized-btr ;Restituisce la rapprsentazione BTR alla quale sono rimosse le eventuali cifre zero in testa
  (lambda (s) ;Stringa
    (if (and (> (string-length s) 1) (char=? (string-ref s 0) #\.))
        (normalized-btr (substring s 1))
        s)))

(define lsd ;Restiruisce la cifra meno significativa di una rappresentazione BTR
  (lambda (s) ; s: stringa
    (if (= (string-length s) 0)
        #\.
        (string-ref s (- (string-length s) 1)) ;restituisci l'ultimo, ovvero il meno significativo
        )))

(define head ;Restituisce la parte che precede l'ultima cifra di una rappresentazione BTR
  (lambda (s) ; s: Stringa
    (if (= (string-length s) 1)
        "."
        (substring s 0 (- (string-length s) 1)) 
        )))



(define btr-digit-sum ;Date 2 cifre BTR e il relativo riporto, restituisce il valore BTR della somma (carattere)
  (lambda (c1 c2 r) ; c1,c2,r: Caratteri
    (cond ;vari casi di somme con il relativo riporto
      ((and (char=? c1 #\+) (char=? c2 #\+) (char=? r #\+)) #\.) 
      ((and (char=? c1 #\+) (char=? c2 #\+) (char=? r #\.)) #\-)
      ((and (char=? c1 #\+) (char=? c2 #\+) (char=? r #\-)) #\+) 
      ((and (char=? c1 #\+) (char=? c2 #\.) (char=? r #\+)) #\-)
      ((and (char=? c1 #\+) (char=? c2 #\.) (char=? r #\.)) #\+)
      ((and (char=? c1 #\+) (char=? c2 #\.) (char=? r #\-)) #\.)
      ((and (char=? c1 #\+) (char=? c2 #\-) (char=? r #\+)) #\+)
      ((and (char=? c1 #\+) (char=? c2 #\-) (char=? r #\.)) #\.)
      ((and (char=? c1 #\+) (char=? c2 #\-) (char=? r #\-)) #\-)
      ((and (char=? c1 #\.) (char=? c2 #\+) (char=? r #\+)) #\-)
      ((and (char=? c1 #\.) (char=? c2 #\+) (char=? r #\.)) #\+)
      ((and (char=? c1 #\.) (char=? c2 #\+) (char=? r #\-)) #\.)
      ((and (char=? c1 #\.) (char=? c2 #\.) (char=? r #\+)) #\+)
      ((and (char=? c1 #\.) (char=? c2 #\.) (char=? r #\.)) #\.)
      ((and (char=? c1 #\.) (char=? c2 #\.) (char=? r #\-)) #\-)
      ((and (char=? c1 #\.) (char=? c2 #\-) (char=? r #\+)) #\.)
      ((and (char=? c1 #\.) (char=? c2 #\-) (char=? r #\.)) #\-)
      ((and (char=? c1 #\.) (char=? c2 #\-) (char=? r #\-)) #\+)
      ((and (char=? c1 #\-) (char=? c2 #\+) (char=? r #\+)) #\+)
      ((and (char=? c1 #\-) (char=? c2 #\+) (char=? r #\.)) #\.)
      ((and (char=? c1 #\-) (char=? c2 #\+) (char=? r #\-)) #\-)
      ((and (char=? c1 #\-) (char=? c2 #\.) (char=? r #\+)) #\.)
      ((and (char=? c1 #\-) (char=? c2 #\.) (char=? r #\.)) #\-)
      ((and (char=? c1 #\-) (char=? c2 #\.) (char=? r #\-)) #\+)
      ((and (char=? c1 #\-) (char=? c2 #\-) (char=? r #\+)) #\-)
      ((and (char=? c1 #\-) (char=? c2 #\-) (char=? r #\.)) #\+)
      (else ("."))
    )
  )
)

(define btr-carry ;Date 2 cifre BTR e un riporto in entrata, restituisce il riporto BTR in uscita (carattere)
  (lambda (c1 c2 r) ; c1,c2,r: Caratteri
    (cond
     ((and (char=? c1 #\+) (char=? c2 #\+) (char=? r #\+)) #\+)
     ((and (char=? c1 #\+) (char=? c2 #\+) (char=? r #\.)) #\+)
     ((and (char=? c1 #\+) (char=? c2 #\.) (char=? r #\+)) #\+)
     ((and (char=? c1 #\.) (char=? c2 #\+) (char=? r #\+)) #\+)
     ((and (char=? c1 #\.) (char=? c2 #\-) (char=? r #\-)) #\-)
     ((and (char=? c1 #\-) (char=? c2 #\.) (char=? r #\-)) #\-)
     ((and (char=? c1 #\-) (char=? c2 #\-) (char=? r #\.)) #\-)
     ((and (char=? c1 #\-) (char=? c2 #\-) (char=? r #\-)) #\-)
     (else #\.)
    )
  )
)


(define btr-sum ;Ritorna una stringa che rappresenta il risultato della somma di due numeri in rappresentazione ternaria bilanciata
  (lambda (c1 c2) ; c1,c2; Stringhe
    (let ((cc1 (normalized-btr c1)) (cc2 (normalized-btr c2))) ;gli tolgo tutti gli 0 davanti
    (normalized-btr (btr-carry-sum cc1 cc2 #\.)) ;tolgo tutti gli 0 davanti al risultato
    )))

(define btr-carry-sum ;Ritorna sottoforma di stringa la somma di due stringhe in notazione BTR e un resto 
  (lambda (c1 c2 r) ;c1,c2 :Stringhe r: carattere
    (if (and (= (string-length c1) 1) (= (string-length c2) 1))
        (string (btr-carry (lsd c1) (lsd c2) r) (btr-digit-sum (lsd c1) (lsd c2) r))
        (string-append (btr-carry-sum (head c1) (head c2) (btr-carry (lsd c1) (lsd c2) r)) (string (btr-digit-sum (lsd c1) (lsd c2) r))) ;unisci la ricorsione con il substring delle cifre ed il riporto in uscita dato dall'ultima cifra delle stringhe
        ))
  )




