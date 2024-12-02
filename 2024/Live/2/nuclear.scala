import scala.io.Source

object Main {
    def reportIsSafe(levels: Array[String], dampen: Int): Boolean = {
        var first: Boolean = true
        var increasing: Boolean = false
        var decreasing: Boolean = false
        var gradual: Boolean = true
        var last: Int = -1
        for (i <- 0 to levels.size - 1 if i != dampen) {
            val level: Int = levels(i).toInt
            //println("Processing level: " + level)
            if (!first) {
                if (level > last) {
                    increasing = true
                } else if (level < last) {
                    decreasing = true
                }
                val diff: Int = (level - last).abs
                if (diff < 1 || diff > 3) {
                    gradual = false
                }
            }
            last = level
            first = false
        }
        if (gradual && (increasing != decreasing)) {
            return true
        }
        return false
    }

    def main(args: Array[String]): Unit = {
        val filename = "reports.txt"
        val lines = Source.fromFile(filename).getLines().toList
        var safeReportCount: Int = 0

        lines.foreach(line =>
            val levels = line.split(" +")
            if (reportIsSafe(levels, -1)) {
                safeReportCount = safeReportCount + 1
            }
        )
        println("There are a total of " + safeReportCount + " safe reports.")
    }
}