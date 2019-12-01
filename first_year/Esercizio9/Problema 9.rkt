;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Problema 9|) (read-case-sensitive #t) (teachpacks ((lib "hanoi.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "hanoi.ss" "installed-teachpacks")) #f)))
;Per trasferire tutti i dischi da s a d, bisogna prima spostare n-1 dischi da s a t, poi spostare la base da s a d, e poi nuovamente spostare n-1 dischi da t a d.
;Tutto questo (supponendo che vengano effettuate il minor numero di mosse) in 2^n - 1 mosse per una torre di n dischi

(define hanoi-disks ;Restituisce la configurazione del gioco di hanoi con n dischi alla k-esima mossa
  (lambda (n k);n,k: interi
    (hanoi-disks1 n k 1 2 3 0 0 0) ;i tre 0 sono il numero dei dischi nei paletti
    ))

(define hanoi-disks1
  (lambda (n k a b c s t d) ;n: numero di dischi ;k è la k-esima mossa s:sorgente d:destinazione t:transito ;a,b,c numero paletti
    (if (= n 0)
        (list (list a s) (list b t) (list c d))
        (if (>= k (expt 2 (- n 1))) ;la mossa esattamente a metà permette di spostare il disco più grande ( 2^(n-1) ) 
        (hanoi-disks1 (- n 1) (- k (expt 2 (- n 1))) c b a d (+ t 1) s)
        (hanoi-disks1 (- n 1) k a c b (+ s 1) d t))
    )))

(define hanoi-picture ;Restituisce la rappresentazione grafica di hanoi-disks
  (lambda (n k) ;n,k: interi
    (hanoi-picture1 n k 1 2 3 0 0 0 n (towers-background n) )
    ))

(define hanoi-picture1
  (lambda (n k a b c s t d nc img)
    (if (= n 0)
        img
        (if (>= k (expt 2 (- n 1))) 
            (hanoi-picture1 (- n 1) (- k (expt 2 (- n 1))) c b a d (+ t 1) s nc (above (disk-image n nc b t) img)) ;above sovrappone l'immagine all'immagine precedente
            (hanoi-picture1 (- n 1) k a b c (+ s 1) d t nc (above (disk-image n nc a s) img))
            ))))
        
        

(hanoi-disks 3 0)
(hanoi-disks 3 3)
