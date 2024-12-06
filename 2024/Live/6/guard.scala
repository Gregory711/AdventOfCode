import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Main {

    case class Coordinate(x: Int, y: Int)
    
    def getDirection(guard: Char): Option[Coordinate] = {
        guard match {
            case '>' => Some(Coordinate(1, 0))
            case 'v' => Some(Coordinate(0, 1))
            case '<' => Some(Coordinate(-1, 0))
            case '^' => Some(Coordinate(-1, 0))
            case _ => None
        }
    }

    def main(args: Array[String]): Unit = {
        val filename = "lab.txt"
        val lines = Source.fromFile(filename).getLines().toList

        // Read in input
        var lab = ArrayBuffer[String]()
        lines.foreach(line =>
            lab.append(line)
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
        println("Guard is initially at " + guard.x + ", " + guard.y)
    }
}