import scala.io.Source

object Main {
    // Returns (value of mul at index or 0 if none, index incremented past end of mul or index+1 if none)
    def getMulValue(instructions: String, startIndex: Int): (Int, Int) = {
        // todo: implement
        return (0, startIndex+1)
    }

    def main(args: Array[String]): Unit = {
        val filename = "instructions.txt"
        val lines = Source.fromFile(filename).getLines().toList

        var mulTotal = 0
        lines.foreach(line =>
            var startIndex = 0
            while (startIndex < line.size) {
                val (mul, index) = getMulValue(line, startIndex)
                mulTotal = mulTotal + mul
                startIndex = index
            }
        )
        println("The total value of mul instructions is " + mulTotal)
    }
}