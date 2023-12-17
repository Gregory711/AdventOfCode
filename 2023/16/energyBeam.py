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
	return directions

graph = []
for line in file1:
	line = line.rstrip()
	row = []
	for val in line:
		row.append(Cell(value=val))
	graph.append(row)

beams = []
beams.append(Beam(row=0,col=0,direction=Direction.RIGHT))
graph[0][0].beamed[Direction.RIGHT] = True
