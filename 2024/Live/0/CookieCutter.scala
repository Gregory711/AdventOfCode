import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Main {
  def main(args: Array[String]): Unit = {
    println("Advent of Code!")

    val filename = "cookies.txt"
    val lines = Source.fromFile(filename).getLines().toList
    // ArrayBuffer is ArrayList like data structure
    val cookies = ArrayBuffer[String]()
    var cookieCount: Int = 0
    lines.foreach(line =>
        // Split breaks String into String Array
        // " +" is regex for one or more of what came before it which in this case is a space
        // So splits line into an array of strings broken up by spaces
        val items = line.split(" +")
        cookies.append(items(0))
        //println("Parsing " + items.last)
        cookieCount += items.last.toInt
    )
    val sortedCookies = cookies.sorted
    println("The total cookie count is: " + cookieCount)
    println("The cookies in alphabetical order are: ")
    for
        i <- 0 to sortedCookies.size - 1
    do
        println(sortedCookies(i))
  }
}