import almanac2 as AL

a = AL.Range(start=1, end=2)
b = AL.Range(start=2, end=3)
c = AL.Range(start=3, end=4)
d = AL.Range(start=1, end=3)
e = AL.Range(start=1, end=1)
seeds = AL.Range(start=50, end=51)
soilMap = AL.Range(start=98, end=99)

def test_intersecting():
	assert AL.intersecting(a, b) == True
	assert AL.intersecting(a, c) == False
	assert AL.intersecting(a, d) == True
	assert AL.intersecting(b, d) == True
	assert AL.intersecting(a, e) == True

def test_belowMap():
	assert AL.belowMap(a, b) == AL.Range(start=1, end=1)
	assert AL.belowMap(a, c) == AL.Range(start=1, end=2)

def test_aboveMap():
	assert AL.aboveMap(b, a) == AL.Range(start=3, end=3)
	assert AL.aboveMap(c, a) == AL.Range(start=3, end=4)

def test_intersect():
	assert AL.intersect(a, b) == AL.Range(start=2, end=2)
	assert AL.intersect(a, d) == AL.Range(start=1, end=2)
	assert AL.intersect(a, e) == AL.Range(start=1, end=1)

def test_mapToDest():
	assert AL.mapToDest(seeds, soilMap, 50) == soilMap
