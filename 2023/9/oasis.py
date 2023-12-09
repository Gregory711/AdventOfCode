'''
test.txt:
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45

Start by adding a row for each row that is made up of the diffs between numbers:
0   3   6   9   12   15
  3   3   3   3    3
Repeat this process until reach row with all zeros:
0   3   6   9  12  15
  3   3   3   3   3
    0   0   0   0
Add a prediction that is result of going up the tree starting with 0 then left + below:
0   3   6   9  12  15  18
  3   3   3   3   3   3
    0   0   0   0   0
So 0 then 0 + 3 = 3 then 3 + 15 = 18
Answer is just sum of the predictions (e.g. 18) for each (in test.txt its 114)
Note: the real input contains negative numbers, is sometimes decreasing, and is sometimes not equal diffs

Algorithm:
Technically do not need to store the entire rows but input data small enough too and might need on part 2 so might as well.
So start out by reading in row into list, will only store for duration of for in loop cycle to simplify things.
The next step will be to create a list of lists (called derived) to add the derived lists to and maintain a flag variable allZeros to know when to stop
The next step will be to just sum the end of each derived list and add it to a total sum which is the answer!
'''

import sys

name = sys.argv[1]

file1 = open(name, 'r')

sum = 0
for line in file1:
	localSum = 0
	values = []
	i = 0
	while i < len(line):
		j = i + 1
		while j < len(line) and line[j].isdigit():
			j += 1
		values.append(int(line[i:j]))
		i = j + 1
	#print(values)
	derived = []
	derived.append(values)
	allZeros = False
	while not allZeros:
		allZeros = True
		diffs = []
		for i in range(len(derived[-1])-1):
			diff = derived[-1][i+1] - derived[-1][i]
			diffs.append(diff)
			if diff != 0:
				allZeros = False
		derived.append(diffs)
	#print(derived) 
	#print(" ")
	for diffs in derived:
		localSum += diffs[-1]
	#print(localSum)
	sum += localSum
print(sum)
