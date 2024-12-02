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
            var gradual: Boolean = true
            var last: Int = -1
            levels.foreach(level =>
                //println("Processing level: " + level)
                if (!first) {
                    if (level.toInt > last) {
                        increasing = true
                    } else if (level.toInt < last) {
                        decreasing = true
                    }
                    val diff: Int = (level.toInt - last).abs
                    if (diff < 1 || diff > 3) {
                        gradual = false
                    }
                }
                last = level.toInt
            )
        )
    }
}