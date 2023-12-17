import energyBeam as EB

def test_getNewDirections():
	directions = EB.getNewDirections(EB.Direction.LEFT, '.')
	assert directions == [EB.Direction.LEFT]
	directions = EB.getNewDirections(EB.Direction.UP, '|')
	assert directions == [EB.Direction.UP]
	directions = EB.getNewDirections(EB.Direction.LEFT, '-')
	assert directions == [EB.Direction.LEFT]
	# Split up and down
	directions = EB.getNewDirections(EB.Direction.RIGHT, '|')
	assert directions == [EB.Direction.UP, EB.Direction.DOWN]
	# Split left and right
	directions = EB.getNewDirections(EB.Direction.DOWN, '-')
	assert directions == [EB.Direction.LEFT, EB.Direction.RIGHT]
	# Split /
	directions = EB.getNewDirections(EB.Direction.RIGHT, '/')
	assert directions == [EB.Direction.UP]
	# Split \
	directions = EB.getNewDirections(EB.Direction.RIGHT, '\\')
	assert directions == [EB.Direction.DOWN]

def test_getNewRow():
	assert EB.getNewRow(0, EB.Direction.RIGHT) == 0
	assert EB.getNewRow(0, EB.Direction.UP) == -1

def test_getNewCol():
	assert EB.getNewCol(0, EB.Direction.RIGHT) == 1
	assert EB.getNewCol(0, EB.Direction.UP) == 0

def test_inBounds():
	graph = []
	graph.append([EB.Cell(value='.')])
	assert EB.inBounds(0, 0, graph) == True
	assert EB.inBounds(0, 1, graph) == False
	assert EB.inBounds(1, 0, graph) == False
	assert EB.inBounds(-1, 0, graph) == False
	assert EB.inBounds(0, -1, graph) == False
