;
; Scheme Programming HW 2
;
; 28 January 2021
;

#lang scheme

; Problem 1
; Return the sum of values in a numeric list that may contain other lists – do not use tail-recursion
; (sum ‘(2 5 (3 5) 1)) => 16

; Calculates the sum of the values in a list L wich contains numbers and lists
(define (sum L)
  (cond
    ((null? L) 0)
    ((list? (car L)) (+ (sum (car L)) (sum (cdr L))))
    (else (+ (car L) (sum (cdr L))))))

; Testing

(display "Problem 1\n")

;; List of int
(display "(sum '(1)) -> 1: ")
(sum '(1))

;; List of ints
(display "(sum '(1 2 3)) -> 6: ")
(sum '(1 2 3))

;; List of ints and lists
(display "(sum '(1 (2 3) 4 (5)) -> 15: ")
(sum '(1 (2 3) 4 (5)))

; Problem 2
; Return the sum of values in a numeric list that may contain other lists – USE tail-recursion
; (sum2 ‘(2 5 (3 5) 1)) => 16

; Calculates the sum of the values in a list L wich contains numbers and lists
(define (sum2 L)
  (sum2-rec L 0))

; Recursive helper method
; Inputs: list - L, s - sum (init as zero)
(define (sum2-rec L s)
  (cond
    ((null? L) s)
    ((list? (car L)) (sum2-rec (cdr L) (sum2-rec (car L) s)))
    (else (sum2-rec (cdr L) (+ (car L) s)))))

; Testing

(display "\nProblem 2\n")

;; List of int
(display "(sum2 '(1)) -> 1: ")
(sum2 '(1))

;; List of ints
(display "(sum2 '(1 2 3)) -> 6: ")
(sum2 '(1 2 3))

;; List of ints and lists
(display "(sum2 '(1 (2 3) 4 (5)) -> 15: ")
(sum2 '(1 (2 3) 4 (5)))

; Problem 3
; Return the sum of values in a list that may contain other lists and non-numeric values
; (sumNumbers ‘(2 q (3 ((5)))(((x))))) => 10

; Returns the sum of numbers in a list L wich may contain other lists and non-numeric values
(define (sumNumbers L)
  (cond
    ((null? L) 0)
    ((list? (car L)) (+ (sumNumbers (car L)) (sumNumbers (cdr L))))
    ((number? (car L)) (+ (car L) (sumNumbers (cdr L))))
    (else (sumNumbers (cdr L)))))

; Testing

(display "\nProblem 3\n")

;; List of int
(display "(sumNumbers '(1)) -> 1: ")
(sumNumbers '(1))

;; List of ints
(display "(sumNumbers '(1 2 3)) -> 6: ")
(sumNumbers '(1 2 3))

;; List of ints and lists
(display "(sumNumbers '(1 (2 3) 4 (5)) -> 15: ")
(sumNumbers '(1 (2 3) 4 (5)))

;; List of nonint
(display "(sumNumbers '(a)) -> 0: ")
(sumNumbers '(a))

;; List of ints and nonints
(display "(sumNumbers '(1 2 a 3 (a 4)) -> 10: ")
(sumNumbers '(1 2 a 3 (a 4)))

; Problem 4
; Given a list of atoms (no lists within lists), return a list with all odd numbers removed:
; (remove_odd ‘(2 5 3 x 6 1)) => (2 x 6)

; Removes odd numbers from a list of atoms L
(define (remove_odd L)
  L
  (cond
    ((null? L) '())
    ((and (number? (car L)) (odd? (car L))) (remove_odd (cdr L)))
    (else (cons (car L) (remove_odd(cdr L))))))
    
; Testing

(display "\nProblem 4\n")

;; List of odd
(display "(remove_odd '(3)) -> (): ")
(remove_odd '(3))

;; List of odds and others
(display "(remove_odd '(3 4 5 a b 6 7 c)) -> (4 a b 6 c): ")
(remove_odd '(3 4 5 a b 6 7 c))

; Problem 5
; Given any list, return a list with all odd numbers removed:
; (remove_odd2 ‘(2 5 (x (5)) 1)) => (2 (x ()))

; Removes odd numbers from any list L
(define (remove_odd2 L)
  (cond
    ((null? L) '())
    ((list? (car L)) (cons (remove_odd2 (car L)) (remove_odd2 (cdr L))))
    ((and (number? (car L)) (odd? (car L))) (remove_odd2 (cdr L)))
    (else (cons (car L) (remove_odd2 (cdr L))))))

; Testing

(display "\nProblem 5\n")

;; List of odd
(display "(remove_odd2 '(3)) -> (): ")
(remove_odd2 '(3))

;; List of odds and others
(display "(remove_odd2 '(3 4 5 a b 6 7 c)) -> (4 a b 6 c): ")
(remove_odd2 '(3 4 5 a b 6 7 c))

;; List of odds, others, and lists
(display "(remove_odd2 ‘(2 5 (x (5)) 1)) => (2 (x ())): ")
(remove_odd2 '(2 5 (x (5)) 1))

; Problem 6
; Returns true if two lists are structurally equivalent (i.e. if two lists have the same structure, even if
; their contents are different).
; (struct-eq ‘(1 2 3) ‘(e r h)) => #t

; Determines if two lists a, b have the same structure
(define (struct-eq a b)
  (cond
    ((and (null? a) (null? b)) #t)
    ((or (null? a) (null? b)) #f)
    ((and (list? (car a)) (list? (car b))) (struct-eq (cdr a) (cdr b)))
    ((and (not (list? (car a))) (not (list? (car b)))) (struct-eq (cdr a) (cdr b)))
    (else #f)))

; Testing

(display "\nProblem 6\n")

(display "(struct-eq '(1 2 3) '(e r h)) -> #t: ")
(struct-eq '(1 2 3) '(e r h))

(display "(struct-eq ‘(1 2 3) ‘(1 3 5 7)) => #f: ")
(struct-eq '(1 2 3) '(1 3 5 7))
    
(display "(struct-eq ‘(1 2 3) ‘(e (r) h)) => #f: ")
(struct-eq '(1 2 3) '(e (r) h))

(display "(struct-eq ‘(1 (2 3)) ‘(e (r h))) => #t: ")
(struct-eq '(1 (2 3)) '(e (r h)))

; Problem 7 (Textbook problem 3.17 pg 98)
; Write an insert procedure for a BST

; Insert integer n into the BST t
(define (insert n t)
  (let ((croot (car t)) (ls (car (cdr t))) (rs (cdr (cdr t))))
    (cond
      ((<= n croot)
       (if (null? ls)
           (list croot (list n '() '()) rs)
           (list croot (insert n ls) rs)))
      (else
       (if (null? (car rs))
           (list croot ls (list n '() '()))
           (list croot ls (insert n (car rs))))))))

; Testing

(display "\nProblem 7\n")

(display "(insert 8 '(1 (2 (( ) (4 ( ) ( )))) (7 ( ) ( )))):\n")
(display "expected: (1 (2 (() (4 () ()))) (7 () (8 () ())))\nactual:   ")
(insert 8 '(1 (2 (( ) (4 ( ) ( )))) (7 ( ) ( ))))

(display "(insert 6 '(1 (2 (() (4 () ()))) (7 () (8 () ())))):\n")
(display "expected: (1 (2 (() (4 () ()))) (7 (6 () ()) ((8 () ()))))\nactual:   ")
(insert 6 '(1 (2 (() (4 () ()))) (7 () (8 () ()))))


; Problem 8 (Textbook problem 3.21 pg 98)
; Define a filter function which filters out elements in a list l wich pass a predicate test p
; Inputs: l - list, p - predicate; Output: list

; Applys the predicate p to the items in a list l and returns the elements for which the predicate returns true
(define (filter p l)
  (cond
    ((null? l) '())
    ((p (car l)) (cons (car l) (filter p (cdr l))))
    (else (filter p (cdr l)))))

; Testing

(display "\nProblem 8\n")

(display "(filter zero? '(1 3 0 2 0 9)) -> (0 0): ")
(filter zero? '(1 3 0 2 0 9))

(display "(filter even? '(1 2 3 22 23 24)) -> (2 22 24): ")
(filter even? '(1 2 3 22 23 24))



(display "\nDone.")
