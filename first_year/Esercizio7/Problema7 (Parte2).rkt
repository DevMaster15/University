;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Problema7 (Parte2)|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define prob-3d1 ;Restituisce la probabilità che una pallina scendendo di n livelli raggiunga il punto di coordinate i,j
  (lambda (n i j)  ;n livelli, i rossi, j verdi
        (cond
          ((and (> n (+ i j)) (not (= i 0)) (not (= j 0))) (+ (* 1/3 (prob-3d1 (- n 1) i j)) (* 1/3 (prob-3d1 (- n 1) (- i 1) j)) (* 1/3 (prob-3d1 (- n 1) i (- j 1))) ))
          ((and (> n (+ i j)) (not (= i 0))) (+ (* 1/3 (prob-3d1 (- n 1) i j)) (* 1/3 (prob-3d1 (- n 1) (- i 1) j)))) ;i non deve essere 0 però j può esserlo
          ((and (> n (+ i j)) (not (= j 0))) (+ (* 1/3 (prob-3d1 (- n 1) i j)) (* 1/3 (prob-3d1 (- n 1) i (- j 1))))) ;j non deve essere 0 però i può esserlo
          ((> n (+ i j)) (* 1/3(prob-3d1 (- n 1) i j))) ;i e j possono anche essere 0
          ((and (not (= i 0)) (not (= j 0))) (+ (* 1/3 (prob-3d1 (- n 1) i (- j 1))) (* 1/3 (prob-3d1 (- n 1) (- i 1) j)))) ;ci posson essere 0 tratti blu
          ((not (= i 0)) (* 1/3 (prob-3d1 (- n 1) (- i 1) j)))
          ((not (= j 0)) (* 1/3 (prob-3d1 (- n 1) i (- j 1))))
          (else 1)
        )
  )
)

(prob-3d1 3 1 1)