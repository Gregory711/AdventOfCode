import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "disk.txt"
        val lines = Source.fromFile(filename).getLines().toList

        // ASCII addition e.g. '0' - '0' is 0 int, '1' - '0' is 1 int, and so on
        val disk: Array[Int] = lines.head.toCharArray().map(_ - '0')

        println("Initial Disk: " + disk.mkString)
    }
}