'''
Algorithm:
Read in the initialization sequence
Iterate over each comma separated value in sequence
For each char in the value:
	Increase the values current value by the ASCII value of the char
	Multiply the current value by 17
	Set current value to itself modulus 256
Print the sum of current values for each value
'''
import sys

name = sys.argv[1]

file1 = open(name, 'r')

input = None
for line in file1:
	input = line.rstrip()

start = sum = 0
while start < len(input):
	try:
		end = input.index(',', start)
	except ValueError:
		end = len(input)
	value = input[start:end]
	current = 0
	for char in value:
		current += ord(char)
		current *= 17
		current %= 256
	sum += current
	start = end + 1
print(sum)
