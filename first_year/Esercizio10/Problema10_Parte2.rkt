;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname Problema10_Parte2) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define H
  (λ (i s)
    (λ (m n)
      (if(= n 0)
         (i m) ;(lambda(x) x) -> (lambda(m) m) -> stampa il risultato
         (s m ((H i s) m (- n 1)))
         )
      )
    )
  )

(define s2
  (λ (m n)
    (+ n 1)
    
    )
  )

(define add
  (H (λ(x) x)
     s2))

(define mul
  (H (λ(x) 0)
     add))

(define pow
  (H (λ(x) 1)
     mul))

(add 4 3)