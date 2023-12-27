'''
Algorithm:
Read in graph
Find S
Depth first search account for the pipe directions to get the path (add to list)
For each row in graph:
	Go left to right maintaining state whether 'in' loop or not, add tiles that are in loop to a list
Use ray casting algorithm to identify each node that is 'inside' the loop (not accounting for squeezing out)
Iterate over every cell and for each that is inside the loop check if it is next to a loop node and if so:
	Check if the animal can squeeze through the neighboring loop nodes and if so:
		Use the flood fill algorithm to mark this cell and all connecting ones that are inside the loop as outside it
Print the number of cells that are still classified as inside the loop
'''

import sys
import math

name = sys.argv[1]

file1 = open(name, 'r')

graph = []

for line in file1:
	line = line.rstrip()
	row = []
	for node in line:
		row.append(node)
	graph.append(row)
#print(graph)

startRow = startColumn = None

for r in range(len(graph)):
	for c in range(len(graph[r])):
		if graph[r][c] == 'S':
			startRow = r
			startColumn = c
#print(startRow)
#print(startColumn)

# Returns True if valid coordinate (e.g. not -1) and False otherwise
def inBounds(graph, row, col):
	if row < 0 or col < 0:
		return False
	elif row >= len(graph):
		return False
	elif col >= len(graph[row]):
		return False
	else:
		return True

def getLongestPath(graph, toVisit):
	longestPath = -1
	pathNodes = []
	while len(toVisit) > 0:
		visiting = toVisit.pop()
		row = visiting[0]
		col = visiting[1]
		pathLen = pathLens[visiting]

		# Upsert node to pathNodes
		if len(pathNodes) > 0:
			while len(pathNodes) > 0 and pathLens[pathNodes[-1]] >= pathLen:
				pathNodes.pop()
		pathNodes.append(visiting)


		#print("Visiting: ("+str(row)+", "+str(col)+")")
		# Changing current node to ❌ for testing temporarily
		'''
		temp = graph[row][col]
		graph[row][col] = '❌'
		for line in graph:
			print(''.join(line))
		graph[row][col] = temp
		print("Path len:" + str(pathLen))
		print("")
		'''

		above = below = left = right = False

		# | = vertical pipe = connects nodes above and below
		if graph[row][col] == '|':
			above = below = True
		# - = horizontal pipe = connects nodes left and right
		if graph[row][col] == '-':
			left = right = True
		# L = 90 degree bend = connects above and right
		if graph[row][col] == 'L':
			above = right = True
		# J = 90 degree bend = connects above and left
		if graph[row][col] == 'J':
			above = left = True
		# 7 = 90 degree bend = connects below and left
		if graph[row][col] == '7':
			below = left = True
		# F = 90 degree bend = connects below and right
		if graph[row][col] == 'F':
			below = right = True
		# . = ground (no pipe)
		# S = start = finished loop
		if graph[row][col] == 'S' and pathLen > longestPath:
			longestPath = pathLen
			#print("found path with len: " + str(pathLen))

		abovePipe = belowPipe = leftPipe = rightPipe = None
		if inBounds(graph, row-1, col):
			abovePipe = graph[row-1][col]
		if inBounds(graph, row+1, col):
			belowPipe = graph[row+1][col]
		if inBounds(graph, row, col-1):
			leftPipe = graph[row][col-1]
		if inBounds(graph, row, col+1):
			rightPipe = graph[row][col+1]

		if above and abovePipe != None and (row-1, col) not in seen and (abovePipe == '|' or abovePipe == '7' or abovePipe == 'F' or abovePipe == 'S'):
			toVisit.append((row-1, col))
			pathLens[(row-1, col)] = pathLen+1
			seen.add(toVisit[-1])
		if below and belowPipe != None and (row+1, col) not in seen and (belowPipe == '|' or belowPipe == 'L' or belowPipe == 'J' or belowPipe == 'S'):
			toVisit.append((row+1, col))
			pathLens[(row+1, col)] = pathLen+1
			seen.add(toVisit[-1])
		if left and leftPipe != None and (row, col-1) not in seen and (leftPipe == '-' or leftPipe == 'L' or leftPipe == 'F' or leftPipe == 'S'):
			toVisit.append((row, col-1))
			pathLens[(row, col-1)] = pathLen+1
			seen.add(toVisit[-1])
		if right and rightPipe != None and (row, col+1) not in seen and (rightPipe == '-' or rightPipe == 'J' or rightPipe == '7' or rightPipe == 'S'):
			toVisit.append((row, col+1))
			pathLens[(row, col+1)] = pathLen+1
			seen.add(toVisit[-1])
		if (startRow, startColumn) in seen:
			seen.remove((startRow, startColumn))
	return longestPath, pathNodes

# Add initial pipes off start
path = -1
toVisit = []
pathNodes = []
pathLens = {}
seen = set()
row = startRow
col = startColumn
if inBounds(graph, row-1, col):
	toVisit.append((row-1, col))
	pathLens[(row-1, col)] = 0
	seen.add(toVisit[-1])
	tempPath, tempNodes = getLongestPath(graph, toVisit)
	if tempPath > path:
		path = tempPath
		pathNodes = tempNodes
#print("finished up")
pathLens = {}
seen = set()
if inBounds(graph, row+1, col):
	toVisit.append((row+1, col))
	pathLens[(row+1, col)] = 0
	seen.add(toVisit[-1])
	tempPath, tempNodes = getLongestPath(graph, toVisit)
	if tempPath > path:
		path = tempPath
		pathNodes = tempNodes
#print("finished down")
pathLens = {}
seen = set()
if inBounds(graph, row, col-1):
	toVisit.append((row, col-1))
	pathLens[(row, col-1)] = 0
	seen.add(toVisit[-1])
	tempPath, tempNodes = getLongestPath(graph, toVisit)
	if tempPath > path:
		path = tempPath
		pathNodes = tempNode
#print("finished left")
pathLens = {}
seen = set()
if inBounds(graph, row, col+1):
	toVisit.append((row, col+1))
	pathLens[(row, col+1)] = 0
	seen.add(toVisit[-1])
	tempPath, tempNodes = getLongestPath(graph, toVisit)
	if tempPath > path:
		path = tempPath
		pathNodes = tempNode
#print("finished right")
#print(math.ceil(path / 2))
#print(pathNodes)

# pathNodes now contains coordinate tuples for every node in the loop
# will now fill in tiles with coordinate tuple for every node encircled by the loop (but some may squeeze out)
tiles = []
print(tiles)
