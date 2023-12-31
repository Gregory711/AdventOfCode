"""
scratchcards
Elf scratched off a bunch of scratchcards and needs help seeing what he won
Each card has two lists of numbers separated by |
winning numbers then list of numbers you have
first match makes card worth one point
each subsequent match after first doubles the point value of the card!
e.g.
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
worth 8 points
Given a text file find the value of all the cards combined!
test.txt worth 13
"""

import sys

name = sys.argv[1]

file1 = open(name, 'r')

# Iterate over each card
sum = 0
for line in file1:
	line = line.rstrip()
	# Iterate over each winning number
	win = set()
	start = line.index(':') + 2
	while start < len(line) and line[start] != '|':
		while line[start] == ' ':
			start += 1
		end = line.index(' ', start)
		win.add(int(line[start:end]))
		#print(line[start:end])
		start = end + 1
	# Iterate over each selected number
	points = 0
	start = line.index(' ', start) +  1
	while start < len(line):
		while line[start] == ' ':
			start += 1
		try:
			end = line.index(' ', start)
		except ValueError:
			end = len(line)
		if int(line[start:end]) in win:
			points = max(1, points * 2)
		#print(line[start:end])
		start = end + 1
	#print(points)
	sum += points
print(sum)
