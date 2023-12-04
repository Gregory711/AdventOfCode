import sys

name = sys.argv[1]

file1 = open(name, 'r')

sum = 0

for line in file1:
	first = None
	for element in line:
		if element.isdigit():
			if first is None:
				first = element
			last = element
	sum += int(first + last)
print(sum)
