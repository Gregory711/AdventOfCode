"""
scratchcards
Elf scratched off a bunch of scratchcards and needs help seeing what he won
Each card has two lists of numbers separated by |
winning numbers then list of numbers you have
each match wins you a copy of the next scratchcard below
e.g.
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
has 4 match numbers so win one copy of each 2,3,4,5 cards
Remember to calculate all original and copy cards
Given a text file find how many cards in total are evaluated!
test.txt worth 13
"""

import sys

name = sys.argv[1]

file1 = open(name, 'r')

# Iterate over each card
sum = 0
card = 1
copies = {}
for line in file1:
	if not card in copies:
		copies[card] = 1
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
	cards = 0
	matchCount = 0
	start = line.index(' ', start) +  1
	while start < len(line):
		while line[start] == ' ':
			start += 1
		try:
			end = line.index(' ', start)
		except ValueError:
			end = len(line)
		if int(line[start:end]) in win:
			cards += copies[card]
			matchCount += 1
			#print("Card: "+str(card)+" hit match #"+str(matchCount)+" increasing its copies to "+str(cards))
			if not (card + matchCount) in copies:
				copies[card+matchCount] = 1 + copies[card]
			else:
				copies[card+matchCount] += copies[card]
		#print(line[start:end])
		start = end + 1
	sum += cards + 1
	card += 1
print(sum)
