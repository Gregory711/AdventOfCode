import sys

name = sys.argv[1]

file1 = open(name, 'r')

times = []
dists = []
readingTimes = False

for line in file1:
	line = line.rstrip()
	readingTimes = not readingTimes
	i = 0
	numb = ''
	while i < len(line):
		if line[i].isdigit():
			numb += line[i]
		i += 1
	if readingTimes:
		times.append(int(numb))
	else:
		dists.append(int(numb))

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
