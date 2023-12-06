import sys

name = sys.argv[1]

file1 = open(name, 'r')

times = []
dists = []
readingTimes = False

for line in file1:
	line = line.rstrip()
	readingTimes = not readingTimes
	# Read in the times
	i = 0
	start = None
	while i < len(line):
		if start == None and line[i].isdigit():
			start = i
		if start != None and (not line[i].isdigit() or i == (len(line) - 1)):
			if readingTimes:
				times.append(int(line[start:(i+1)]))
			else:
				dists.append(int(line[start:(i+1)]))
			start = None
		i += 1
print(times)
print(dists)
