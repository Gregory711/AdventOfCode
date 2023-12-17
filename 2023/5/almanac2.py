'''
Example:
-seeds: 79 14 55 13
 Above describes 14 seeds from 79 to 92 (79+14-1) inclusive and also 55 to 67 so total of 27 seed numbers
-seed-to-soil map:
 50 98  2
 52 50 48
 Above contains two mappings of seed numbers to soil numbers
 The first mapping maps seeds 98 to 99 (98+2-1) to 50 to 51
 Any number that isn't in any of the maps is mapped to itself

Problem is to map all seed numbers from the initial ranges all the way to location numbers and then print the smallest of those.

Algorithm:
Read in seeds as Range obj
Read in mappings as lists of Range objs
'''
from pydantic import BaseModel, ValidationError, validate_call
import sys

class Range(BaseModel):
	start: int # inclusive
	end: int # inclusive

# Returns True if the Range objs have any overlap, False otherwise
@validate_call
def intersecting(a: Range, b: Range):
	if a.end > b.end and a.start <= b.end:
		return True
	elif b.end >= a.end and b.start <= a.end:
		return True
	else:
		return False

# Returns Range consisting of the part of the input range that is 'below' the map range (excluding intersection)
# Assumes that at least part of input range is below map range (below = smaller int)
@validate_call
def belowMap(inputRange: Range, mapRange: Range):
	return Range(start=inputRange.start, end=min(inputRange.end, mapRange.start-1))

# Returns Range consisting of the part of the input range that is 'above' the map range (excluding intersection)
# Assumes that at least part of input range is above map range (above = bigger int)
@validate_call
def aboveMap(inputRange: Range, mapRange: Range):
	return Range(start=max(inputRange.start, mapRange.end+1), end=inputRange.end)

# Returns Range containing the intersection of the Range objs, assumes there is some amount of overlap
@validate_call
def intersect(a: Range, b: Range):
	return Range(start=max(a.start, b.start), end=min(a.end, b.end))

# Returns Range created by mapping the input Range using the map Range, assumes the entire range can be mapped
@validate_call
def mapToDest(inputRange: Range, mapRange: Range):
	diff = mapRange.start - inputRange.start
	return Range(start=inputRange.start+diff, end=inputRange.end+diff)

if len(sys.argv) > 1:
	name = sys.argv[1]
else:
	name = 'test.txt'

file1 = open(name, 'r')

seedRanges = []
mapRanges = []
srcStarts = []
inMap = False

for line in file1:
	line = line.rstrip()
	if len(seedRanges) == 0:
		start = line.index(' ') + 1
		rangeStart = None
		while start < len(line):
			try:
				end = line.index(' ', start)
			except ValueError:
				end = len(line)
			temp = int(line[start:end])
			if rangeStart == None:
				rangeStart = temp
			else:
				seedRanges.append(Range(start=rangeStart, end=rangeStart+temp-1))
				rangeStart = None
			start = end + 1
	elif len(line) == 0:
		inMap = False
	elif line[0].isdigit():
		# Read in destStart, srcStart, and rangeLen
		end = line.index(' ')
		destStart = int(line[0:end])
		start = end + 1
		end = line.index(' ', start)
		srcStart = int(line[start:end])
		start = end + 1
		end = len(line)
		rangeLen = int(line[start:end])

		if not inMap:
			inMap = True
			mapRanges.append([])
			srcStarts.append([])
		mapRanges[-1].append(Range(start=destStart, end=destStart+rangeLen-1))
		srcStarts[-1].append(srcStart)
#print(seedRanges)
#print(mapRanges)
#print(srcStarts)
