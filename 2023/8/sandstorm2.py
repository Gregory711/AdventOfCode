'''
Algorithm:
Read in directions into dictionary mapping origins to left and right destinations keeping track of all staring (A) positions
For each starting position:
	while not found:
		increment step count
		update current location to direction
		if new current locations ends with Z update found
	append step count to stepCounts
Print least common multiple of stepCounts
'''
import sys

name = sys.argv[1]

file1 = open(name, 'r')

directions = ''
hauntedMap = {}
empty = True
locs = []

for line in file1:
	line = line.rstrip()
	if len(directions) == 0:
		directions = line
		continue
	elif empty:
		empty = False
		continue
	# Read in the map to get out of the haunted wasteland!
	i = line.index(' ')
	origin = line[0:i]
	i = line.index('(') + 1
	j = line.index(',')
	left = line[i:j]
	i = line.index(' ', i) + 1
	j = line.index(')')
	right = line[i:j]
	hauntedMap[origin] = (left, right)
	if origin[-1] == 'A':
		locs.append(origin)
#print(hauntedMap)
#print(locs)

'''
found = False
steps = 0

while not found:
	for step in directions:
		steps += 1
		found = True
		for i in range(len(locs)):
			if step == 'L':
				locs[i] = hauntedMap[locs[i]][0]
			else:
				locs[i] = hauntedMap[locs[i]][1]
			if locs[i][-1] != 'Z':
				found = False
		if found:	
			break
print(steps)
'''
