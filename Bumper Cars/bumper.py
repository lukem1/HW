##
## Name: Luke McGuire
## bumper.py
##
## Purpose: Bumper cars simulation
##
## Algorithm: Create window, create car class, controller function
##
## Certification of Authenticity:
##   I certify that this lab is my own work, but I
##   discussed it with: Giuliana Tosi
##
## Input: Mouse clicks, Keyboard input
## Output: Graphics

from graphics import *
import math
import random
from time import sleep

run = True
fps = 60

#Sets up window
width = 500
height = 500
win = GraphWin("Bumper Cars", width, height, autoflush=False)
win.setBackground("green")

#Labels
exitLabel = Text(Point(width/2, height/1.02), "To exit press x on the keyboard.")
exitLabel.draw(win)

#Regenerate label
regenLabel = Text(Point(width/2, height/1.05), "Regenerate cars by pressing r.")
regenLabel.draw(win)

carRadius = 20
cars = []

#Function to calculate the distance between two point objects
def distance(p1, p2):
    return ((p2.getX()-p1.getX())**2+(p2.getY()-p1.getY())**2)**0.5

#Returns which quadrant an angle is in
def idQuadrant(angle):
    if angle > 0 and angle < math.pi/2:
        return 1
    elif angle > math.pi/2 and angle < math.pi:
        return 2
    elif angle > math.pi and angle < (3*math.pi)/2:
        return 3
    elif angle > (3*math.pi)/2 and angle < 2*math.pi:
        return 4
    else:
        return None
        #raise Exception("Invalid Parameter: "+str(angle)) #Used during testing

def randColor():
    return color_rgb(random.randint(0,255), random.randint(0,255), random.randint(0,255))

#Car class
class car():
    def __init__(self, startPoint, startAngle, startSpeed=1, color="red"):
        self.radius = carRadius
        self.sprite = Circle(startPoint, self.radius)
        self.sprite.setFill(color)
        self.draw()
        self.angle = startAngle
        self.speed = startSpeed
        self.skipNextUpdate = False
        self.collisons = []
        self.boundsCollision = None
        cars.append(self)

    def draw(self):
        self.sprite.draw(win)

    def undraw(self):
        self.sprite.undraw()

    def delete(self):
        self.undraw()
        cars.remove(self)

    def move(self, angle=None, dist=None):
        if angle == None:
            angle = self.angle
        if dist == None:
            dist = self.speed
        moveX = dist * math.cos(angle)
        moveY = dist * math.sin(angle) * -1
        self.sprite.move(moveX, moveY)

    def update(self):
        if self.angle > math.pi*2:
            self.angle %= 2*math.pi
        elif self.angle < 0:
            self.angle = (2*math.pi) - (self.angle % (2*math.pi))

        if not self.skipNextUpdate:
            cX = self.sprite.getCenter().getX()
            cY = self.sprite.getCenter().getY()

            #Finishes dealing with collisions detected previous update
            q = idQuadrant(self.angle)
            if self.boundsCollision == "Y":
                if q == 1 or q == 2:
                    self.angle = math.pi - self.angle
                elif q == 3 or q == 4:
                    self.angle = 2*math.pi - (self.angle - math.pi)
                else:
                    self.angle += math.pi

            elif self.boundsCollision == "X":
                if q != None:
                    self.angle = 2*math.pi - self.angle
                else:
                    self.angle += math.pi
            elif len(self.collisons) > 0:
                for c in self.collisons:
                    c.angle += math.pi
                    c.move(dist=c.speed)
                self.angle += math.pi
                self.move(dist=self.speed)
                self.collisons = []

            self.boundsCollision = None

            #Checks for and deals with car collisons
            moveD = self.speed
            for c in cars:
                if c != self:
                    d = distance(c.sprite.getCenter(), self.sprite.getCenter())
                    if d <= self.radius*2+self.speed:
                        moveD = (self.speed/(self.speed+c.speed)) * (d - carRadius*2)
                        cmoveD = (c.speed/(self.speed+c.speed)) * (d - carRadius*2)
                        c.move(dist=cmoveD)
                        self.move(dist=moveD)
                        c.skipNextUpdate = True
                        self.collisons.append(c)
                        self.sprite.setFill(randColor())
                        c.sprite.setFill(randColor())

            #The following checks for and deals with collisions with window edges
            if self.collisons == []:
                moveX = moveD * math.cos(self.angle)
                moveY = moveD * math.sin(self.angle) * -1
                if cX - self.radius + moveX <= 0:
                    #Left C
                    moveX = (0 - (cX-self.radius))
                    self.boundsCollision = "Y"
                elif cX + self.radius + moveX >= width:
                    #Right C
                    moveX = width - (cX + self.radius)
                    self.boundsCollision = "Y"

                if cY - self.radius + moveY <= 0:
                    #Top C
                    moveY = (0 - (cY - self.radius))
                    self.boundsCollision = "X"
                elif cY + self.radius + moveY >= height:
                    #Bottom C
                    moveY = height - (cY + self.radius)
                    self.boundsCollision = "X"

                self.sprite.move(moveX, moveY)
        else:
            self.skipNextUpdate = False

#Used to generate cars
def generate(cars=2, linear=False, speed=3, test=False):
    #Caps number of cars at 10
    if cars > 10:
        cars = 10

    if not test:
        points = []
        for i in range(cars):
            if linear: #Limits directions to L,R,U,D
                angle = random.choice([0, math.pi/2, math.pi, 3*math.pi/2])
            else:
                angle = random.randint(0, int(2*math.pi))

            # Ensures cars don't generate on top of each other
            notFound = True
            while notFound:
                point = Point(random.randint(0+carRadius, width-carRadius), random.randint(0+carRadius, height-carRadius))
                if len(points) > 0:
                    for p in points:
                        if distance(p, point) >= carRadius*2+3:
                            notFound = False
                else:
                    notFound = False

            points.append(point)
            car(point, angle, speed, randColor())
    else:
        #testing mode sets up two cars heading towards eachother
        cars = []
        car(Point(width / 2 - 100, height / 2), 0, 2, "red")
        car(Point(width / 2 + 100, height / 2), math.pi, 2, "blue")
        #car(Point(width / 2 - 100, height / 2), 4, 2, "red")


def controller():
    global run
    global cars
    #Initial car generation
    generate(cars=10)
    while run:
        # Mouse Controller
        mousePos = win.checkMouse()

        # Key Controller
        keyState = win.checkKey()
        if keyState == "x":
            run = False
        elif keyState == "r":
            #regen cars without restarting program
            for c in cars:
                c.undraw()
            cars = []
            generate()

        for c in cars:
            c.update()

        win.update()
        sleep(1/fps)

controller()