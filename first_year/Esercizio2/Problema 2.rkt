;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Problema 2|) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss" "installed-teachpacks")) #f)))
(set-puzzle-shift-step!)


(glue-tiles
 (shift-down (shift-right smaller-tile 2) 5)
 (glue-tiles (shift-right (half-turn smaller-tile) 2)
             (glue-tiles (shift-right (shift-down larger-tile 1) 2) (half-turn larger-tile))))

(glue-tiles
 (shift-right (shift-down (half-turn smaller-tile) 5) 2)
 (glue-tiles (shift-right smaller-tile 2)
             (glue-tiles larger-tile (shift-down (shift-right (half-turn larger-tile) 2) 1))))
      

