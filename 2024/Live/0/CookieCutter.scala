import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    println("Advent of Code!")

    val filename = "cookies.txt"
    val lines = Source.fromFile(filename).getLines().toList
    lines.foreach(println)
  }
}