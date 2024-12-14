import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "bridge.txt"
        val lines = Source.fromFile(filename).getLines().toList

        lines.foreach(line =>
            val sum: Int = line.slice(0, line.indexOf(':')).toInt
            println("Test sum: " + sum)
        )
    }
}