;
; Scheme Programming HW 1
; 21 January 2021
;

#lang scheme

;; Returns the units (least significant or rightmost) digit of the integer n
(define (units-digit n)
  (remainder n 10))

;; Returns all but the units digit of the integer n
(define (all-but-units-digit n)
  (quotient n 10))

; Problem 1
; (decimal-length n) returns k+1, where d k is the leading digit of n. For example,
; (decimal-length 348567) --> 6

;; Method that determines the length of an integer n
(define (decimal-length n)
  (decimal-length-n-rec n 1))

;; Recursive helper method
;; Parameters: n - integer, d - length count (init as 1)
(define (decimal-length-n-rec n d)
  (if (= (all-but-units-digit n) 0)
      d
      (decimal-length-n-rec (all-but-units-digit n) (+ 1 d))))

;; Tests

;; Single digit
(display "(decimal-length 1) -> 1: ")
(decimal-length 1)

;; Negative integer
(display "(decimal-length -1) -> 1: ")
(decimal-length -1)

;; Multiple digits
(display "(decimal-length 1234567890) -> 10: ")
(decimal-length 1234567890)
  
; Problem 2
; (ith-digit n i) returns d i of n. For example, (ith-digit 671895629 3) --> 5

;; Method that determines the ith digit of an integer n
(define (ith-digit n i)
  (ith-digit-rec n i 0))

;; Recursive helper method
;; Parameters: n - integer, i - index to determine, d - current index (init to 0)
(define (ith-digit-rec n i d)
  (if (= i d)
      (units-digit n)
      (ith-digit-rec (all-but-units-digit n) i (+ 1 d))))

;; Tests

;; Least significant digit
(display "(ith-digit 1234567890 0) -> 0: ")
(ith-digit 1234567890 0)

;; Middle digit

(display "(ith-digit 1234567890 4) -> 6: ")
(ith-digit 1234567890 4)

;; Most significant digit

(display "(ith-digit 1234567890 9) -> 1: ")
(ith-digit 1234567890 9)

;; Negative integer

(display "(ith-digit -1234567890 9) -> -1: ")
(ith-digit -1234567890 9)

; Problem 3
; (leading-digit n) returns d k , the most significant digit . (leading-digit 534) --> 5

;; Recursively determines the most significant digit of an integer n
(define (leading-digit n)
  (if (= (all-but-units-digit n) 0)
      n
      (leading-digit (all-but-units-digit n))))

;; Tests

;; Single digit
(display "(leading-digit 0) -> 0: ")
(leading-digit 0)

;; Negative integer
(display "(leading-digit -2) -> -2: ")
(leading-digit -2)

;; Multiple digits
(display "(leading-digit 1234567890) -> 1: ")
(leading-digit 1234567890)

; Problem 4
; (occurrences d n) returns the number of times digit d occurs in n .
; (occurrences 6 5494576548754987587654655478563) --> 4

;; Method to count the occurrences of a digit d in an integer n
(define (occurrences d n)
  (occurrences-rec d n 0))

;; Recursive helper method
;; Parameters: d - digit to count, n - integer to search, c - current count (init to 0)
(define (occurrences-rec d n c)
    (let ((i (if (= (units-digit n) d) 1 0)))
      (if (= (all-but-units-digit n) 0)
        (+ c i)
        (occurrences-rec d (all-but-units-digit n) (+ c i)))))
  
;; Tests

;; No occurrences
(display "(occurrences 5 11223344) -> 0: ")
(occurrences 5 11223344)

;; One occurrence
(display "(occurrences 1 1) -> 1: ")
(occurrences 1 1)

;; Many occurrences
(display "(occurrences 3 122333) -> 3: ")
(occurrences 3 122333)

;; Negative integer
(display "(occurrences -3 -122333) -> 3: ")
(occurrences -3 -122333)     

; Problem 5
; (digit-sum n) returns the sum of the digits in n . (digit-sum 2354) --> 14 , (digit-sum 8) --> 8

;; Method to sum the digits of an integer n
(define (digit-sum n)
  (digit-sum-rec n 0))

;; Recursive helper method
;; Parameters: n - integer, s - sum (init as 0)
(define (digit-sum-rec n s)
  (let ((i (+ (units-digit n) s)))
  (if (= (all-but-units-digit n) 0)
      i
      (digit-sum-rec (all-but-units-digit n) i))))

;; Testing

;; Negative integer
(display "(digit-sum -123) -> -6: ")
(digit-sum -123)

;; Zeros
(display "(digit-sum 000) -> 0: ")
(digit-sum 000)

;; Positive integer
(display "(digit-sum 0123) -> 6: ")
(digit-sum 0123)  

; Problem 6
; (digital-root n) returns the result of repeatedly applying digit-sum until the result is a single
; digit . (digital-root 341943) --> 6 (via 3+4+1+9+4+3=24, then 2+4=6)

;; Method to calculate the 'digital-root' of an integer n
(define (digital-root n)
  (let ((i (digit-sum n)))
    (if (= (all-but-units-digit i) 0)
        i
        (digital-root i))))

;; Testing

;; Single digit
(display "(digital-root 3) -> 3: ")
(digital-root 3)

;; Multiple digits
(display "(digital-root 341943) -> 6: ")
(digital-root 341943)

;; Negative integer
(display "(digital-root -341943) -> -6: ")
(digital-root -341943)

; Problem 7
;(backwards n) returns the digits in reverse order. You do not need to handle leading or trailing zeros. For this
;problem you may use multiplication. For example: (backwards 329145) --> 541923

;; Method which reverses the order of the digits of an integer n
(define (backwards n)
  (backwards-rec n (- (decimal-length n) 1) 0))

;; Recursive helper method
;; Parameters: n - integer, p - power (init as length of n - 1), r - result (init as 0)
(define (backwards-rec n p r)
  (if (= (all-but-units-digit n) 0)
      (+ r (units-digit n))
      (backwards-rec (all-but-units-digit n) (- p 1) (+ r (* (units-digit n) (expt 10 p))))))

;; Testing

;; One digit
(display "(backwards 1) -> 1: ")
(backwards 1)

;; Multiple digits
(display "(backwards 12345) -> 54321: ")
(backwards 12345)

;; Negative integer
(display "(backwards -12345) -> -54321: ")
(backwards -12345)

(display "Done.")
