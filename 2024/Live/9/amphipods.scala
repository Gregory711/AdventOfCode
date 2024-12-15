import scala.io.Source
import scala.collection.mutable.Queue

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "disk.txt"
        /*val filename = "randA.txt" checksum should be 624, proof:
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

        // Figure out how much of the freespace can be filled and with what files
        // To do so need to find freespace going left to right while also
        // finding files to fill it in going right to left
        // If at any point the files looked at for filling are to the left of the freespace needing
        // filling then have to quit as we can only move files to the left

        // OLD approach (new approach being made above)
        // Figure out what files need to be moved left into freespace by file id
        val moving: Queue[Int] = Queue[Int]()
        var toMove: Int = freeSpace
        var i: Int = disk.size - 1
        // Make sure it starts on file not freespace
        if (i % 2 != 0) {
            i = i - 1
        }
        while (toMove > 0) {
            val fileSize: Int = disk(i)
            for
                j <- 0 until math.min(fileSize, toMove)
            do
                moving.enqueue(i / 2) // i / 2 = id
            toMove = toMove - fileSize
            // Makes sure you don't move files to the right to fill freespace
            toMove = toMove - disk(i - 1) // this is the problem line
            i = i - 2
        }
        toMove = moving.size
        println("Moving: " + moving.mkString(", "))

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
                    if (!moving.isEmpty) {
                        wideDisk(diskPtr) = moving.dequeue()
                    } else {
                        wideDisk(diskPtr) = -1
                    }
                    diskPtr = diskPtr + 1
            }
        // Set original locations of files moved to the left to -1 to represent free space
        var skipFrees: Int = 0
        for
            j <- 0 until toMove
        do
            var k: Int = wideDisk.size - j - 1 - skipFrees
            while (wideDisk(k) == -1) {
                skipFrees = skipFrees + 1
                k = k - 1
            }
            wideDisk(k) = -1
        println(wideDisk.mkString(" "))

        // Calculate checksum for the new file structure
        var sum: Int = 0
        for
            j <- 0 until wideDisk.size if wideDisk(j) > 0
        do
            sum = sum + (j * wideDisk(j))

        //println("Initial Disk: " + disk.mkString)
        println("Free space = " + freeSpace)
        println("Checksum of new file structure: " + sum)
    }
}