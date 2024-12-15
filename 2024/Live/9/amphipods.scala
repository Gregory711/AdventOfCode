import scala.io.Source
import scala.collection.mutable.Queue

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "disk.txt"
        val lines = Source.fromFile(filename).getLines().toList

        // ASCII addition e.g. '0' - '0' is 0 int, '1' - '0' is 1 int, and so on
        val disk: Array[Int] = lines.head.toCharArray().map(_ - '0')

        var freeSpace: Int = 0
        for (i <- 0 until disk.size if i % 2 != 0) {
            freeSpace = freeSpace + disk(i)
        }

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
        println("Moving: " + moving.mkString(", "))

        println("Initial Disk: " + disk.mkString)
        println("Free space = " + freeSpace)
    }
}