;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname Esercizio8a) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define prime-factors
  (lambda(n)
    
  
    (factors n 2)

    ))


(define factors
  (lambda(n k)

    (if(= n k)
       (list k)

       (if(= (modulo n k) 0)
           
                
          (append (list k) (factors (/ n k) k))
          (factors n (+ k 1))
          )
       )
    )
  )
    

(prime-factors 540)