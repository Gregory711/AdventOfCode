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

result = 1
for race in range(len(times)):
	ways = 0
	for speed in range(times[race]):
		dist = speed * (times[race] - speed)
		#print(str(speed) + " speed times " + str(times[race]-speed) + " time equals dist " + str(dist))
		if dist > dists[race]:
			ways += 1
		#print(str(ways) + " ways to beat record is now")
	result *= ways
	#print("results is now " + str(result))
print(result)
