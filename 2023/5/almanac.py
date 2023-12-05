import sys

name = sys.argv[1]

file1 = open(name, 'r')

seeds = []
inMap = False

for line in file1:
	line = line.rstrip()
	if len(seeds) == 0:
		start = line.index(' ') + 1
		while start < len(line):
			try:
				end = line.index(' ', start)
			except ValueError:
				end = len(line)
			seeds.append(int(line[start:end]))
			start = end + 1
	elif len(line) == 0:
		if inMap:
			inMap = False
	elif not line[0].isdigit():
		inMap = True
		maps = []
	else:
		end = line.index(' ')
		destStart = int(line[0:end])
		start = end + 1
		end = line.index(' ', start)
		srcStart = int(line[start:end])
		start = end + 1
		end = len(line)
		rangeLen = int(line[start:end])
		maps.append((destStart, srcStart, rangeLen))
#print(maps)
#print(seeds)
