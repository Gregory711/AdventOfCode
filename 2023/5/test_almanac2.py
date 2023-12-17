import almanac2 as AL

def test_intersecting():
	a = AL.Range(start=1, end=2)
	b = AL.Range(start=2, end=3)
	c = AL.Range(start=3, end=4)
	d = AL.Range(start=1, end=3)
	e = AL.Range(start=1, end=1)
	assert AL.intersecting(a, b) == True
	assert AL.intersecting(a, c) == False
	assert AL.intersecting(a, d) == True
	assert AL.intersecting(b, d) == True
	assert AL.intersecting(a, e) == True
