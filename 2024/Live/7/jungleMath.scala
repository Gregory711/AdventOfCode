import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "bridge.txt"
        val lines = Source.fromFile(filename).getLines().toList

        lines.foreach(line =>
            val colonIndex: Int = line.indexOf(':')
            val sum: Int = line.slice(0, colonIndex).toInt
            val numbs: Array[Int] = line.substring(colonIndex+2).split(" +").map(_.toInt)
            println("Test sum: " + sum)
            println("Numbers: " + numbs.mkString(", "))
            println()
        )
    }
}