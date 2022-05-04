(* ML Data Structures *)

(* 16 February 2021 *)

datatype 'a BST = Nil 
|	Node of 'a * 'a BST  * 'a BST;

(* Trees for Testing *)

val tree1 = Node(3, Node(1, Nil, Nil), Node(4, Nil, Nil));
val tree2 = Node(5, Node(3, Node(2, Node(~1, Nil, Nil), Nil), Nil), Node(10, Node(6, Nil, Node(8, Nil, Nil)), Node(12, Nil, Node(20, Nil, Nil))));
(*
 Tree 1          Tree 2
    3               5
   / \            /   \
  1   4          3    10
                /    /  \
               2    6    12
              /      \    \
            ~1        8    20
*)


(* 1. A boolean function called find that returns true if a given value is in a binary search tree
and false otherwise. *)

print "--- Problem 1 ---";

fun find (v, Nil) = false
  | find (v, Node(d, l, r)) = if v = d then true else if v < d then find (v, l) else find (v, r);

print "Expecting:\n-> true\n-> true\n-> true\n-> true\n-> false\n";

find (5, tree2);
find (~1, tree2);
find (20, tree2);
find (8, tree2);
find (0, tree2);

(* 2. A function called parentsOfOne that returns the number of nodes with exactly one non-
empty subtree. *)

print "--- Problem 2 ---";

fun parentsOfOneHelper (Nil, c) = 0
  | parentsOfOneHelper (Node(d, Node(s,l,r), Nil), c) = parentsOfOneHelper(Node(s,l,r), c) + 1
  | parentsOfOneHelper (Node(d, Nil, Node(s,l,r)), c) = parentsOfOneHelper(Node(s,l,r), c) + 1
  | parentsOfOneHelper (Node(d, l, r), c) = parentsOfOneHelper(l, 0) + parentsOfOneHelper(r, 0) + c;
fun parentsOfOne (tree) = parentsOfOneHelper (tree, 0);

print "Expecting:\n-> 0\n-> 0\n-> 1\n-> 1\n-> 4";

parentsOfOne(Nil);
parentsOfOne(Node(0, Nil, Nil));
parentsOfOne(Node(1, Node(2, Nil, Nil), Nil));
parentsOfOne(Node(1, Nil, Node(2, Nil, Nil)));
parentsOfOne(tree2);

(* 3. A function verify that returns true if its parameter is a binary search tree, as opposed to
any binary tree. You may assume that the parameter is a binary tree; verify's job is to
confirm that the binary search property holds. *)

print "--- Problem 3 ---";

fun verify (Nil) = true
  | verify (Node(d,Nil,Nil)) = true
  | verify (Node(d,Node(s,l,r),Nil)) = if d >= s then verify (Node(s,l,r)) else false
  | verify (Node(d,Nil,Node(s,l,r))) = if d < s then verify (Node(s,l,r)) else false
  | verify (Node(d,l,r)) = verify (Node(d,l,Nil)) andalso verify (Node(d,Nil,r));

print "Expecting:\n-> true\n-> true\n-> true\n-> false\n-> true";

verify(Nil);
verify(Node(1, Nil, Nil));
verify(Node(1, Node(0, Nil, Nil), Node(2, Nil, Nil)));
verify(Node(3, Node(1, Nil, Node(~1, Nil, Nil)), Node(5, Node(4, Nil, Nil), Nil)));
verify(tree2);

