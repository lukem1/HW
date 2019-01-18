##
## Name: Luke M
## vigenere.py
##
## Purpose: Encode text with the Vignere chiper
##
## Algorithm: Create graphics window, get message/key from user, generate encoded message, display encoded message
##
## Certification of Authenticity:
##   I certify that this lab is my own work, but I
##   discussed it with: 
##
## Input: Mouse clicks, keyboard input, message, key
## Output: Graphics, encoded message

from graphics import *

def code(message, keyword):
    multiplier = len(message)/len(keyword) #Assumes key is not blank
    keyword = keyword*(int(multiplier)+1) #+1 to ensure int is not zero
    keyword = keyword[:len(message)] #Slices key to proper length
    result = ""
    row = list(range(0,26)) #Var name "row" in reference to Vigenere table

    for i in range(len(message)):
        #Generates appropriate row of Vigenere table for key letter
        shiftedRow = row[ord(keyword[i])-65:] + row[:ord(keyword[i])-65]
        num = ord(message[i])-65
        #Uses the row to encode a letter from the message
        result += chr(shiftedRow[num]+65)

    return result



def main():
    #Graphics setup
    width = 500
    height = 250

    win = GraphWin("Vigenere", width, height)

    messageLabel = Text(Point(width/4, height/6), "Message to encode:")
    messageLabel.draw(win)

    messageBox = Entry(Point(width/1.5, height/6), 20)
    messageBox.draw(win)

    keyLabel = Text(Point(width/4, height/3), "Key:")
    keyLabel.draw(win)

    keyBox = Entry(Point(width/1.5, height/3), 10)
    keyBox.draw(win)

    encodeRect = Rectangle(Point(width/2+width/7, height/1.75), Point(width/2-width/7, height/1.25))
    encodeRect.draw(win)

    encodeText = Text(Point(width/2, height/1.45), "Encode")
    encodeText.draw(win)

    resultLabel = Text(Point(width/2, height/2), "Encoded Message:")
    resultText = Text(Point(width/2, height/1.7), "")

    closeText = Text(Point(width/2, height/1.05), "Click anywhere to exit")

    win.getMouse()

    #Get input

    messageI = messageBox.getText().upper().replace(' ', '')
    keyI = keyBox.getText().upper()

    #Generate and display output

    result = code(messageI, keyI)

    encodeText.undraw()
    encodeRect.undraw()

    resultLabel.draw(win)
    resultText.setText(result)
    resultText.draw(win)

    closeText.draw(win)

    win.getMouse()

    #Close window

    win.close()

main()
