import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashSet
import scala.collection.mutable.Stack

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

    def inBounds(coords: Coordinate, map: ArrayBuffer[Array[Int]]): Boolean = {
        if (
            coords.y < 0 || coords.x < 0 ||
            coords.y >= map.size || coords.x >= map(coords.y).size
        ) {
            return false
        }
        return true
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

        val deltas: List[Coordinate] = List(
            Coordinate(0, -1), // up
            Coordinate(1, 0), // right
            Coordinate(0, 1), // down
            Coordinate(-1, 0) // left
        )

        var totalScore: Int = 0
        trailheads.foreach(trailhead =>
            // Depth first search for max elevation via hiking
            var score: Int = 0
            val visited = HashSet[Coordinate]()
            val toVisit = Stack[Coordinate]()
            toVisit.push(trailhead)
            while (!toVisit.isEmpty) {
                val visiting: Coordinate = toVisit.pop
                val elevation: Int = map(visiting.x)(visiting.y)
                if (elevation == 9) {
                    score = score + 1
                } else {
                    deltas.foreach(delta =>
                        val visit = Coordinate(visiting.x + delta.x, visiting.y + delta.y)
                        if (!visited.contains(visit) && inBounds(visit, map) && map(visit.x)(visit.y) == (elevation + 1)) {
                            toVisit.push(visit)
                            visited.add(visit)
                        }
                    )
                }
            }
            println("Score for trailhead at " + trailhead.toString + " is " + score)
            totalScore = totalScore + score
        )

        printMap(map)
        println("Trailheads: " + trailheads.mkString(", "))
        println("Total Score: " + totalScore)
    }
}