;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Problema 6|) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss" "installed-teachpacks")) #f)))


(set-tessellation-shift-step!)

(define L-tessellation ;Data la lunghezza del lato più corto della regione da coprire restituisce l'immagine della regione tassellata
  (lambda (x) ;x: intero (potenza di 2)
    (if (= x 1)
        L-tile ;se x = 1 restituisci solamente la piastrella
        ;else
        (glue-tiles (shift-down (shift-right(L-tessellation (/ x 2)) (/ x 2)) (/ x 2))
      (glue-tiles (shift-down (quarter-turn-left (L-tessellation (/ x 2))) x)
                  (glue-tiles (shift-right (quarter-turn-right (L-tessellation (/ x 2))) x) (L-tessellation(/ x 2)))))
      )))
   
                                                                                                                                                  




