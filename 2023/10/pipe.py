# Algorithm:
# Read in graph
# Find S
# Depth first search accounting for the pipe directions to get the path
# Return half of the distance of the path rounding up e.g. in test data it would be ceil(7/2)=4

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

# Add initial pipes off start
toVisit = []
seen = set()
row = startRow
col = startColumn
if inBounds(graph, row-1, col):
	toVisit.append((row-1, col))
	seen.add(toVisit[-1])
if inBounds(graph, row+1, col):
	toVisit.append((row+1, col))
	seen.add(toVisit[-1])
if inBounds(graph, row, col-1):
	toVisit.append((row, col-1))
	seen.add(toVisit[-1])
if inBounds(graph, row, col+1):
	toVisit.append((row, col+1))
	seen.add(toVisit[-1])

pathLen = 0
longestPath = -1

while len(toVisit) > 0:
	pathLen += 1
	visiting = toVisit.pop()
	row = visiting[0]
	col = visiting[1]

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
	
	if above and inBounds(graph, row-1, col) and (row-1, col) not in seen:
		toVisit.append((row-1, col))
		seen.add(toVisit[-1])
	if below and inBounds(graph, row+1, col) and (row+1, col) not in seen:
		toVisit.append((row+1, col))
		seen.add(toVisit[-1])
	if left and inBounds(graph, row, col-1) and (row, col-1) not in seen:
		toVisit.append((row, col-1))
		seen.add(toVisit[-1])
	if right and inBounds(graph, row, col+1) and (row, col+1) not in seen:
		toVisit.append((row, col+1))
		seen.add(toVisit[-1])
print(math.ceil(longestPath / 2))
