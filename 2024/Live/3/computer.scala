import scala.io.Source

object Main {
    // Returns (value of next mul after index or 0 if none or invalid, index incremented past end of mul or end of string if none)
    def getMulValue(instructions: String, startIndex: Int): (Int, Int) = {
        val mulIndex = instructions.indexOf("mul(", startIndex)
        // If no mul instruction found return end of string
        if (mulIndex == -1) {
            return (0, instructions.size)
        }
        // Find comma ending first mul arg, return end of string if none found
        val commaIndex = instructions.indexOf(",", mulIndex)
        if (commaIndex == -1) {
            return (0, instructions.size)
        }
        // Find closing parentheses ending mul call, return end of string if none found
        val closeIndex = instructions.indexOf(")", commaIndex)
        if (closeIndex == -1) {
            return (0, instructions.size)
        }
        // Try to parse first mul arg, if failed return end of mul(
        val argAOption = instructions.slice(mulIndex, commaIndex).toIntOption
        val argA = argAOption match {
            case Some(arg) => arg
            case None =>
                return (0, mulIndex + 1)
        }
        // Try to parse second mul arg, if failed return end of ,
        val argBOption = instructions.slice(commaIndex + 1, closeIndex).toIntOption
        val argB = argBOption match {
            case Some(arg) => arg
            case None =>
                return (0, commaIndex + 1)
        }
        // Return result of calculating mul instruction!
        return (argA * argB, closeIndex + 1)
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