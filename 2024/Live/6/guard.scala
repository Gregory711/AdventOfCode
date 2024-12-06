import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "lab.txt"
        val lines = Source.fromFile(filename).getLines().toList

        var lab = ArrayBuffer[String]()
        lines.foreach(line =>
            lab.append(line)
        )
    }
}