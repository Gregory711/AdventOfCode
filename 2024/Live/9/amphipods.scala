import scala.io.Source
import scala.collection.mutable.Queue

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "disk.txt"
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
            toMove = toMove - disk(i - 1)
            i = i - 2
        }
        toMove = moving.size
        //println("Moving: " + moving.mkString(", "))

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
        for
            j <- 0 until toMove
        do
            wideDisk(wideDisk.size - j - 1) = -1
        println(wideDisk.mkString(" "))

        println("Initial Disk: " + disk.mkString)
        println("Free space = " + freeSpace)
    }
}