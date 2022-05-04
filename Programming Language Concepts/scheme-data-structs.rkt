;
; Scheme Data Structures
;
; 16 February 2021
;

#lang scheme

; Tree Helper Methods

(define (root t)(car t))
(define (left t) (car (cdr t)))
(define (right t) (car (cdr (cdr t))))

; Trees for Testing

(define tree1 '(3 (1 () ()) (4 () ())))
(define tree2 '(5 (3 (2 (-1 () ()) ()) ()) (10 (6 () (8 () ())) (12 () (20 () ())))))

; Tree 1          Tree 2
;    3               5
;   / \            /   \
;  1   4          3    10
;                /    /  \
;               2    6    12
;              /      \    \
;            ~1        8    20

; Problem 1
; A boolean function called find that returns true if a given value is in a binary search tree
; and false otherwise.

(define (find v t)
  (cond
    ((null? t) #f)
    ((= v (root t)) #t)
    ((< v (root t)) (find v (left t)))
    (else (find v (right t)))))

; Testing

(display "\nProblem 1\n")

(display "(find 1 tree1) -> #t: ")
(find 3 tree1)
(display "(find 0 tree1) -> #f: ")
(find 0 tree1)
(display "(find 8 tree2) -> #t: ")
(find 8 tree2)


; Problem 2
; A function called parentsOfOne that returns the number of nodes with exactly one non-
; empty subtree.

(define (parentsOfOneHelper t c)
  (cond
    ((null? t) 0)
    ((and (not (null? (left t))) (null? (right t))) (parentsOfOneHelper (left t) (+ 1 c)))
    ((and (null? (left t)) (not (null? (right t)))) (parentsOfOneHelper (right t) (+ 1 c)))
    (else (+ (parentsOfOneHelper (left t) 0) (parentsOfOneHelper (right t) 0) c))))

(define (parentsOfOne t)
  (parentsOfOneHelper t 0))

; Testing

(display "\nProblem 2\n")

(display "(parentsOfOne tree1) -> 0: ")
(parentsOfOne tree1)
(display "(parentsOfOne '(1 (2 () ()) ())) -> 1: ")
(parentsOfOne '(1 (2 () ()) ()))
(display "(parentsOfOne tree2) -> 4: ")
(parentsOfOne tree2)


; Problem 3
; A function verify that returns true if its parameter is a binary search tree, as opposed to
; any binary tree. You may assume that the parameter is a binary tree; verify's job is to
; confirm that the binary search property holds.

(define (verify t)
  (cond
    ((null? t) true)
    ((and (not (null? (left t))) (null? (right t))) (if (>= (root t) (root (left t))) (verify (left t)) #f))
    ((and (null? (left t)) (not (null? (right t)))) (if (< (root t) (root (right t))) (verify (right t)) #f))
    (else (and (verify (left t)) (verify (right t))))))

; Testing

(display "\nProblem 3\n")

(display "(verify '()) -> #t: ")
(verify '())
(display "(verify '(1 () ())) -> #t: ")
(verify '(1 () ()))
(display "(verify '(1 (0 () ()) (2 () ()))) -> #t: ")
(verify '(1 (0 () ()) (2 () ())))
(display "(verify '(3 (1 () (-1 () ())) (5 (4 () ()) ()))) -> #f: ")
(verify '(3 (1 () (-1 () ())) (5 (4 () ()) ())))
(display "(verify tree2) -> #t: ")
(verify tree2)

(display "\nDone.")
