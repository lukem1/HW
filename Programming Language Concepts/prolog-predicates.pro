%
% Prolog Predicates
% 
% 18 March 2021
%

% Define the following Prolog predicates:

% 1. oddLength(List) returns true if list is of odd length.
% For example the call ?-oddLength([a, b, c]) returns true

oddLength([]) :- false.  % empty list is not odd
oddLength([_]) :- true. % list of 1 is odd
% for lists with >= 2 items recursively remove two elements
% until the tail matches [] or [_].
oddLength([_, _ |T]) :- oddLength(T). 

% Tests
%?-oddLength([]). -> false
%?-oddLength([a]). -> true
%?-oddLength([a, b]). -> false
%?-oddLength([a, b, c]). -> true

% 2. evenLength(List ) returns true if list is of even length.
% For example the call ?-evenLength([a, b, c]) returns false

evenLength([]) :- false. % empty list is not even
% Otherwise, we want the negation of oddLength
evenLength([H|T]) :- not(oddLength([H|T])).

% Tests
%?-evenLength([]). %-> false
%?-evenLength([a]). %-> false
%?-evenLength([a, b]). %-> true
%?-evenLength([a, b, c]). %-> false

% 3. addAtRear(X, Oldlist, Newlist).
% The item X is added at the rear of the list Oldlist to produce the list Newlist . 
% For example the call ?-addAtRear(d, [a, b, c], List)
% produces the response List = [a, b, c, d]

addAtRear(X, [], [X]) :- !. % If oldlist is []
addAtRear(X, [OH], [OH, X]) :- !. % If oldlist has 1 item
% If oldlist has more than one item
addAtRear(X, [OH|OT], [OH|N]) :- addAtRear(X, OT, N).

% Tests
%?-addAtRear(x, [], List). %-> List = [x]
%?-addAtRear(y, [x], List). %-> List = [x,y]
%?-addAtRear(d, [a,b,c], List). %-> List = [a,b,c,d]


% 4. deleteFromRear(Oldlist, X, Newlist).
% The last item is removed from the list Oldlist to produce the list Newlist 
% and is also returned as X. For example the call
% ?-deleteFromRear([a, b, c, d], Item, List) produces the response
% Item = d
% List = [a, b, c]

deleteFromRear([], [], []). % Empy list
deleteFromRear([X], X, []) :- !. % One item in list
deleteFromRear([OH|OT], X, [OH|NT]) :- deleteFromRear(OT, X, NT).
    	
% Tests
%?-deleteFromRear([], Item, List). %-> Item = [], List = []
%?-deleteFromRear([a], Item, List). %-> Item = a, List = []
%?-deleteFromRear([a,b], Item, List). %-> Item = b, List = [a]
%?-deleteFromRear([a,b,c], Item, List). %-> Item = c, List = [a,b]


% 5. elementAt(Element, List, Position).
% Find the element at position Position in List and return it through Element. 
% Note the first element is at position 1. For example the call
% elementAt(X, [1, 2, 4, 5], 3) produces the response X = 4

elementAt(H, [H|_], 1) :- !. % Current head is 1
% ID > 1
elementAt(X, [_|T], ID) :- LID is ID - 1, elementAt(X, T, LID).

% Tests
%?-elementAt(X, [a], 1) %-> X = a
%?-elementAt(X, [1,2,3], 1) %-> X = 1
%?-elementAt(X, [1,2,3], 2) %-> X = 2
%?-elementAt(X, [1,2,3], 3) %-> X = 3


% 6. The following definition performs an inorder traversal of a tree (visiting left subtree, root, then
% right subtree) and creates a list of nodes in the order that they are visited.
inorder(t(K,L,R), List):-inorder(L,LL), inorder(R, LR),
	append(LL, [K|LR],List).
inorder(nil, []).

tree1(t(6, t(4, t(2, nil, nil), t(5, nil, nil)), t(9, t(7, nil, nil), nil))).
tree2(t(8, t(5, nil, t(7, nil, nil)), t(9, nil, t(11, nil, nil)))).

% A. Write a definition for preorder

preorder(t(K,L,R), List) :- preorder(L,LL), preorder(R,LR),
    append([K|LL], LR, List).
preorder(nil, []).

% Tests
%?-tree1(T), preorder(T, List) %-> List = [6, 4, 2, 5, 9, 7]
%?-tree2(T), preorder(T, List) %-> List = [8, 5, 7, 9, 11]

% B. Write a definition for postorder

postorder(t(K,L,R), List) :- postorder(L,LL), postorder(R,LR),
    append(LL, LR, TL), append(TL, [K], List).
postorder(nil, []).

% Tests
%?-tree1(T), postorder(T, List) %-> List = [2, 5, 4, 7, 9, 6]
%?-tree2(T), postorder(T, List) %-> List = [7, 5, 11, 9, 8]



