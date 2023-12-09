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
'''
