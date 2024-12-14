import scala.io.Source

object Main {
    def solve(numbs: Array[Int], operators: Array[Char], result: Int, index: Int): Boolean = {
        return false
    }

    def main(args: Array[String]): Unit = {
        val filename = "bridge.txt"
        val lines = Source.fromFile(filename).getLines().toList

        lines.foreach(line =>
            val colonIndex: Int = line.indexOf(':')
            val result: Int = line.slice(0, colonIndex).toInt
            val numbs: Array[Int] = line.substring(colonIndex+2).split(" +").map(_.toInt)
            println("Test result: " + result)
            println("Numbers: " + numbs.mkString(", "))
            println()
        )
    }
}