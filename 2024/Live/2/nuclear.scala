import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "reports.txt"
        val lines = Source.fromFile(filename).getLines().toList

        lines.foreach(line =>
            val levels = line.split(" +")
            var first: Boolean = true
            var increasing: Boolean = false
            var decreasing: Boolean = false
            var last: Int = -1
            levels.foreach(level =>
                println("Processing level: " + level)
            )
        )
    }
}