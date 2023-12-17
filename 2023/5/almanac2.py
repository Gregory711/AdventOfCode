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
'''
from pydantic import BaseModel, ValidationError, validate_call

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
