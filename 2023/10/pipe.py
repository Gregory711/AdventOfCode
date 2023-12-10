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

toVisit = []
toVisit.append((startRow, startColumn))
seen = set()
found = False

while not found:
	visiting = toVisit.pop()
	row = visiting[0]
	col = visiting[1]
	for r in range(-1, 1+1):
		if r == -1 and row == 0:
			continue
		elif r == 1 and row == (len(graph)-1):
			continue
		for c in range(-1, 1+1):
			if c == -1 and col == 0:
				continue
			elif c == 1 and col == (len(graph[r])-1):
				continue
			elif r == 0 and c == 0:
				continue
			print(graph[row+r][col+c])
	found = True # temp test
