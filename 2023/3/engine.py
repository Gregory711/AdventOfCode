import sys
from pydantic import BaseModel

name = sys.argv[1]

file1 = open(name, 'r')

# Returns list of adjacent characters, avoids array out of bounds
def getAdjacent(graph, row, col):
	adjacent = []
	for i in range(row-1, row+2):
		for j in range(col-1, col+2):
			# Skip if i or j out of range or at given cell
			if i < 0 or j < 0 or i == len(graph) or j == len(graph[i]) or (i == row and j == col):
				continue
			adjacent.append(graph[i][j])
	return adjacent

class Part(BaseModel):
	numb: int
	row: int
	startCol: int
	endCol: int
	connected: bool

scheme = []

# Add input data to scheme list
for line in file1:
	scheme.append(line.rstrip())

parts = []

# Iterate over scheme and add part data to parts list
for i, row in enumerate(scheme):
	numb = ''
	for j, c in enumerate(row):
		if c.isdigit():
			numb += c
		if (not c.isdigit() or j == (len(row) - 1)) and len(numb) > 0:
			#print("j: " + str(j) + ", len(numb): " + str(len(numb)))
			if c.isdigit():
				start = j - len(numb) + 1
				end = j
			else:
				start = j - len(numb)
				end = j - 1
			parts.append(Part(numb=int(numb), row=i, startCol=start, endCol=end, connected=False))
			#print(parts[len(parts)-1])
			numb = ''

# Iterate over parts and set connected for parts that are adjacent to symbols other than . and digits
for part in parts:
	for col in range(part.startCol, part.endCol+1):
		adjacent = getAdjacent(scheme, part.row, col)
		#print(adjacent)
		for c in adjacent:
			if not c.isdigit() and c != '.':
				part.connected = True

# Iterate over parts that are connected to symbols other than digits and . and print sum
sum = 0
for part in parts:
	if part.connected:
		#print(part.numb)
		sum += part.numb
print(sum)
