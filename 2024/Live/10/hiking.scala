import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashSet

object Main {
    /**
      *  _____ x
      * |1 2
      * |3 4
      * |
      * y
      * 
      * Coordinate(x,y), (0,0) = 1, (1, 0) = 2, (0, 1) = 3, (1, 1) = 4
      *
      * @param x - horizontal e.g. leftmost = 0
      * @param y - vertical e.g. topmost = 0
      */
    case class Coordinate(x: Int, y: Int) {
        // override methods so can use hashset
        override def hashCode(): Int = (x, y).hashCode()

        override def equals(obj: Any): Boolean = obj match {
            case other: Coordinate => this.x == other.x && this.y == other.y
            case _ => false
        }
    }

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
        val map = ArrayBuffer[Array[Int]]()
        lines.foreach(line =>
            map.append(line.toCharArray().map(_ - '0'))
        )

        // Read in trailheads
        val trailheads = ArrayBuffer[Coordinate]()
        for ((row, y) <- map.zipWithIndex) {
            for ((elevation, x) <- row.zipWithIndex) {
                if (elevation == 0) {
                    trailheads.append(Coordinate(x, y))
                }
            }
        }

        printMap(map)
        println("Trailheads: " + trailheads.mkString(", "))
    }
}