import scala.io.Source

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "pages.txt"
        val lines = Source.fromFile(filename).getLines().toList

        lines.foreach(line =>
            val pipe: Int = line.indexOf('|')
            if (pipe != -1) {
                val a: Int = line.slice(0, pipe).toInt
                val b: Int = line.substring(pipe + 1).toInt
                //println(a + " must come before "+ b)
            }
        )
    }
}