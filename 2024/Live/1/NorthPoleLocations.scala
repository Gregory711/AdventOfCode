import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap

object Main {
  def main(args: Array[String]): Unit = {
    val filename = "lists.txt"
    val lines = Source.fromFile(filename).getLines().toList
    val listA = ArrayBuffer[Int]()
    val listB = ArrayBuffer[Int]()
    val occurrences = HashMap[Int, Int]()
    lines.foreach(line =>
        // Split breaks String into String Array
        // " +" is regex for one or more of what came before it which in this case is a space
        // So splits line into an array of strings broken up by spaces
        val ids = line.split(" +")
        listA.append(ids(0).toInt)
        listB.append(ids(1).toInt)
        // If id already in hashmap increment it otherwise insert it with value 1
        occurrences.updateWith(ids(1).toInt) {
            case Some(id) => Some(id + 1)
            case None => Some(1)
        }
        //println(ids(0) + ", " + ids(1));
        //println(occurrences)
    )
    val listASorted = listA.sorted
    val listBSorted = listB.sorted
    var distance: Int = 0
    var similarity: Int = 0
    for
        i <- 0 to listASorted.size - 1
    do
        //println(listASorted(i) + ", " + listBSorted(i))
        distance += (listASorted(i) - listBSorted(i)).abs
        //println("Distance: " + distance)
        similarity += listASorted(i) * occurrences.getOrElse(listASorted(i), 0)
        //println("Similarity: " + similarity)
    println("The total distance is " + distance)
    println("The total similarity score is " + similarity)
  }
}