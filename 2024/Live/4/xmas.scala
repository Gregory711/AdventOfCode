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

        var xmasCount: Int = 0

        for
            i <- 0 to lines.size - 1
        do
            for (j <- 0 to lines(i).size - 1 if lines(i)(j) == 'X') {
                deltas.foreach(delta =>
                    val M: Coordinate = Coordinate(i + delta.x, j + delta.y)
                    val A: Coordinate = Coordinate(i + (delta.x * 2), j + (delta.y * 2))
                    val S: Coordinate = Coordinate(i + (delta.x * 3), j + (delta.y * 3))
                    //println("Checking X at (" + i + ", " + j + ")")
                    if (
                        M.x >= 0 && M.y >= 0 && M.x < lines.size && M.y < lines(M.x).size && lines(M.x)(M.y) == 'M' &&
                        A.x >= 0 && A.y >= 0 && A.x < lines.size && A.y < lines(A.x).size && lines(A.x)(A.y) == 'A' &&
                        S.x >= 0 && S.y >= 0 && S.x < lines.size && S.y < lines(S.x).size && lines(S.x)(S.y) == 'S'
                    ) {
                        xmasCount = xmasCount + 1
                    }
                )
            }
        println("XMAS appears in the word search " + xmasCount + " times!")
    }
}