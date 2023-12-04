import sys
from pydantic import BaseModel

name = sys.argv[1]

file1 = open(name, 'r')


# Add input data to scheme list
scheme = []
for line in file1:
	scheme.append(line.rstrip())


# Iterate over scheme and add gear (row, col, part count, part product, flag) lists to gears list
gears = []
for i, row in enumerate(scheme):
	for j, c in enumerate(row):
		if c == '*':
			gears.append([i, j, 0, 1, False])
			#print("adding gear: "+str(i)+","+str(j)+",0,1")

# Iterate over scheme and add part data to parts list

# Returns list of adjacent character tuple coords, avoids array out of bounds
def getAdjacent(graph, row, col):
	adjacent = []
	for i in range(row-1, row+2):
		for j in range(col-1, col+2):
			# Skip if i or j out of range or at given cell
			if i < 0 or j < 0 or i == len(graph) or j == len(graph[i]) or (i == row and j == col):
				continue
			adjacent.append((i, j))
	return adjacent

class Part(BaseModel):
	numb: int
	row: int
	startCol: int
	endCol: int
	connected: bool

# Iterate over scheme and add part data to parts list
parts = []
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

# Iterate over parts and update relevant gear tuples
for part in parts:
	for gear in gears:
		gear[4] = False
	for col in range(part.startCol, part.endCol+1):
		adjacent = getAdjacent(scheme, part.row, col)
		for coord in adjacent:
			if scheme[coord[0]][coord[1]] == '*':
				for i in range(len(gears)):
					if gears[i][0] == coord[0] and gears[i][1] == coord[1] and not gears[i][4]:
						gears[i][2] += 1
						gears[i][3] *= part.numb
						gears[i][4] = True
						#print("gear:")
						#print(gears[i])
						#print("connect to part:")
						#print(part)

# Sum all of the gear ratios that only connect to exactly 2 parts
sum = 0
for gear in gears:
	if gear[2] == 2:
		sum += gear[3]
print(sum)
