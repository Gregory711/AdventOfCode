'''
Start with platform like below:
O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....
The 0s are rocks that can roll, the # are rocks that stay in place, and .s are empty.
If you tilt the platform so all the 0s roll as far north as they can you end you end up with:
OOOO.#.O.. 10
OO..#....#  9
OO..O##..O  8
O..#.OO...  7
........#.  6
..#....#.#  5
..O..#.O.O  4
..O.......  3
#....###..  2
#....#....  1
The numbers on right are their distance to south support beams, multiply it by them and you get 136 the total load and answer!

Algorithm:
Read in input into graph.
Iterate over each column keeping track of highest unblocked empty spot and when encounter 0 swap and update it and update total load.
For each column:
	Keep track of highest unblocked empty space initialized to -1
	Iterate over rows highest to lowest, if cell is:
		#: Set unblocked empty space to -1
		0: Swap with unblocked if not -1 (and iterate from it to here to find new empty space or -1), either way use final location to update total load
		.: Set empty space to if -1 currently
'''
import sys

name = sys.argv[1]

file1 = open(name, 'r')

graph = []
for line in file1:
	row = line.rstrip()
	r = []
	for cell in row:
		r.append(cell)
	graph.append(r)
#print(graph)a

load = 0
for col in range(len(graph[0])):
	unblocked = -1
	for row in range(len(graph)):
		cell = graph[row][col]
		#if col == 0:
			#print(cell)
		if cell == '#':
			#if col == 0:
				#print('Blocked!')
			unblocked = -1
		elif cell == 'O':
			if unblocked != -1:
				graph[row][col] = '.'
				graph[unblocked][col] = 'O'
				load += len(graph) - unblocked
				unblocked += 1
				#if col == 0:
					#print('Rolling stones')
			else:
				load += len(graph) - row
				#print('Stoned')
		elif unblocked == -1:
			unblocked = row
			#if col == 0:
				#print('unblocked!')
		#if col == 0:
			#print(unblocked)
for row in graph:
	r = '';
	for col in row:
		r += col
	#print(r)
print(load)
						
