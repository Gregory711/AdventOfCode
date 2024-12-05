import scala.io.Source
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet

object Main {
    def main(args: Array[String]): Unit = {
        val filename = "pages.txt"
        val lines = Source.fromFile(filename).getLines().toList

        var before = new HashMap[Int, List[Int]]()
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
                before(b).prepended(a)
            } else if (comma != -1) {
                val pages = line.split(",+")
                var addMiddle: Boolean = true
                //println(pages.toList)
                var rulesBarred = HashSet[Int]()
                pages.foreach(page =>
                    if (rulesBarred.contains(page.toInt)) {
                        addMiddle = false
                    } else if (addMiddle) {
                        if (before.contains(page.toInt)) {
                            before(page.toInt).foreach(rule =>
                                rulesBarred.add(rule)
                            )
                        }
                    }
                )
                if (addMiddle) {
                    println("Should add middle number for this report: " + line)
                }
            }
        )
    }
}