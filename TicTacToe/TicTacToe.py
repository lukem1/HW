##
## Name: Luke McGuire
## TicTacToe.py
##
## Purpose: Two player tic tac toe game
##
## Algorithm:
##
## Certification of Authenticity:
##   I certify that this lab is my own work, but I
##   discussed it with: Giuliana Tosi
##
## Input: Mouse clicks
## Output: Graphics

from graphics import *

width = 500
height = 500
win = GraphWin("Tic Tac Toe", width, height)

#Game Label
gameLabel = Text(Point(width/2, height/1.05), "_____")
gameLabel.draw(win)

#Used to convert from click location to board list index
boardC = {(0, 0): 0, (1, 0): 1, (2, 0): 2, (0, 1): 3, (1, 1): 4, (2, 1): 5, (0, 2): 6, (1, 2): 7, (2, 2): 8}

#Board Spaces
dispBoard = []

dispBoard.append(Text(Point(width/6, height/6), ""))
dispBoard.append(Text(Point(width/2, height/6), ""))
dispBoard.append(Text(Point(width/2+width/3, height/6), ""))
dispBoard.append(Text(Point(width/6, height/2), ""))
dispBoard.append(Text(Point(width/2, height/2), ""))
dispBoard.append(Text(Point(width/2+width/3, height/2), ""))
dispBoard.append(Text(Point(width/6, height/2+height/3), ""))
dispBoard.append(Text(Point(width/2, height/2+height/3), ""))
dispBoard.append(Text(Point(width/2+width/3, height/2+height/3), ""))


for t in dispBoard:
    t.setSize(25)
    t.draw(win)

#Board Lines
lines = []
#Vertical
lines.append(Line(Point(width/3, 0), Point(width/3, height)))
lines.append(Line(Point(2*width/3, 0), Point(2*width/3, height)))
#Horizontal
lines.append(Line(Point(0, height/3), Point(width, height/3)))
lines.append(Line(Point(0, 2*height/3), Point(width, 2*height/3)))

for l in lines:
    l.draw(win)


def buildBoard():
    return list(range(1, 10))

def isLegal(board, spot):
    if spot >= 10 or spot < 0:
        return False
    elif board[spot] != "x" and board[spot] != "o":
        return True
    else:
        return False


def fillSpot(board, spot, char):
    board[spot] = char
    dispBoard[spot].undraw()
    dispBoard[spot].setText(char)
    if char == "x":
        dispBoard[spot].setTextColor('red')
    elif char == "o":
        dispBoard[spot].setTextColor('blue')
    dispBoard[spot].draw(win)


def isGameWon(board):
    if board[0:3] == ['x', 'x', 'x'] or board[0:3] == ['o', 'o', 'o']:
        return True
    elif board[3:6] == ['x', 'x', 'x'] or board[3:6] == ['o', 'o', 'o']:
        return True
    elif board[6:] == ['x', 'x', 'x'] or board[6:] == ['o', 'o', 'o']:
        return True
    elif board[:7:3] == ['x', 'x', 'x'] or board[:7:3] == ['o', 'o', 'o']:
        return True
    elif board[1::3] == ['x', 'x', 'x'] or board[1::3] == ['o', 'o', 'o']:
        return True
    elif board[2::3] == ['x', 'x', 'x'] or board[2::3] == ['o', 'o', 'o']:
        return True
    elif board[::4] == ['x', 'x', 'x'] or board[::4] == ['o', 'o', 'o']:
        return True
    elif board[2:7:2] == ['x', 'x', 'x'] or board[2:7:2] == ['o', 'o', 'o']:
        return True
    else:
        return False


def main():
    board = buildBoard()
    turn = "x"
    moves = 0
    endStatus = None
    msg = ""
    while endStatus is None:
        gameLabel.setText(msg+turn.upper()+"'s Turn!")
        #The following try/except block allows for using the OS's x button to close the window without error
        try:
            click = win.getMouse()
        except GraphicsError:
            endstatus = "quit"
            break

        x = click.getX()
        y = click.getY()
        xRow = None
        yRow = None
        #Figures out where the player clicks on the board
        if x < width / 3:
            xRow = 0
        elif x > width / 3 and x < 2 * width / 3:
            xRow = 1
        elif x > 2 * width / 3:
            xRow = 2

        if y < height / 3:
            yRow = 0
        elif y > height / 3 and y < 2 * height / 3:
            yRow = 1
        elif y > 2 * height / 3:
            yRow = 2

        space = boardC[(xRow, yRow)]
        """
        print("X: " + str(xRow))
        print("Y: " + str(yRow))
        print("List Index: " + str(space))
        """
        #Checks if the space is valid and performs move operations if so
        if isLegal(board, space):
            fillSpot(board, space, turn)
            moves += 1
            if isGameWon(board):
                endStatus = "win" + turn
            elif moves >= 9:
                endStatus = "tie"
            elif turn == "x":
                msg = ""
                turn = "o"
            elif turn == "o":
                msg = ""
                turn = "x"
        else:
            msg = "Invalid Space!\n"
    #Handles different endstatus possibilities
    if endStatus is None:
        pass
    elif endStatus[:-1] == "win":
        gameLabel.setText(turn.upper()+" Wins!\nClick Anywhere to Quit")
        try:
            click = win.getMouse()
        except GraphicsError:
            pass
    elif endStatus == "tie":
        gameLabel.setText("Tie Game!\nClick Anywhere to Quit")
        try:
            click = win.getMouse()
        except GraphicsError:
            pass


main()