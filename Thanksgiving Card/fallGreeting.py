##
## Name: Luke McGuire
## fallGreeting.py
##
## Purpose: Display an animated Thanksgiving card.
##
## Algorithm: Create graphics objects, animate in, wait for click, animate out, close
##
## Certification of Authenticity:
##   I certify that this lab is my own work, but I
##   discussed it with: Giuliana Tosi
##
## Input: Mouse clicks and keyboard input
## Output: Graphics

from graphics import *
from time import sleep

nameTo = "123456789"
nameFrom = "Luke"

run = True
ups = 60 #Updates per second
mousePos = None
keyState = None

#Window setup
width = 500
height = 500
w = GraphWin("Happy Thanksgiving", width, height, autoflush=False)
w.setBackground('brown')

nodes = []
tracks = []
animationStage = 'enter'

#Node Setup (Higher node list index = higher display level)

#Message card

messageCard = [] #Tracks nodes in message card

textRect = Rectangle(Point(width/5, height/5), Point(width/1.25, height/1.25))
textRect.move(0,(((height/5)-(height/1.25))*1.5))
textRect.setFill('coral')
textRect.setOutline('coral')
nodes.append(textRect)
messageCard.append(textRect)

topTri = Polygon(Point(width/5, height/5), Point(width/2, height/6), Point(width/1.25, height/5))
topTri.setFill('coral')
topTri.setOutline('coral')
topTri.move(0, (((height/5)-(height/1.25))*1.5))
nodes.append(topTri)
messageCard.append(topTri)

bottomTri = Polygon(Point(width/5, height/1.25), Point(width/2, height/1.2), Point(width/1.25, height/1.25))
bottomTri.setFill('coral')
bottomTri.setOutline('coral')
bottomTri.move(0, (((height/5)-(height/1.25))*1.5))
nodes.append(bottomTri)
messageCard.append(bottomTri)

#If name is too long add newline
if len(nameTo) <= 8:
    mainLabel = Text(Point(width/2, height/3.5), "Happy Thanksgiving "+nameTo+"!")
elif len(nameTo) > 8:
    mainLabel = Text(Point(width / 2, height / 3.5), "Happy Thanksgiving\n" + nameTo + "!")
else:
    mainLabel = Text(Point(width / 2, height / 3.5), "Happy Thanksgiving!")

mainLabel.setSize(16)
mainLabel.move(0, (((height/5)-(height/1.25))*1.5))
nodes.append(mainLabel)
messageCard.append(mainLabel)

fromLabel = Text(Point(width / 2, height / 1.45), "-"+nameFrom)
fromLabel.move(0, (((height/5)-(height/1.25))*1.5))
nodes.append(fromLabel)
messageCard.append(fromLabel)

turkey = Image(Point(width/2, height/2), "frame0.gif")
frames = ["frame0.gif", "frame1.gif", "frame2.gif", "frame3.gif"]
frameCount = 0
timingCount = 0
turkey.move(0, (((height/5)-(height/1.25))*1.5))
nodes.append(turkey)
messageCard.append(turkey)

#Exit Card

exitCard = [] #Tracks nodes in exit card

oval = Oval(Point(width/4, height/1.15), Point(width/1.35, height/1.05))
oval.setFill('coral')
oval.move(0, (height/7))
nodes.append(oval)
exitCard.append(oval)

contLabel = Text(Point(width / 2, height / 1.1), "Click anywhere to exit")
contLabel.move(0, (height/7))
nodes.append(contLabel)
exitCard.append(contLabel)

class track(): #"Conveyer Belt" style animation object
    def __init__(self, x, size=10, buffer=25, reverse=False):
        self.x = x
        self.size = size
        self.buffer = buffer
        self.nodes = []
        self.reverse = reverse
        self.colors = ['orange', 'brown', 'gold', 'maroon', 'green', 'red']
        self.colorCount = 0
        self.undraw = False
        tracks.append(self)

        if not reverse:
            self.move = 1
            self.start = 0-self.size
        elif reverse:
            self.move = -1
            self.start = height+self.size

    def generate(self): #Generates a new circle in the track
        self.nodes.append(Circle(Point(self.x, self.start), self.size))
        self.nodes[len(self.nodes) - 1].setFill(self.colors[self.colorCount])
        self.nodes[len(self.nodes)-1].draw(w)
        if self.colorCount < len(self.colors)-1:
            self.colorCount += 1
        else:
            self.colorCount = 0

    def update(self):
        if self.undraw is False:
            if self.nodes == []: #If no circles exist, create one
                self.nodes.append(Circle(Point(self.x, self.start), self.size))
                self.nodes[len(self.nodes) - 1].draw(w)

            #Calls self.generate() if the previously generated circle has passed the buffer point
            if self.nodes[len(self.nodes)-1].getCenter().getY() > self.buffer and not self.reverse:
                self.generate()
            elif height - self.nodes[len(self.nodes)-1].getCenter().getY() > self.buffer and self.reverse:
                self.generate()
        if self.undraw and self.nodes == []:
            tracks.remove(self)

        for n in self.nodes: #Updates each circle in the track
            if n.getCenter().getY() > height+self.size or n.getCenter().getY() < 0-self.size:
                #Undraws and removes from list if the end of the track is reached
                n.undraw()
                self.nodes.remove(n)
            else:
                #Otherwise moves circle
                n.move(0, self.move)

def Controller():
    global run
    global animationStage
    global frameCount
    global turkey
    global timingCount

    while run:
        #Mouse Controller
        mousePos = w.checkMouse()

        #Key Controller
        keyState = w.checkKey()
        if keyState == "X":
            run = False
            #Faster than waiting for exit animation

        #Animation Controller

        if animationStage == 'enter':
            if textRect.getCenter().getY() < height/2:
                for n in messageCard:
                    n.move(0, 3)
            else:
                animationStage = 'wait'
        elif animationStage == 'wait':
            if mousePos == None and oval.getCenter().getY() > height/1.075:
                for n in exitCard:
                    n.move(0, -3)
            elif mousePos != None:
                animationStage = 'exit'
                for t in tracks:
                    t.undraw = True
        elif animationStage == 'exit':
            if textRect.getCenter().getY() > height * 1.5:
                for n in messageCard:
                    nodes.remove(n)
                    n.undraw()
                    animationStage = 'waitTU'
            else:
                for n in messageCard:
                    n.move(0, 3)
                for n in exitCard:
                    n.move(0, 3)
        elif animationStage == 'waitTU':
            if tracks == []:
                animationStage = 'done'
        elif animationStage == 'done':
            run = False

        #Slows down turkey animation cycle
        if timingCount == 9 and turkey in nodes:
            timingCount = 0
            if frameCount <= 2:
                frameCount += 1
            else:
                frameCount = 0

            turkey.undraw()
            nodes.remove(turkey)
            messageCard.remove(turkey)
            turkey = Image(turkey.getAnchor(), frames[frameCount])
            nodes.append(turkey)
            messageCard.append(turkey)
        else:
            timingCount += 1

        for t in tracks:
            t.update()

        for n in nodes:
            n.undraw()
            n.draw(w)

        w.update()
        sleep(1/ups)

#Track Generation
for i in range(int(width/20)):
    if i % 2 == 0:
        r = True
    else:
        r = False
    tracks.append(track(10+(i*20), reverse=r, buffer=15))

Controller()
w.close()