import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val filename = "lists.txt"
    val lines = Source.fromFile(filename).getLines().toList
    lines.foreach(line =>
        println(line);
    )
  }
}