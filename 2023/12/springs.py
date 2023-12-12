'''
Problem statement:
Given rows like of the following format:
???.### 1,1,3
Where '.' represent operational springs
      '#' represent damaged springs
      '?' represent spring of unknown condition
And the numbers at the end represent contiguous groups of damaged springs in order
So the given row has to be '#.#.###'
Most rows however have a large number of possible configurations.
The problem is to output sum of the possible configurations for all rows.

Algorithm:
Start by generating all permutations of the numbers of contiguous damaged groups.
Then don't add permutations that either:
1. Would break continuity of the groups
2. Are already counted as permutations e.g. just swapped 1 and 1 in the order
'''
