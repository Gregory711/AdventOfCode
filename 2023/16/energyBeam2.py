'''
Problem:
Energy beam is going rightwards from the top left corner.
If it hits:
. = empty space then it keeps on going
/ = mirror A while going rightward then it goes upward, while going upward then rightward, leftward then downward, downward then leftward
\ = mirror B while going rightward then it goes downward, leftward then upward, downward then rightward, upward then leftward
| = splitter A such that it hits the flat side then it is split in 2 (up and downward), hits pointy end goes out other pointy end
- = splitter B such that it hits the flat side then it is split in 2 (left and rightward), hits pointy end goes out other pointy end
Beams don't interact with other beams on tile (means we will have to record what direction beams are going on tile to avoid cycles)
Tile is energized if tile has at least one beam pass through it, answer is number of energized tiles.

Classes:
-Cell
 value: '.' or / or \ or | or -
 left, right, up, down: boolean values are default to False, represent directions beams travel through this cell
-Beam
 row, col: int coordinates (0 indexed)
 direction: char 'l' or r or u d for left/right/up/down respectively

Functions:
-getNewDirections
 Params: Beam b
 Returns: char[] directions or None if hits wall
-getNewRow
 Params: int row, char direction
 Returns: int newRow
-getNewCol

Algorithm:
Read in input into 2d array of Cells
Create a list of Beams and add a Beam at 0,0 with direction right to represent the starting beam and mark Cell direction bool
while there are Beams in the list:
  for each Beam in list:
    for directions in getNewDirections for Beam:
      if !Cell has direction:
        mark Cell direction bool
        add new beam to new beam list
  Beam set becomes new beam list
for each Cell:
  if any Cells directions true:
    increment sum
print sum!
'''
import sys
from pydantic import BaseModel, ValidationError, validate_call
from typing import List
from enum import IntEnum

if len(sys.argv) > 1:
	name = sys.argv[1]
else:
	name = 'test.txt'

file1 = open(name, 'r')

class Direction(IntEnum):
	LEFT = 0
	RIGHT = 1
	UP = 2
	DOWN = 3

class Cell(BaseModel):
	value: str
	beamed: List[bool] = [False, False, False, False] #left,right,up,down

class Beam(BaseModel):
	row: int
	col: int
	direction: Direction

# Returns True if coordinates are in bounds, False otherwise
@validate_call
def inBounds(row: int, col: int, graph: List[List[Cell]]):
	if row < 0 or col < 0:
		return False
	elif row >= len(graph) or col >= len(graph[0]):
		return False
	else:
		return True

# Returns new row after applying given direction (ignoring where it is going including if it will hit wall)
@validate_call
def getNewRow(row: int, beamDirection: Direction):
	if beamDirection == Direction.UP:
		return row - 1
	elif beamDirection == Direction.DOWN:
		return row + 1
	else:
		return row

# Returns new col after applying given direction (ignoring where it is going including if it will hit wall)
@validate_call
def getNewCol(col: int, beamDirection: Direction):
	if beamDirection == Direction.LEFT:
		return col - 1
	elif beamDirection == Direction.RIGHT:
		return col + 1
	else:
		return col

# Returns list containing Directions based on beams direction and the value it is hitting (ignoring where it is going including if it will hit wall)
@validate_call
def getNewDirections(beamDirection: Direction, value: str):
	directions = []
	if value == '.':
		directions.append(beamDirection)
	elif value == '|':
		if beamDirection == Direction.UP or beamDirection == Direction.DOWN:
			directions.append(beamDirection)
		else:
			directions.extend([Direction.UP, Direction.DOWN])
	elif value == '-':
		if beamDirection == Direction.LEFT or beamDirection == Direction.RIGHT:
			directions.append(beamDirection)
		else:
			directions.extend([Direction.LEFT, Direction.RIGHT])
	elif value == '/':
		if beamDirection == Direction.RIGHT:
			directions.append(Direction.UP)
		elif beamDirection == Direction.UP:
			directions.append(Direction.RIGHT)
		elif beamDirection == Direction.DOWN:
			directions.append(Direction.LEFT)
		else:
			directions.append(Direction.DOWN)
	else:
		if beamDirection == Direction.RIGHT:
			directions.append(Direction.DOWN)
		elif beamDirection == Direction.UP:
			directions.append(Direction.LEFT)
		elif beamDirection == Direction.DOWN:
			directions.append(Direction.RIGHT)
		else:
			directions.append(Direction.UP)
	return directions

graph = []
for line in file1:
	line = line.rstrip()
	row = []
	for val in line:
		row.append(Cell(value=val))
	graph.append(row)

# Does cleanup i.e. resets all beamed value to False
@validate_call
def cleanup():
	for r in range(len(graph)):
		for c in range(len(graph[r])):
			b = graph[r][c].beamed
			b[0] = b[1] = b[2] = b[3] = False

# Returns number of energized tiles using provided starting beams
@validate_call
def getEnergized(beams: List[Beam]):
	while len(beams) > 0:
		newBeams = []
		for beam in beams:
			directions = getNewDirections(beam.direction, graph[beam.row][beam.col].value)
			#print('getNewDirections(' + str(beam.direction) + ', graph[' + str(beam.row) + '][' + str(beam.col) + '].value) =' + str(directions))
			for direction in directions:
				if not graph[beam.row][beam.col].beamed[direction]:
					graph[beam.row][beam.col].beamed[direction] = True
					newRow = getNewRow(beam.row, direction)
					newCol = getNewCol(beam.col, direction)
					if inBounds(newRow, newCol, graph):
						newBeams.append(Beam(row=newRow, col=newCol, direction=direction))
		beams = newBeams

	sum = 0
	for row in range(len(graph)):
		r = ''
		for col in range(len(graph[row])):
			beamed = graph[row][col].beamed
			if beamed[0] or beamed[1] or beamed[2] or beamed[3]:
				sum += 1
				r += '1'
			else:
				r += '0'
		#print(r)
	cleanup()
	return sum

maxSum = 0
for row in range(len(graph)):
	for col in range(len(graph[row])):
		# If in center of graph skip
		if row > 0 and col > 0 and row < (len(graph) - 1) and col < (len(graph[row]) - 1):
			continue
		# If on leftmost column
		if col == 0:
			beams = [Beam(row=row, col=col, direction=Direction.RIGHT)]
			maxSum = max(maxSum, getEnergized(beams))
		# If on rightmost column
		if col == (len(graph[row]) - 1):
			beams = [Beam(row=row, col=col, direction=Direction.LEFT)]
			maxSum = max(maxSum, getEnergized(beams))
		# If on topmost row
		if row == 0:
			beams = [Beam(row=row, col=col, direction=Direction.DOWN)]
			maxSum = max(maxSum, getEnergized(beams))
		# If on bottommost row
		if row == (len(graph) - 1):
			beams = [Beam(row=row, col=col, direction=Direction.UP)]
			maxSum = max(maxSum, getEnergized(beams))
print(maxSum)
