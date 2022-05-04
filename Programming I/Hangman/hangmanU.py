##
## Name: lukem1
## hangman.py
##
## Purpose: Hangman Game
##
## Algorithm:
##
## Certification of Authenticity:
##   I certify that this lab is my own work, but I
##   discussed it with: 
##
## Input: Mouse clicks, Keyboard Input
## Output: Graphics

import random
from graphics import *

wordList = "wordlist.txt"

width = 500
height = 500
win = GraphWin("Hangman", width, height)
win.setBackground("lightblue")

#Game Graphics Objects
gameBase = []

#Static nodes not 'given' variables

#Lines
gameBase.append(Line(Point(0, height/1.3), Point(width, height/1.3)))
gameBase.append(Line(Point(width/2, height/1.3), Point(width/2, height)))

#Persistent Labels
gameBase.append(Text(Point(width/2-width/4, height/1.25), "Guesses"))
gameBase.append(Text(Point(width/2+width/4, height/1.25), "Word"))

#Drawing
gameBase.append(Rectangle(Point(width/3, height/7), Point(width/3.25, height/1.75)))
gameBase[len(gameBase)-1].setFill(color_rgb(135, 90, 0))
gameBase[len(gameBase)-1].setOutline(color_rgb(135, 90, 0))

gameBase.append(Rectangle(Point(width/3, height/7), Point(width/1.9, height/6.25)))
gameBase[len(gameBase)-1].setFill(color_rgb(135, 90, 0))
gameBase[len(gameBase)-1].setOutline(color_rgb(135, 90, 0))

gameBase.append(Line(Point(width/2, height/6.25), Point(width/2, height/5)))

#Nonstatic nodes

secretLabel = Text(Point(width/2+width/4, height/1.15), "___")
gameBase.append(secretLabel)

guessesLabel = Text(Point(width/2-width/4, height/1.15), "___")
gameBase.append(guessesLabel)

remainingLabel = Text(Point(width/2-width/4, height/1.025), "Remaining: ")
gameBase.append(remainingLabel)

messageLabel = Text(Point(width/2+width/4, height/1.025), "___")
gameBase.append(messageLabel)

inputBox = Entry(Point(width/2.15, height/1.375), 10)
gameBase.append(inputBox)

submitButton = Circle(Point( width/1.675, height/1.375), 10)
submitButton.setFill("green")
gameBase.append(submitButton)

#Man
hman = []

hman.append(Circle(Point(width/2, height/4.2), 20)) #head
hman.append(Line(Point(width/2, height/4.2+20), Point(width/2, height/2.25))) #body
hman.append([]) #arms
hman[len(hman)-1].append(Line(Point(width/2, height/3), Point(width/2-width/20, height/2.75)))
hman[len(hman)-1].append(Line(Point(width/2, height/3), Point(width/2+width/20, height/2.75)))
hman.append([]) #legs
hman[len(hman)-1].append(Line(Point(width/2, height/2.25), Point(width/2-width/15, height/1.95)))
hman[len(hman)-1].append(Line(Point(width/2, height/2.25), Point(width/2+width/15, height/1.95)))
hman.append(Circle(Point(width/2, height/4.125), 1)) #nose
hman.append(Line(Point(width/2-width/50, height/3.85), Point(width/2+width/50, height/3.85)))
hman.append([]) #eyes
hman[len(hman)-1].append(Text(Point(width/2-width/45, height/4.35), "X"))
hman[len(hman)-1][0].setSize(7)
hman[len(hman)-1].append(Text(Point(width/2+width/45, height/4.35), "X"))
hman[len(hman)-1][1].setSize(7)

"""
for i in hman:
    if isinstance(i, list):
        for e in i:
            e.draw(win)
    else:
            i.draw(win)
"""

def distance(p1, p2):
    return ((p2.getX()-p1.getX())**2+(p2.getY()-p1.getY())**2)**0.5

def getWords(file=wordList):
    with open(file, 'r') as f:
        words = f.readlines()

    return words

def getSecret(words):
    return random.choice(words).replace('\n', '')

def hideUndiscovered(guesses, secret):
    hidden = ""
    for l in secret:
        if l.upper() in guesses:
            hidden += l
        else:
            hidden += " _ "

    return hidden

def isGuessed(guesses, secret):
    for l in secret:
        if l.upper() not in guesses:
            return False

    return True

def checkGuess(guesses, guess, secret):
    valid = True
    if guess.upper() in guesses or len(guess) != 1:
        valid = False

    correct = False
    if guess in secret:
        correct = True

    return valid, correct

def dispMan(iguesses):
    for i in range(iguesses):
        n = hman[i]
        if isinstance(n, list):
            for e in n:
                e.undraw()
                e.draw(win)
        else:
            n.undraw()
            n.draw(win)


def hangman():
    words = getWords()
    secret = getSecret(words)
    guesses = []
    guessesC = 0
    incorrectGuesses = 0
    maxIncorrect = 7
    endstatus = None
    for i in gameBase:
        i.draw(win)

    while endstatus is None:
        dispMan(incorrectGuesses)

        secretLabel.undraw()
        secretLabel.setText(hideUndiscovered(guesses, secret))
        secretLabel.draw(win)

        remainingLabel.undraw()
        remainingLabel.setText("Remaining: " + str(maxIncorrect - incorrectGuesses))
        remainingLabel.draw(win)

        gs = ""
        for g in guesses:
            gs += g + " "
        guessesLabel.undraw()
        guessesLabel.setText(gs)
        guessesLabel.draw(win)

        try:
            click = win.getMouse()
        except GraphicsError:
            endstatus = "quit"
            break

        if distance(click, submitButton.getCenter()) <= 10:
            g = inputBox.getText()
            v, c = checkGuess(guesses, g, secret)
            if v:
                inputBox.setText("")
                messageLabel.undraw()
                messageLabel.setText("Guess Again!")
                messageLabel.draw(win)

                guesses.append(g.upper())
                guessesC += 1
                if c:
                    if isGuessed(guesses, secret):
                        endstatus = "win"
                else:
                    incorrectGuesses += 1
                    if incorrectGuesses >= maxIncorrect:
                        endstatus = "lose"

                gs = ""
                for g in guesses:
                    gs += g + " "

                guessesLabel.undraw()
                guessesLabel.setText(gs)
                guessesLabel.draw(win)

                remainingLabel.undraw()
                remainingLabel.setText("Remaining: " + str(maxIncorrect - incorrectGuesses))
                remainingLabel.draw(win)
            else:
                messageLabel.undraw()
                messageLabel.setText("Invalid Guess!")
                messageLabel.draw(win)

            if endstatus == "quit":
                pass
            elif endstatus == "win":
                messageLabel.undraw()
                messageLabel.setText("You Win!")
                messageLabel.draw(win)
                secretLabel.undraw()
                secretLabel.setText(secret)
                secretLabel.draw(win)
                try:
                    click = win.getMouse()
                except GraphicsError:
                    break
            elif endstatus == "lose":
                dispMan(incorrectGuesses)
                messageLabel.undraw()
                messageLabel.setText("You Lose!")
                messageLabel.draw(win)
                secretLabel.undraw()
                secretLabel.setText(secret)
                secretLabel.draw(win)
                try:
                    click = win.getMouse()
                except GraphicsError:
                    break

def main():
    hangman()

main()
