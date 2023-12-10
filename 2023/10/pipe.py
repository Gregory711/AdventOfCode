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
print(startRow)
print(startColumn)
