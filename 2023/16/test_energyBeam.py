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
