'''
Problem:
Energy beam is going rightwards from the top left corner.
If it hits:
. = empty space then it keeps on going
/ = mirror A while going rightward then it goes upward, while going upward then rightward, leftward then downward, downward then leftward
\ = mirror B while going rightward then it goes downward, leftward then upward, downward then rightward, upward then leftward
| = splitter A such that it hits the flat side then it is split in 2 (up and downward), hits pointy end goes out other pointy end
- = splitter B such that it hits the flat side then it is split in 2 (left and rightward), hits pointy end goes out other pointy end
Beams don't interact with other beams on tile (means we will have to record what direction beams are going on tile to avoid cycles)
Tile is energized if tile has at least one beam pass through it, answer is number of energized tiles.
'''
