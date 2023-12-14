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
