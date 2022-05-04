(* ML Programming Assignment 1 *)

(* 11 February 2021*)

(* 1. Use the map function to perform the following operations on a list L. *)

print "--- Problem 1 ---";

(* a. Replace every negative element of a list of reals by 0, leaving non-negatives
elements as they are. [1.2, ~5.0, 0.0] becomes [1.2, 0.0, 0.0] *)

fun replaceNegative x =
  if x < 0.0 then 0.0 else x;

print "map replaceNegative [1.2, ~5.0, 0.0] -> [1.2, 0.0, 0.0]";
map replaceNegative [1.2, ~5.0, 0.0];

(* b. Subtract 1 from every element of an integer list. [1,2,5] becomes [0, 1, 4] *)

fun sub1 x = x -1;

print "map sub1 [1,2,5] -> [0, 1, 4]";
map sub1 [1,2,5];

(* c. Return the size of each string in a list of strings. (Note ML has a size function for
strings.)*)

print "map size [\"hello\",\"world\",\"!\"] ->  [5, 5, 1]";
map size ["hello","world","!"];


(* 2. Use the reduce function discussed in class to perform the following operations on a list L. *)

print "--- Problem 2 ---";

exception EmptyList;

fun reduce (f, []) = raise EmptyList
  | reduce (f, [a]) = a
  | reduce (f, x::xs) = f(x, reduce (f, xs));

(* a. Find the maximum of a list of reals. *)

fun max (a, b) =
  if a > b then a else b;

print "reduce (max, [1, 4, 3, 5, 7, 6]) -> 7";
reduce (max, [1, 4, 3, 5, 7, 6]);

(* b. Find the minimum of a list of reals. *)

fun min (a, b) =
  if a < b then a else b;

print "reduce (min, [1, 4, ~1, 5, 7, 6]) -> ~1";
reduce (min, [1, 4, ~1, 5, 7, 6]);

(* c. Find the logical OR of a list of booleans. *)

fun or (a, b) =
  if a orelse b then true else false;

print "reduce (or, [true, false, false]); -> true";
reduce (or, [true, false, false]);
print "reduce (or, [false]); -> false";
reduce (or, [false]);

(* 3. Write a function lreduce that takes a two-parameter function F and a list [a 1 , a 2,...,
a n ] and produces: F(....F(F(a 1 , a 2 ),a 3 )...,a n ). lreduce is like reduce but it
groups the elements of the list from the beginning of the list instead of the end. The
form of lreduce is shown in the next problem. *)

print "--- Problem 3 ---";

fun lreducehelper (f, [], c) = raise EmptyList
  | lreducehelper (f, [a], c) = f(c, a)
  | lreducehelper (f, (h::t), c) = lreducehelper(f, t, f(c, h))

fun lreduce (f, []) = raise EmptyList
  | lreduce (f, (a::t)) = lreducehelper (f, t, a);

(* 4. In comments, compare the results of and lreduce(op - , L)? reduce (op -,
L) for some list L with at least one element. *)

(* As seen below, lreduce groups elements from the left and reduce groups elements from the right. *)

print "lreduce (op -, [1,2,3,4]) -> (((1 - 2) - 3) - 4) -> ~8";
lreduce (op -, [1,2,3,4]);
print "reduce (op -, [1,2,3,4]) -> (1 - (2 - (3 - 4))) -> ~2";
reduce(op -, [1,2,3,4]);

(* 5. Write the filter function defined as was in Scheme programming assignment 2. *)

print "--- Problem 5 ---";

fun filter (p, []) = []
  | filter (p, (h::t)) = if p h then [h] @ filter (p, t) else filter (p, t);

(* 6. Use your filter function to *)

print "--- Problem 6 ---";

(* a. Find those elements of a list of reals that are greater than 0. *)

val z = (fn x => if x > 0 then true else false);

print "filter (z, [~3,0,1,2,3,0]) -> [1, 2, 3]";
filter (z, [~3,0,1,2,3,0]);

(* b. Find those elements of a list of reals that are between 1 and 2. *) 

val b = (fn x => if (x > 1.0 andalso x < 2.0) then true else false);

print "filter (b, [~1.0, 0.0, 0.5, 1.0, 1.1, 1.9, 2.0, 2.1]) -> [1.1, 1.9]";
filter (b, [~1.0, 0.0, 0.5, 1.0, 1.1, 1.9, 2.0, 2.1]);

(* c. Find those strings in a list of strings that are longer than 3 characters. *)

val s = (fn s => if size s > 3 then true else false);

print "filter (s, [\"a\", \"ab\", \"abc\", \"abcd\"]) -> [\"abcd\"]";
filter (s, ["a", "ab", "abc", "abcd"]);

(* 7. Write another version of reduce, called reduceB, that takes a basis constant g of some
type ‘b , a function f of type ‘a * ‘b -> ‘b, and a list of elements of type ‘a. The result
applied to a list [a 1 , a 2,..., a n ] is F(a 1 , ... F( a n-1 , F(a n ,g))...). reduceB
takes three arguments: reduceB ( g, f, list) *)

print "--- Problem 7 ---";

fun reduceB (g, f, []) = raise EmptyList
  | reduceB (g, f, [x]) = f(x, g)
  | reduceB (g, f, (x::y)) = f(x, reduceB(g, f, y));

(* 8. Use your reduceB function to *)

print "--- Problem 8 ---";

(* a. Compute the length of a list. *)

fun count (_, b) = b + 1;

print "reduceB (0, count, [1,2,3,4]) -> 4";
reduceB (0, count, [1,2,3,4]);

(* b. Count the number of negative values in a list of ints. *)

fun countNegative (a, b) = if a < 0 then b + 1 else b;

print "reduceB (0, countNegative, [~1, 0, 1, 2, ~3]) -> 2";
reduceB (0, countNegative, [~1, 0, 1, 2, ~3]);

