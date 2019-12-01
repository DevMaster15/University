;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Problema7 (parte 1)|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define manh-3d
  (lambda(i j k)

    (cond
         ((and (= i 0) (= j 0) (= k 0)) 0)
         ((and (> i 0) (= j 0) (= k 0)) 1)
         ((and (= i 0) (> j 0) (= k 0)) 1)
         ((and (= i 0) (= j 0) (> k 0)) 1)
         ((and (> i 0) (> j 0) (= k 0)) (+ (manh-3d (- i 1) j k) (manh-3d i (- j 1) k)))
         ((and (> i 0) (= j 0) (> k 0)) (+ (manh-3d (- i 1) j k) (manh-3d i j (- k 1))))
         ((and (> i 0) (> j 0) (= k 0)) (+ (manh-3d (- i 1) j k) (manh-3d i (- j 1) k)))
         ((and (= i 0) (> j 0) (> k 0)) (+ (manh-3d i (- j 1) k) (manh-3d i j (- k 1))))
         ((and (> i 0) (> j 0) (> k 0)) (+ (manh-3d (- i 1) j k) (manh-3d i (- j 1) k) (manh-3d i j (- k 1) )))

         ) ;cond



    ))

(define prob7
  (lambda(n i j) ;numero di livelli  i: tratti rossi j: tratti verdi

    
    ;rapporto tra il numero di percorsi diversi ed il numero di percorsi possibili
    (/ (manh-3d i j (- n (+ i j))) (expt 3 n)) 

    )
   )

(prob7 3 1 1)