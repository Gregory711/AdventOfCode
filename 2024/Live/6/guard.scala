import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Main {

    case class Coordinate(x: Int, y: Int)
    
    def getDirection(guard: Char): Option[Coordinate] = {
        guard match {
            case '>' => Some(Coordinate(1, 0))
            case 'v' => Some(Coordinate(0, 1))
            case '<' => Some(Coordinate(-1, 0))
            case '^' => Some(Coordinate(0, -1))
            case _ => None
        }
    }

    def getNextDirection(guardDirection: Coordinate): Coordinate = {
        guardDirection match {
            case Coordinate(1, 0) => Coordinate(0, 1)
            case Coordinate(0, 1) => Coordinate(-1, 0)
            case Coordinate(-1, 0) => Coordinate(0, -1)
            case Coordinate(0, -1) => Coordinate(1, 0)
        }
    }

    def inBounds(coords: Coordinate, lab: ArrayBuffer[Array[Char]]): Boolean = {
        if (
            coords.y < 0 || coords.x < 0 ||
            coords.y >= lab.size || coords.x >= lab(coords.y).size
        ) {
            return false
        }
        return true
    }

    def main(args: Array[String]): Unit = {
        val filename = "lab.txt"
        val lines = Source.fromFile(filename).getLines().toList

        // Read in input
        var lab = ArrayBuffer[Array[Char]]()
        lines.foreach(line =>
            lab.append(line.toCharArray)
        )

        // Get initial location of guard
        var guard = Coordinate(0, 0)
        while (getDirection(lab(guard.y)(guard.x)) == None) {
            if (guard.x < lab(guard.y).size - 1) {
                guard = Coordinate(guard.x + 1, guard.y)
            } else {
                guard = Coordinate(0, guard.y + 1)
            }
        }
        //println("Guard is initially at " + guard.x + ", " + guard.y)

        var visited: Int = 0
        var outOfBounds: Boolean = false
        var guardDirection: Coordinate = getDirection(lab(guard.y)(guard.x)).get
        while (!outOfBounds) {
            // If on a new tile then mark it visited and increment visited
            if (lab(guard.y)(guard.x) != 'X') {
                lab(guard.y)(guard.x) = 'X'
                visited = visited + 1
            }
            val ahead = Coordinate(guard.x + guardDirection.x, guard.y + guardDirection.y)
            val newDirection = getDirection(lab(ahead.y)(ahead.x))
            if (!inBounds(ahead, lab)) {
                outOfBounds = true
            } else if (newDirection == None) {
                // Ahead is obstacle so need to change direction
                guardDirection = getNextDirection(guardDirection)
            } else {
                guard = ahead
            }
        }
        println("The guard visited " + visited + " locations")
    }
}