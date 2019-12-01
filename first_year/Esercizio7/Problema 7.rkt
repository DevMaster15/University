;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Problema 7|) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss" "installed-teachpacks")) #f)))
(define manhattan-3d; Restituisce il numero di percorsi diversi che consentono di raggiungere il punto di coordinate y,z
  (lambda (x y z); x,y,z: interi
    (cond ((and (= x 0) (= y 0) (= z 0))
           1)
          (else
           (+ 
              (if (> x 0)
                  (manhattan-3d (- x 1) y z) 0)
              (if (> y 0)
                  (manhattan-3d x (- y 1) z) 0)
              (if (> z 0)
                  (manhattan-3d x y (- z 1)) 0)
              )
           )
          )
    ))



(define prob-3d2 ;Restituisce la probabilità che una pallina scendendo di x livelli raggiunga il punto di coordinate y, z
  (lambda (x y z) ; x,y,z: interi
    (/ (manhattan-3d (- x (+ y z)) y z) (all x y z))
    )
  )
    
(define all ;Restituisce il numero complessivo di percorsi possibili
  (lambda (x y z) ; x,y,z: interi
    (cond ((= x 0) 1)
          ( else (* 3 (all (- x 1) y z)))
          )
    )
  )

(define prob-3d1 ;Restituisce la probabilità che una pallina scendendo di n livelli raggiunga il punto di coordinate i,j
  (lambda (n i j)  ;n livelli, i rossi, j verdi
        (cond
          ((and (> n (+ i j)) (not (= i 0)) (not (= j 0))) (+ (* 1/3 (prob-3d1 (- n 1) i j)) (* 1/3 (prob-3d1 (- n 1) (- i 1) j)) (* 1/3 (prob-3d1 (- n 1) i (- j 1))) ))
          ((and (> n (+ i j)) (not (= i 0))) (+ (* 1/3 (prob-3d1 (- n 1) i j)) (* 1/3 (prob-3d1 (- n 1) (- i 1) j))))
          ((and (> n (+ i j)) (not (= j 0))) (+ (* 1/3 (prob-3d1 (- n 1) i j)) (* 1/3 (prob-3d1 (- n 1) i (- j 1)))))
          ((> n (+ i j)) (* 1/3(prob-3d1 (- n 1) i j)))
          ((and (not (= i 0)) (not (= j 0))) (+ (* 1/3 (prob-3d1 (- n 1) i (- j 1))) (* 1/3 (prob-3d1 (- n 1) (- i 1) j))))
          ((not (= i 0)) (* 1/3 (prob-3d1 (- n 1) (- i 1) j)))
          ((not (= j 0)) (* 1/3 (prob-3d1 (- n 1) i (- j 1))))
          (else 1)
        )
  )
)



      

        