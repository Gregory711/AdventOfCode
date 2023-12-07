import sys
from functools import cmp_to_key

name = sys.argv[1]

file1 = open(name, 'r')

hands = []

for line in file1:
	space = line.index(' ')
	hand = line[0:space]
	bid = int(line[(space+1):len(line)])
	#print(hand + ", " + str(bid))
	hands.append((hand, bid))

def handType(hand):
	counts = {}
	for card in hand:
		if card not in counts:
			counts[card] = 1
		else:
			counts[card] += 1
	# If J is the most common card type it pretends to be the next most common card
	# Otherwise J pretends to be the most common card type
	jCount = 0
	if 'J' in counts and len(counts) > 1:
		jCount = counts['J']
		del counts['J']
	counts = dict(sorted(counts.items(), key=lambda item: item[1], reverse=True))
	countsOnly = list(counts.values())
	countsOnly[0] += jCount
	#print(counts)
	if countsOnly[0] == 5:
		return 7 # Five of a kind
	elif countsOnly[0] == 4:
		return 6 # Four of a kind
	elif countsOnly[0] == 3 and countsOnly[1] == 2:
		return 5 # Full house
	elif countsOnly[0] == 3:
		return 4 # Three of a kind
	elif countsOnly[0] == 2 and countsOnly[1] == 2:
		return 3 # Two pair
	elif countsOnly[0] == 2:
		return 2 # One pair
	else:
		return 1 # High card

def cardValue(card):
	if card.isdigit():
		return int(card)
	if card == 'A':
		return 14
	elif card == 'K':
		return 13
	elif card == 'Q':
		return 12
	elif card == 'J':
		return 0
	elif card == 'T':
		return 10
	else:
		print("Danger Will Robinson:" + card)
		return -1

def compare(hand1, hand2):
	hand1 = hand1[0]
	hand2 = hand2[0]
	hand1Bigger = False
	if handType(hand1) > handType(hand2):
		hand1Bigger = True
	elif handType(hand1) == handType(hand2):
		i = 0
		#print("hand1:"+str(hand1))
		#print("hand1[0]:"+hand1[0])
		while hand1[i] == hand2[i]:
			i += 1
		if cardValue(hand1[i]) > cardValue(hand2[i]):
			hand1Bigger = True
	if not hand1Bigger:
		return -1
	elif hand1Bigger:
		return 1
	else:
		return 0

#hands.sort(key=compare)
hands = sorted(hands, key=cmp_to_key(compare))
#print(hands)
sum = 0
for i in range(len(hands)):
	#print(str(hands[i]))
	sum += (i + 1) * hands[i][1]
print(str(sum))
