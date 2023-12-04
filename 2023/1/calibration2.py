import sys

name = sys.argv[1]

file1 = open(name, 'r')

sum = 0

# Note: python string slicing is [inclusive, exclusive] so h="Hello" then h[2:]="llo" and h[:2]="He"
def getNumber(seq, index):
	if (len(seq) - index) < 3:
		return -1
	if seq[index:(index+3)] == "one":
		return 1
	elif seq[index:(index+3)] == "two":
		return 2
	elif seq[index:(index+3)] == "six":
		return 6
	if (len(seq) - index) < 4:
		return -1
	elif seq[index:(index+4)] == "four":
		return 4
	elif seq[index:(index+4)] == "five":
		return 5
	elif seq[index:(index+4)] == "nine":
		return 9
	if (len(seq) - index) < 5:
		return -1
	elif seq[index:(index+5)] == "three":
		return 3
	elif seq[index:(index+5)] == "seven":
		return 7
	elif seq[index:(index+5)] == "eight":
		return 8
	else:
		return -1

for line in file1:
	first = None
	i = 0
	for element in line:
		if element.isdigit():
			if first is None:
				first = element
			last = element
		else:
			if first is None and getNumber(line, i) != -1:
				first = str(getNumber(line, i))
			if getNumber(line, i) != -1:
				last = str(getNumber(line, i))
		i += 1
	sum += int(first + last)
print(sum)
