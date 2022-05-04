%
% Logic Programming in Prolog
%
% 12 March 2021
%

% 1. Write a Prolog rule that checks for class room conflicts (more than one class scheduled for same room at same time).

sameTime(Course1, Course2) :- course(Course1, time(Days, Start, Finish), _, _), course(Course2, time(Days, Start, Finish), _, _), Course1 \== Course2 .

samePlace(Course1, Course2) :- course(Course1, _, _, Location), course(Course2, _, _, Location), Course1 \== Course2 .

roomConflict(Course1, Course2) :- sameTime(Course1, Course2), samePlace(Course1, Course2) .
% Note that this does not check for classes with partially overlapping times.

% 2. Write a query that will display the course name, location and time for courses taught by Professor RoxAnn Stalvey.

course(course(Name, _), time(Days, Start, End), instructor(roxann, stalvey), _) .

% 3. Write a query that will display the name of any professor who teaches a course that starts at 1200.

course(course(Name, _), time(_, Start, _), _, _), Start=1200 .

% 4. Write a height predicate to return the height of any tree.

max(One,Two,One) :- One > Two, !. max(_,Two, Two).
height(nil, -1).
height(tree(nil), -1).
height(tree(_, L, R), H) :-
    height(L, LH), height(R, RH), max(LH, RH, M), H is M+1.
