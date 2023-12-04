import sys

name = sys.argv[1]

file1 = open(name, 'r')

class Game:
	def __init__(self, id):
		self.id = id
		self.red = self.green = self.blue = -1

games = []

for line in file1:
	# Start by extracting the game id
	startId = line.index(' ') + 1
	endId = line.index(':')
	gameId = int(line[startId:endId])

	# Create the game object with the given id to store the data
	gameObj = Game(gameId)

	# Process the rest of the string left to right to finish calculating the game object
	i = endId
	#print('the line is: ' + line)
	#print('the line len is: ' + str(len(line)))
	#print('the line len should be: ' + str(len('Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green')))
	# len has to be minus one cause appears to count EOF as determined by above prints
	while i < (len(line) - 1):
		# Start by extracting the number of cubes
		startNumb = line.index(' ', i) + 1
		endNumb = line.index(' ', startNumb)
		numb = int(line[startNumb:endNumb])
		
		# Extract the color of the cubes based on the first character and move i to the right of it
		startColor = endNumb + 1
		if line[startColor] == 'r':
			gameObj.red = max(gameObj.red, numb)
			i = startColor + len('red')
		elif line[startColor] == 'g':
			gameObj.green = max(gameObj.green, numb)
			i = startColor + len('green')
		else:
			gameObj.blue = max(gameObj.blue, numb)
			i = startColor + len('blue')
	# Add the game obj to the games list so it can be queried later
	games.append(gameObj)

# Create Game Object containing possible number of cubes the bag holds of each color
bag = Game(-1)
bag.red = 12
bag.green = 13
bag.blue = 14

# Iterate over the games and calculate the sum of the ids of the ones that comply with the given restrictions
sum = 0
for obj in games:
	#print('red: ' + str(obj.red) + ', green: ' + str(obj.green) + ', blue: ' + str(obj.blue))
	if obj.red <= bag.red and obj.green <= bag.green and obj.blue <= bag.blue:
		#print(obj.id)
		sum += obj.id
print(sum)
