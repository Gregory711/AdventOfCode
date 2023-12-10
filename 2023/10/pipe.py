# Algorithm:
# Read in graph
# Find S
# Depth first search accounting for the pipe directions to get the path
# Return half of the distance of the path rounding up e.g. in test data it would be ceil(7/2)=4

import sys

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

# Returns list of tuples of format (row, col) that are in bounds and surround given coordinate
def getAdjacent(graph, row, col):
	adj = []
	for i in range(-1, 1+1):
		for j in range(-1, 1+1):
			r = row+i
			c = col+j
			if inBounds(graph, r, c):
				adj.append((r, c))
	return adj

# Add initial pipes off start
toVisit = getAdjacent(graph, startRow, startColumn)
seen = set()
found = False
pathLen = 0

while not found:
	pathLen += 1
	visiting = toVisit.pop()

	# | = vertical pipe = connects nodes above and below
	# - = horizontal pipe = connects nodes left and right
	# L = 90 degree bend = connects above and right
	# J = 90 degree bend = connects above and left
	# 7 = 90 degree bend = connects below and left
	# F = 90 degree bend = connects below and right
	# . = ground (no pipe)

	found = True # temp test
