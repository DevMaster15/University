;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname Esercizo8c) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define prime-factors-degs
  (lambda(n)

    (factors n 2)

    )
  )


(define factors
  (lambda (n k)

    (if(= n 1)
       null
       (if(= (modulo n k) 0)
          (append (list (lista n k 0)) (factors (/ n (expt (list-ref (lista n k 0) 0) (list-ref(lista n k 0) 1))) (+ k 1)))
          (factors n (+ k 1))
          )
       )
    ))

(define lista
  (lambda (n k e)
    (if (= (remainder n k) 0)
       (lista (/ n k) k (+ e 1))
       (list k e)
       )
    
    ))

