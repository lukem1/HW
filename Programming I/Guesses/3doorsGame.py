##
## Name: Luke M
## 3doorsGame.py
##
## Purpose: Graphical Guessing Game
##
##
## Certification of Authenticity:
##   I certify that this lab is my own work, but I
##   discussed it with:
##
## Input: Mouse clicks
## Output: Graphics

from graphics import *
import random
from button import Button
from time import sleep

width = 500
height = 250
win = GraphWin("Guessing Game", width, height)

wins = 0
games = 0

#Door buttons
doors = []
doors.append(Button(Rectangle(Point(width/2-width/2.5, height/2+height/10), Point(width/2-width/4, height/2-height/10)), "Door 1"))
doors.append(Button(Rectangle(Point(width/2-45, height/2+height/10), Point(width/2+45, height/2-height/10)), "Door 2"))
doors.append(Button(Rectangle(Point(width/2+width/4, height/2+height/10), Point(width/2+width/2.5, height/2-height/10)), "Door 3"))
#Text objects
messages = []
messages.append(Text(Point(width/2, height/5), "Text"))
messages.append(Text(Point(width/2, height/1.25), "Text2"))

#Menu Buttons
buttons = []
buttons.append(Button(Rectangle(Point(width/3-45, height/2+20), Point(width/3+45, height/2-20)), "Quit"))
buttons.append(Button(Rectangle(Point(width/1.5-45, height/2+20), Point(width/1.5+45, height/2-20)), "New Game"))

def game(): #Function that runs the game
	global games
	global wins
	#Chooses secret door
	secret = random.choice(doors)
	for d in doors:
		d.colorButton('white')
		d.draw(win)
	messages[0].setText("Pick a Door...")
	messages[1].setText("Click to Choose")
	for m in messages:
		m.draw(win)

	endStatus = None
	while endStatus is None:
		#Get mouse click
		try:
			click = win.getMouse()
		except GraphicsError:
			endStatus = "quit"
			break
		#Check if the click is on the secret door
		if secret.isClick(click):
			endStatus = "win"
			wins += 1
			messages[0].setText("You Win!")
			messages[1].setText(secret.label.getText() + " was the secret door.\nClick to continue...")
			for i in range(4):
				secret.colorButton('green')
				sleep(1/7)
				secret.colorButton('white')
				sleep(1/7)
			secret.colorButton('green')
			try:
				win.getMouse()
			except GraphicsError:
				endStatus = 'quit'
		else:
			#Check if the click is on another door
			for d in doors:
				if d.isClick(click):
					d.colorButton('red')
					endStatus = "lose"
					secret.colorButton('green')
					messages[0].setText("You Lose!")
					messages[1].setText(secret.label.getText()+" was the secret door.\nClick to continue...")
					try:
						win.getMouse()
					except GraphicsError:
						endStatus = 'quit'

	if endStatus != 'quit':
		games += 1
		menu(endStatus)

def menu(endStatus=None): #Function to display/run menu
	global games
	global wins
	if endStatus is not None:
		for d in doors:
			d.undraw()
		for m in messages:
			m.undraw()

	for b in buttons:
		b.draw(win)
	#Displays a message based on performance
	if games > 0:
		winP = wins/games
		messages[0].setText("Games Played: "+str(games)+"\nGames Won: "+str(wins)+"\nGames Lost: "+str(games-wins)+"\nWin Percent: "+str(winP*100)[:5]+"%")
		messages[0].draw(win)
		if winP == 1.0:
			messages[1].setText("Perfect!")
		elif winP >= 0.75:
			messages[1].setText("Pretty Good!")
		elif winP >= 0.33:
			messages[1].setText("Ok...")
		elif winP > 0.0:
			messages[1].setText("Not doing so well?")
		elif winP == 0.0:
			messages[1].setText("Still haven't won...")
		messages[1].draw(win)


	state = None
	while state is None:
		try:
			click = win.getMouse()
		except GraphicsError:
			state = "quit"
			break

		if buttons[1].isClick(click):
			state = 'newgame'
		elif buttons[0].isClick(click):
			state = 'end'

	if state == 'newgame': #Starts a new game
		for m in messages:
			m.undraw()
		for b in buttons:
			b.undraw()
		game()



game()