import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Main {
    def printRoof(roof: ArrayBuffer[Array[Char]]): Unit = {
        println("Current state of roof:")
        roof.foreach(line =>
            println(line.mkString)
        )
    }

    def main(args: Array[String]): Unit = {
        val filename = "roof.txt"
        val lines = Source.fromFile(filename).getLines().toList

        // Read in input
        var roof = ArrayBuffer[Array[Char]]()
        lines.foreach(line =>
            roof.append(line.toCharArray)
        )

        printRoof(roof)
    }
}