import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap

object Main {
    // Just to clarify each row of roof has different x starting with 0 (top/first line)
    // Each column has y starting with 0 leftmost
    case class Coordinate(x: Int, y: Int)

    def square(a: Double): Double = {
        return a * a
    }

    def getDistance(a: Coordinate, b: Coordinate): Double = {
        return math.sqrt(square(b.x - a.x) + square(b.y - a.y))
    }

    def printRoof(roof: ArrayBuffer[Array[Char]]): Unit = {
        println("Current state of roof:")
        roof.foreach(line =>
            println(line.mkString)
        )
    }

    def main(args: Array[String]): Unit = {
        val filename = "roof.txt"
        val lines = Source.fromFile(filename).getLines().toList

        // Read in input
        var roof = ArrayBuffer[Array[Char]]()
        lines.foreach(line =>
            roof.append(line.toCharArray)
        )

        printRoof(roof)

        // Get locations of antennas
        // Maps frequencies to antenna locations
        val antennas = new HashMap[Char, List[Coordinate]]()
        for (x <- roof.indices) {
            val row: Array[Char] = roof(x)
            for (y <- row.indices) {
                val antenna: Char = row(y)
                if (antenna != '.') {
                    if (!antennas.contains(antenna)) {
                        antennas(antenna) = List.empty[Coordinate]
                    }
                    antennas(antenna) = antennas(antenna).prepended(Coordinate(x, y))
                }
            }
        }

        //println(getDistance(Coordinate(-2, 4), Coordinate(10, 1))) // should be ~12.369
    }
}