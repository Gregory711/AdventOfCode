import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "wordSearch.txt"
        val lines = Source.fromFile(filename).getLines().toList

        case class Coordinate(x: Int, y: Int)
        val deltas: List[Coordinate] = List(
            Coordinate(-1, -1), // diagonal up left
            Coordinate(0, -1), // up
            Coordinate(1, -1), // diagonal up right
            Coordinate(1, 0), // right
            Coordinate(1, 1), // diagonal down right
            Coordinate(0, 1), // down
            Coordinate(-1, 1), // diagonal down left
            Coordinate(-1, 0) // left
        )

        for
            i <- 0 to lines.size - 1
        do
            for (j <- 0 to lines(i).size - 1 if lines(i)(j) == 'X') {
                //
            }
    }
}