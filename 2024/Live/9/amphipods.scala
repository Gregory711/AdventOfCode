import scala.io.Source
import scala.collection.mutable.Queue

object Main {
    def getPart1Checksum(disk: Array[Int], fileSpace: Int, freeSpace: Int): Long = {
        // Figure out how much of the freespace can be filled and with what files
        // To do so need to find freespace going left to right while also
        // finding files to fill it in going right to left
        // If at any point the files looked at for filling are to the left of the freespace needing
        // filling then have to quit as we can only move files to the left
        val fileBlocksToMove: Queue[Int] = Queue[Int]()
        var freeSpaceToFill: Int = freeSpace
        var freePtr: Int = 1
        var spaceLeftInFreeBlock: Int = disk(freePtr)
        var filePtr: Int = disk.size -1
        // Make sure it starts on file not freespace
        if (filePtr % 2 != 0) {
            filePtr = filePtr - 1
        }
        while (freeSpaceToFill > 0 && freePtr < filePtr) {
            val fileSize: Int = disk(filePtr)
            for
                j <- 0 until math.min(fileSize, freeSpaceToFill) if freePtr < filePtr
            do
                fileBlocksToMove.enqueue(filePtr / 2) // id
                spaceLeftInFreeBlock = spaceLeftInFreeBlock - 1
                if (spaceLeftInFreeBlock == 0) {
                    freePtr = freePtr + 2
                    spaceLeftInFreeBlock = disk(freePtr)
                }
                freeSpaceToFill = freeSpaceToFill - 1
            filePtr = filePtr - 2
        }
        //println("Moving: " + fileBlocksToMove.mkString(" "))
        val fileBlockMoveCount: Int = fileBlocksToMove.size

        // Create disk with space expanded and fill it with correct ids
        val wideDisk: Array[Int] = new Array[Int](fileSpace + freeSpace)
        var diskPtr: Int = 0
        for
            j <- 0 until disk.size
        do
            if (j % 2 == 0) {
                for
                    k <- 0 until disk(j)
                do
                    wideDisk(diskPtr) = j / 2 // id
                    diskPtr = diskPtr + 1
            } else {
                for
                    k <- 0 until disk(j)
                do
                    // Fill free space with files from the rightmost space if available
                    if (!fileBlocksToMove.isEmpty) {
                        wideDisk(diskPtr) = fileBlocksToMove.dequeue()
                    } else {
                        wideDisk(diskPtr) = -1
                    }
                    diskPtr = diskPtr + 1
            }
        // Set original locations of files moved to the left to -1 to represent free space
        var skipFrees: Int = 0
        for
            j <- 0 until fileBlockMoveCount
        do
            var k: Int = wideDisk.size - j - 1 - skipFrees
            while (wideDisk(k) == -1) {
                skipFrees = skipFrees + 1
                k = k - 1
            }
            wideDisk(k) = -1
        //println(wideDisk.mkString(" "))

        // Calculate checksum for the new file structure
        var sum: Long = 0
        for
            j <- 0 until wideDisk.size if wideDisk(j) > 0
        do
            sum = sum + (j * wideDisk(j))

        //println("Initial Disk: " + disk.mkString)
        //println("Free space = " + freeSpace)
        return sum
    }

    def main(args: Array[String]): Unit = {
        val filename = "disk.txt"
        /*val filename = "randA.txt" part 1 checksum should be 624, proof:
            9 9 2 0 2 0 9 5 2 4
              -   -   -   -   -
            9.........229.....2....
            0.........1122333333333.....44
            000000000443333333112233..............
            4*9 + 4*10 + 3*11 + 3*12 + 3*13 + 3*14 + 3*15 + 3*16 + 3*17 + 1*18 + 1*19 + 2*20 + 2*21 + 3*22 + 3*23 = 624 */
        val lines = Source.fromFile(filename).getLines().toList

        // ASCII addition e.g. '0' - '0' is 0 int, '1' - '0' is 1 int, and so on
        val disk: Array[Int] = lines.head.toCharArray().map(_ - '0')

        var fileSpace: Int = 0
        for (i <- 0 until disk.size if i % 2 == 0) {
            fileSpace = fileSpace + disk(i)
        }

        var freeSpace: Int = 0
        for (i <- 0 until disk.size if i % 2 != 0) {
            freeSpace = freeSpace + disk(i)
        }

        println("Part 1 checksum of new file structure: " + getPart1Checksum(disk, fileSpace, freeSpace))
    }
}