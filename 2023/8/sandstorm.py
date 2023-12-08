import sys

name = sys.argv[1]

file1 = open(name, 'r')

directions = ''
hauntedMap = {}
empty = True

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
print(hauntedMap)
