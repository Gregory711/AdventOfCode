import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val filename = "lists.txt"
    val lines = Source.fromFile(filename).getLines().toList
    lines.foreach(line =>
        // Split breaks String into String Array
        // " +" is regex for one or more of what came before it which in this case is a space
        // So splits line into an array of strings broken up by spaces
        val ids = line.split(" +");
        println(ids(0) + ", " + ids(1));
    )
  }
}