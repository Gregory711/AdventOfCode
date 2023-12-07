import sys

name = sys.argv[1]

file1 = open(name, 'r')

for line in file1:
	space = line.index(' ')
	hand = line[0:space]
	bid = int(line[(space+1):len(line)])
	print(hand + ", " + str(bid))
