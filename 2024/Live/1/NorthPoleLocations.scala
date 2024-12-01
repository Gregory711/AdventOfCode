import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap

object Main {
  def main(args: Array[String]): Unit = {
    val filename = "lists.txt"
    val lines = Source.fromFile(filename).getLines().toList
    val listA = ArrayBuffer[String]()
    val listB = ArrayBuffer[String]()
    val occurrences = HashMap[Int, Int]()
    lines.foreach(line =>
        // Split breaks String into String Array
        // " +" is regex for one or more of what came before it which in this case is a space
        // So splits line into an array of strings broken up by spaces
        val ids = line.split(" +");
        listA.append(ids(0))
        listB.append(ids(1))
        //println(ids(0) + ", " + ids(1));
    )
    val listASorted = listA.sorted
    val listBSorted = listB.sorted
    var distance: Int = 0
    for
        i <- 0 to listASorted.size - 1
    do
        //println(listASorted(i) + ", " + listBSorted(i))
        distance += (listASorted(i).toInt - listBSorted(i).toInt).abs
        //println("Distance: " + distance)
    println("The total distance is " + distance)
  }
}