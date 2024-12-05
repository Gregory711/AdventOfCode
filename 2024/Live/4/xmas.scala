import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "input.txt"
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

        val diagonalPairs: List[List[Coordinate]] = List(
            List(
                Coordinate(-1, -1), // diagonal up left
                Coordinate(1, 1) // diagonal down right
            ),
            List(
                Coordinate(-1, 1), // diagonal down left
                Coordinate(1, -1) // diagonal up right
            )
        )

        var xmasCount: Int = 0
        var masCount: Int = 0

        for
            i <- 0 to lines.size - 1
        do
            // Part 1
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
            // Part 2
            for (j <- 0 to lines(i).size - 1 if lines(i)(j) == 'A') {
                var masDiagonals: Boolean = true
                diagonalPairs.foreach(diagonal => 
                    val left: Coordinate = Coordinate(i + diagonal(0).x, j + diagonal(0).y)
                    val right: Coordinate = Coordinate(i + diagonal(1).x, j + diagonal(1).y)
                    // Check if left and right are in bounds
                    if (
                        left.x >= 0 && left.y >= 0 && left.x < lines.size && left.y < lines(left.x).size &&
                        right.x >= 0 && right.y >= 0 && right.x < lines.size && right.y < lines(left.y).size
                    ) {
                        // Left and right must use S and M between them or not an X-MAS formation
                        if (
                            !(lines(left.x)(left.y) == 'M' && lines(right.x)(right.y) == 'S') &&
                            !(lines(left.x)(left.y) == 'S' && lines(right.x)(right.y) == 'M') 
                        ){
                            masDiagonals = false
                        }
                    } else {
                        masDiagonals = false
                    }
                )
                if (masDiagonals) {
                    masCount = masCount + 1
                }
            }
        println("XMAS appears in the word search " + xmasCount + " times!")
        println("X-MAS formation appears in the word search " + masCount + " times!")
    }
}