'''
First step will be to read in the galaxy coordinates as lists
Double increment if row doesn't have any galaxies
Once that is done make a list of all columns that don't have galaxies by iterating over coordinates
Iterate over no galaxy column list and for each increment all galaxy coordinates with col greater and
increment adjustment to use to calculate the next no galaxy column
Iterate over each row and make a list containing number of galaxies in each row
Iterate over each row again and use number list to calculate row part of solution
Repeat process for column
Print answer!
'''

import sys

name = sys.argv[1]

file1 = open(name, 'r')

galaxies = []
emptyRows = 0
for row, line in enumerate(file1):
	rowIsEmpty = True
	for col, char in enumerate(line):
		if char == '#':
			galaxies.append([row+emptyRows, col])
			rowIsEmpty = False
	if rowIsEmpty:
		emptyRows += 1
print(galaxies)
