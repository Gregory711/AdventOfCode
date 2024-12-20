import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap

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

    def printLab(lab: ArrayBuffer[Array[Char]], guardCoords: Coordinate, visited: Int): Unit = {
        println("Current state of lab:")
        lab.foreach(line =>
            println(line.mkString)
        )
        println("Guard is at (" + guardCoords.x + ", " + guardCoords.y + ") and has visited " + visited + " locations so far!\n")
    }

    def getVisitCount(lab: ArrayBuffer[Array[Char]], guardStart: Coordinate, guardStartDirection: Coordinate): Int = {
        var guard: Coordinate = guardStart
        var visited: Int = 0
        var outOfBounds: Boolean = false
        var guardDirection: Coordinate = guardStartDirection
        // For loop detection, maps coordinates guard has patrolled to the
        // directions he was facing when he patrolled there
        // If patrolling same coordinate facing same direction twice in a loop
        var patrolled = new HashMap[Coordinate, List[Coordinate]]()
        while (!outOfBounds) {
            //printLab(lab, guard, visited)
            // If on a new tile then mark it visited and increment visited
            if (lab(guard.y)(guard.x) != 'X') {
                lab(guard.y)(guard.x) = 'X'
                visited = visited + 1
            }

            if (!patrolled.contains(guard)) {
                patrolled(guard) = List.empty[Coordinate]
            }
            if (!patrolled(guard).contains(guardDirection)) {
                patrolled(guard) = patrolled(guard).appended(guardDirection)
            }

            val ahead = Coordinate(guard.x + guardDirection.x, guard.y + guardDirection.y)
            val newDirection = getNextDirection(guardDirection)

            /*println("Dead ahead of guard is (" + ahead.x + ", " + ahead.y + ")")
            if (inBounds(ahead, lab)) {
                println("Which is " + lab(ahead.y)(ahead.x))
            }
            println("New direction if turning is (" + newDirection.x + ", " + newDirection.y + ")")*/

            if (!inBounds(ahead, lab)) {
                outOfBounds = true
                //println("Out of bounds, see ya!")
            } else if (lab(ahead.y)(ahead.x) == '#') {
                // Ahead is obstacle so need to change direction
                guardDirection = getNextDirection(guardDirection)
                //println("Ruh roh, the path is blocked!")
            } else {
                guard = ahead
                //println("Onwards!")
                if (patrolled.contains(guard) && patrolled(guard).contains(guardDirection)) {
                    // In a loop!
                    return -1
                }
            }
            //println()
        }
        return visited
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

        // Get initial direction of guard
        val guardDirection: Coordinate = getDirection(lab(guard.y)(guard.x)).get

        // Part 1
        val visited: Int = getVisitCount(lab, guard, guardDirection)

        // Part 2
        var loops: Int = 0
        for
            i <- 0 until lab.size
        do
            for
                j <- 0 until lab(i).size
            do
                val before: Char = lab(i)(j)
                lab(i)(j) = '#'
                if (!(i == guard.y && j == guard.x) && getVisitCount(lab, guard, guardDirection) == -1) {
                    loops = loops + 1
                }
                lab(i)(j) = before

        println("The guard visited " + visited + " locations")
        println("There are " + loops + " places to put obstacles such that it will cause guard to end up in a loop!")
    }
}