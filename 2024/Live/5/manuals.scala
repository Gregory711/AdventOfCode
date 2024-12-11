import scala.io.Source
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "pages.txt"
        val lines = Source.fromFile(filename).getLines().toList

        var before = new HashMap[Int, List[Int]]()
        var middleSum: Int = 0
        var sortedSum: Int = 0
        lines.foreach(line =>
            val pipe: Int = line.indexOf('|')
            val comma: Int = line.indexOf(',')
            if (pipe != -1) {
                val a: Int = line.slice(0, pipe).toInt
                val b: Int = line.substring(pipe + 1).toInt
                //println(a + " must come before "+ b)
                if (!before.contains(b)) {
                    before(b) = List.empty[Int]
                }
                before(b) = before(b).prepended(a)
                //println("Added rule that before " + b + " must be " + a + " if " + a + " is included")
            } else if (comma != -1) {
                val pages = line.split(",+")
                var addMiddle: Boolean = true
                //println(pages.toList)
                var rulesBarred = HashSet[Int]()
                pages.foreach(page =>
                    if (rulesBarred.contains(page.toInt)) {
                        addMiddle = false
                    } else if (addMiddle) {
                        //println("Checking if there are rules on what must come before " + page)
                        if (before.contains(page.toInt)) {
                            before(page.toInt).foreach(rule =>
                                //println(page.toInt + " is barring " + rule)
                                rulesBarred.add(rule)
                            )
                        }
                    }
                )
                if (addMiddle) {
                    //println("Should add middle number for this report: " + line)
                    if ((pages.size % 2) == 0) {
                        println("ERROR: CANNOT FIGURE OUT MIDDLE NUMBER FOR EVEN LENGTH LIST!")
                    } else {
                        middleSum = middleSum + pages(pages.length / 2).toInt
                    }
                } else {
                    // If pages are in the wrong order need to sort them and sum middle number of sorted list
                    // Uses custom comparator that returns true if a should come before b
                    // Or rather, will return true if there is no rule saying b must come before a
                    val sortedPages = pages.sortWith((a, b) => {
                        !before.contains(a.toInt) || !before(a.toInt).contains(b.toInt)
                    })
                    if ((sortedPages.size % 2) == 0) {
                        println("ERROR: CANNOT FIGURE OUT MIDDLE NUMBER FOR EVEN LENGTH LIST!")
                    } else {
                        sortedSum = sortedSum + sortedPages(sortedPages.length / 2).toInt
                    }
                }
            }
        )
        println("The sum of the middles is " + middleSum)
        println("The sum of the sorted middles is " + sortedSum)
    }
}