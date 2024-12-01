import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Main {
  def main(args: Array[String]): Unit = {
    val filename = "lists.txt"
    val lines = Source.fromFile(filename).getLines().toList
    val listA = ArrayBuffer[String]()
    val listB = ArrayBuffer[String]()
    lines.foreach(line =>
        // Split breaks String into String Array
        // " +" is regex for one or more of what came before it which in this case is a space
        // So splits line into an array of strings broken up by spaces
        val ids = line.split(" +");
        listA.append(ids(0))
        listB.append(ids(1))
        //println(ids(0) + ", " + ids(1));
    )
  }
}