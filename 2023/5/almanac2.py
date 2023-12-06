# Algorithm:
# 1. Record each initial seed range as tuple in seeds list.
# 2. For each range in each map do the following:
#	a. For each seed range do the following:
#		1. If a portion of the seed range is below the source range from the map then:
#			Add a new range from bottom of seed range (inclusive) to bottom of the
#			source range to a temp seed list.
#		2. If a portion of the seed range is on the source range from the map then:
#			Add a new range from max(bottom of source range, bottom of the seed
#			range) to min(top of source range, top of the seed range) to updated
#			seed list (using adjusted numbers for range!).
#		3. If a portion of the seed range is above the source range from the map then:
#			Add a new range from top of source range (exclusive) to top of the
#			seed range to a temp seed list.
#	b. Replace the old seed seed range with the temp seed list.
#	c. If that was the final map range then add the temp seed list to the update seed list
#		and replace the seed range list with the updated list.
# 3. Iterate over the final seed range and print the lowest lower bound.

import sys

name = sys.argv[1]

file1 = open(name, 'r')

seedRanges = []
inMap = False
startRange = None

for line in file1:
	line = line.rstrip()
	if len(seedRanges) == 0:
		start = line.index(' ') + 1
		while start < len(line):
			try:
				end = line.index(' ', start)
			except ValueError:
				end = len(line)
			if startRange != None:
				seedRanges.append((startRange, startRange + int(line[start:end]) - 1 ))
				startRange = None
			else:
				startRange = int(line[start:end])
			start = end + 1
	elif len(line) == 0:
		if inMap:
			print("exiting map")
			inMap = False
	elif not line[0].isdigit():
		print("entering map")
		inMap = True
		tempSeedRanges = []
		updatedSeedRanges = []
	else:
		print("reading next seed range in map")
		end = line.index(' ')
		destStart = int(line[0:end])
		start = end + 1
		end = line.index(' ', start)
		srcStart = int(line[start:end])
		start = end + 1
		end = len(line)
		rangeLen = int(line[start:end])
		# destStart is the beginning of the numbers that the current seeds are mapped to
		# srcStart is the beginning of the numbers of seeds that are being mapped to new numbers
		# rangeLen is the number of seeds that could be mapped at a maximum in this range
		
		# Iterate over the seed ranges for the map
		for seedRange in seedRanges:
			# Find the portion (if any) of the seed range that is below the map and add it to updatedSeedRanges
			if seedRange[0] < srcStart:
				begin = seedRange[0]
				conclude = min(seedRange[1], srcStart + rangeLen - 1)
				# Adjust for dest mapping
				begin += destStart
				conclude += destStart
				tempSeedRanges.append((begin, conclude))
			# Find the portion (if any) of the seed range that is on the map
			if seedRange[0] >= srcStart and seedRange[1] <= (srcStart + rangeLen - 1):
				begin = max(seedRange[0], srcStart)
				conclude = min(seedRange[1], srcStart + rangeLen - 1)
				# Adjust for dest mapping
				begin += destStart
				conclude += destStart
				updatedSeedRanges.append((begin, conclude))
			# Find the portion (if any) of the seed range that is above the map
			if seedRange[1] > (srcStart + rangeLen - 1):
				begin = max(seedRange[1], srcStart + rangeLen - 1)
				conclude = seedRange[1]
				# Adjust for dest mapping
				begin += destStart
				conclude += destStart
				tempSeedRanges.append((begin, conclude))
print(seedRanges)
'''
	elif len(line) == 0:
		if inMap:
			#print("starting map")
			inMap = False
			for i in range(len(seeds)):
				seeded = False
				for m in maps:
					if not seeded and seeds[i] >= m[1] and seeds[i] < (m[1] + m[2]):
						before = seeds[i]
						seeds[i] = (seeds[i] - m[1]) + m[0]
						seeded = True
						#print(str(before)+" maps to "+str(seeds[i]))
	elif not line[0].isdigit():
		inMap = True
		maps = []
	else:
#print(maps)
for i in range(len(seeds)):
	seeded = False
	for m in maps:
		if not seeded and seeds[i] >= m[1] and seeds[i] < (m[1] + m[2]):
			before = seeds[i]
			seeds[i] = (seeds[i] - m[1]) + m[0]
			seeded = True
#print(seeds)
print(min(seeds))
'''
