;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Esercizio1) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
(define frase
  (lambda(s v c)
    (string-append (soggetto s)(verbo s v)(complemento c))
    )
  )


(define soggetto
  (lambda(s)
    (cond ((char=? #\o (string-ref s (-(string-length s) 1))) (string-append "Il" " " s " ")) ;prima condizione
          ((char=? #\a (string-ref s (-(string-length s) 1))) (string-append "La" " " s " ")) ;seconda condizione
          ((char=? #\i (string-ref s (-(string-length s) 1))) (string-append "I" " " s " "));terza condizone
          ((char=? #\e (string-ref s (-(string-length s) 1))) (string-append "Le" " " s " "))) ;quarta coondizoner
    
    ;chiudo il cond
    ))

(define verbo
  (lambda(s v)
    (frase "cane" "mangia" "crocchette")(cond((and(or (char=? #\o (string-ref s (- (string-length s) 1))) (char=? #\a (string-ref s (- (string-length s) 1)))) (string=? "are" (substring v  (- (string-length v) 3) (string-length v)))) (string-append(substring v 0(- (string-length v) 3)) "a" " "))
         ((and(or (char=? #\o (string-ref s (- (string-length s) 1))) (char=? #\a (string-ref s (- (string-length s) 1)))) (string=? "ere" (substring v  (- (string-length v) 3) (string-length v)))) (string-append(substring v 0(- (string-length v) 3)) "e" " "))
         ((and(or (char=? #\o (string-ref s (- (string-length s) 1))) (char=? #\a (string-ref s (- (string-length s) 1)))) (string=? "ire" (substring v  (- (string-length v) 3) (string-length v)))) (string-append(substring v 0(- (string-length v) 3)) "e" " "))


         ((and(or (char=? #\i (string-ref s (- (string-length s) 1))) (char=? #\e (string-ref s (- (string-length s) 1)))) (string=? "are" (substring v  (- (string-length v) 3) (string-length v)))) (string-append(substring v 0(- (string-length v) 3)) "ano" " "))
         ((and(or (char=? #\i (string-ref s (- (string-length s) 1))) (char=? #\e (string-ref s (- (string-length s) 1)))) (string=? "ere" (substring v  (- (string-length v) 3) (string-length v)))) (string-append(substring v 0(- (string-length v) 3)) "ono" " "))
         ((and(or (char=? #\i (string-ref s (- (string-length s) 1))) (char=? #\e (string-ref s (- (string-length s) 1)))) (string=? "ire" (substring v  (- (string-length v) 3) (string-length v)))) (string-append(substring v 0(- (string-length v) 3)) "ono" " "))

         ) ;quarta coondizoner
    
    ;chiudo il cond

    )
  )

(define complemento
  (lambda(c)
    (cond ((char=? #\o (string-ref c (-(string-length c) 1))) (string-append "il" " " c)) ;prima condizione
          ((char=? #\a (string-ref c (-(string-length c) 1))) (string-append "la" " " c)) ;seconda condizione
          ((char=? #\i (string-ref c (-(string-length c) 1))) (string-append "i" " " c));terza condizone
          ((char=? #\e (string-ref c (-(string-length c) 1))) (string-append "le" " " c))) ;quarta coondizoner
    
    ;chiudo il cond
    ))




    

