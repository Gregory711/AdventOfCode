import scala.io.Source

object Main {
    // Returns (value of next mul after index or 0 if none or invalid, index incremented past end of mul or end of string if none)
    def getMulValue(instructions: String, startIndex: Int): (Int, Int) = {
        //println("Entering getMulValue with startIndex: " + startIndex)
        val mulIndex = instructions.indexOf("mul(", startIndex)
        //println("  mulIndex is " + mulIndex)
        // If no mul instruction found return end of string
        if (mulIndex == -1) {
            return (0, instructions.size)
        }
        // Find comma ending first mul arg, return end of string if none found
        val commaIndex = instructions.indexOf(",", mulIndex)
        //println("  commaIndex is " + commaIndex)
        if (commaIndex == -1) {
            return (0, instructions.size)
        }
        // Find closing parentheses ending mul call, return end of string if none found
        val closeIndex = instructions.indexOf(")", commaIndex)
        //println("  closeIndex is " + closeIndex)
        if (closeIndex == -1) {
            return (0, instructions.size)
        }
        // Try to parse first mul arg, if failed return end of mul(
        val argAOption = instructions.slice(mulIndex + "mul(".size, commaIndex).toIntOption
        val argA = argAOption match {
            case Some(arg) => arg
            case None =>
                return (0, mulIndex + 1)
        }
        //println("  Arg A is " + argA)
        // Try to parse second mul arg, if failed return end of ,
        val argBOption = instructions.slice(commaIndex + 1, closeIndex).toIntOption
        val argB = argBOption match {
            case Some(arg) => arg
            case None =>
                return (0, commaIndex + 1)
        }
        //println("  Arg B is " + argB)
        // Return result of calculating mul instruction!
        //println("Added " + argA + " * " + argB + " = " + argA * argB)
        return (argA * argB, closeIndex + 1)
    }

    def getMulTotal(instructions: String): Int = {
        var mulTotal = 0
        var startIndex = 0
        while (startIndex < instructions.size) {
            val (mul, index) = getMulValue(instructions, startIndex)
            mulTotal = mulTotal + mul
            startIndex = index
        }
        return mulTotal
    }

    def main(args: Array[String]): Unit = {
        val filename = "instructions2.txt"
        val lines = Source.fromFile(filename).getLines().toList

        var mulTotalPart1 = 0
        var mulTotalPart2 = 0
        var enabled = true
        lines.foreach(line =>
            // Part 1
            mulTotalPart1 = mulTotalPart1 + getMulTotal(line)
            // Part 2
            var startIndex: Int = 0
            var endIndex: Int = -1
            while (startIndex < line.size && endIndex < line.size) {
                if (enabled) {
                    endIndex = line.indexOf("don't()", (endIndex).max(startIndex) + 1)
                    //println("endIndex is " + endIndex)
                    if (endIndex == -1) {
                        endIndex = line.size
                    } else {
                        enabled = false
                    }
                    mulTotalPart2 = mulTotalPart2 + getMulTotal(line.slice(startIndex, endIndex))
                    //println("Adding " + startIndex + "-" + endIndex + " bringing total to " + mulTotalPart2)
                }
                if (!enabled) {
                    startIndex = line.indexOf("do()", endIndex + 1)
                    if (startIndex == -1) {
                        startIndex = line.size
                    } else {
                        enabled = true
                    }
                }
            }
        )
        println("The total value of mul instructions for part 1 is " + mulTotalPart1)
        println("The total value of mul instructions for part 2 is " + mulTotalPart2)
    }
}