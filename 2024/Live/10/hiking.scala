import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Main {
    def printMap(map: ArrayBuffer[Array[Int]]): Unit = {
        println("Map: ")
        map.foreach(line =>
            println(line.mkString)
        )
    }

    def main(args: Array[String]): Unit = {
        val filename = "map.txt"
        val lines = Source.fromFile(filename).getLines().toList

        // Read in input
        var map = ArrayBuffer[Array[Int]]()
        lines.foreach(line =>
            map.append(line.toCharArray().map(_ - '0'))
        )
        printMap(map)
    }
}