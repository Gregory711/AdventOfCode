import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "disk.txt"
        val lines = Source.fromFile(filename).getLines().toList

        val disk: Array[Char] = lines.head.toCharArray()
        println("Initial Disk: " + disk.mkString)
    }
}