import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "wordSearch.txt"
        val lines = Source.fromFile(filename).getLines().toList

        for
            i <- 0 to lines.size - 1
        do
            for (j <- 0 to lines(i).size - 1 if lines(i)(j) == 'X') {
                //
            }
    }
}