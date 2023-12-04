import sys

name = sys.argv[1]

file1 = open(name, 'r')

class Part:
	def __init__(self, numb, row, startCol, endCol):
		self.numb = numb
		self.row = row
		self.startCol = startCol
		self.endCol = endCol
		self.connected = False

scheme = []

for line in file1:
	scheme.append(line)

parts = []

for i, row in enumerate(scheme):
	numb = ''
	for j, c in enumerate(row):
		if c.isdigit():
			numb += c
		if (not c.isdigit() or j == (len(row) - 1)) and len(numb) > 0:
			parts.append(Part(numb, i, j - len(numb) + 1, j))
			print(parts[len(parts)-1])
			print("Part found on row: " + str(i) + " starting at col: " + str(j - len(numb) + 1) + " and ending at " + str(j))
			numb = ''
