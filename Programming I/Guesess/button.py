##
## Name: Luke M
## 3doorsGame.py
##

from graphics import *

class Button():
	def __init__(self, shape, text):
		self.shape = shape
		self.label = Text(self.shape.getCenter(), text)

	def getLabel(self):
		return self.label

	def draw(self, win):
		self.shape.draw(win)
		self.label.draw(win)

	def undraw(self):
		self.label.undraw()
		self.shape.undraw()

	def isClick(self, point):
		p1 = self.shape.getP1()
		p2 = self.shape.getP2()
		xC = point.getX()
		yC = point.getY()
		"""print(xC, yC)
		print("P1:", p1.getX(), p1.getY())
		print("P2:", p2.getX(), p2.getY())"""
		if xC >= p1.getX() and xC <= p2.getX():
			if yC <= p1.getY() and yC >= p2.getY():
				return True
			else:
				return False
		else:
			return False

	def colorButton(self, color):
		self.shape.setFill(color)

	def setLabel(self, win, label):
		self.label.undraw()
		self.label.setText(label)
		self.label.draw(win)



def main():
	win = GraphWin()
	rb = Button(Rectangle(Point(100,100), Point(65, 130)), 'yes')
	rb.draw(win)
	while True:
		pt = win.getMouse()
		if rb.isClick(pt):
			rb.colorButton('red')
			rb.setLabel(win,'red')
		else:
			rb.colorButton('blue')
			rb.setLabel(win,'blue')

	win.getMouse()

if __name__ == '__main__':
	main()
