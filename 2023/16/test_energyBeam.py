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
