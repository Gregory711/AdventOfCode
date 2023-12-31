'''
Algorithm:
Read in graph
Find S
Depth first search account for the pipe directions to get the path (add to list)
For each row in graph:
	Go left to right maintaining state whether 'in' loop or not, add tiles that are in loop to a list
Use ray casting algorithm to identify each node that is 'inside' the loop
Print the number of cells that are inside the loop
'''

import sys
import math
import copy

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
		pathNodes = tempNodes
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

# pathNodes now contains coordinate tuples for every node in the loop
# will now fill in tiles with coordinate tuple for every node encircled by the loop (but some may squeeze out)

# ray casting algorithm to identify cells inside the loop
# ray casting algorithm works by iterating left to right keeping track of whether or not nodes are in the shape by counting number of edges occurred (even = out)
# Assumes that ray is slightly above points so top edges (F and 7) don't count but bottom (L and J) do (- is ignored)
markedGraph = copy.deepcopy(graph) # stores graph but with pipes replaced with * and inside nodes with @
for node in pathNodes:
	markedGraph[node[0]][node[1]] = '*'
inside = []
for row in range(len(markedGraph)):
	count = 0
	r = []
	for col in range(len(markedGraph[row])):
		r.append(False)
		# Ideally should replace S with actual pipe but for now guessing what it is
		# |, J, and L are the only ones connect north!
		# This works cause consider the following:
		# F------7
		# |      |
		# L--7^^^|
		#    L---J
		# Need to take into account the land highlighted with ^
		# Must either use the one going north (L) or the one going south (7) to know that land exists
		# In other words it is easy to realize that you don't count very top and very bottom edges and to count the ones where you enter |
		# The edges in between where it changes elevation part way through is the hard part, but since they change elevation and must connect
		# to the top and bottom edges there is a north and southbound pipe for each so just account for all north OR southbound connections
		c = graph[row][col]
		if markedGraph[row][col] == '*' and (c == '|' or c == 'J' or c == 'L' or c == 'S'):
			count += 1
		elif markedGraph[row][col] != '*' and (count % 2) == 1:
			r[-1] = True
			markedGraph[row][col] = '@'
	inside.append(r)

insideCount = 0
for row in range(len(inside)):
	for col in range(len(inside[row])):
		if inside[row][col]:
			print(str(row) + ', ' + str(col) + ' is inside!')
			insideCount += 1

print("Original graph:")
for line in graph:
	print(''.join(line))
print("Graph with loop cells replaced with * and cells inside loop replaced with @:")
for line in markedGraph:
	print(''.join(line))
print(insideCount)
