import energyBeam as EB

def test_getNewDirections():
	directions = EB.getNewDirections(EB.Direction.LEFT, '.')
	assert directions == [EB.Direction.LEFT]
