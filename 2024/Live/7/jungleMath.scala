import scala.io.Source

object Main {
    def printEquation(numbs: Array[Long], operators: Array[Char]): Unit = {
        val eq: Array[String] = new Array[String](numbs.size + operators.size)
        for
            i <- 0 until numbs.size
        do
            val index: Int = i * 2
            eq(index) = numbs(i).toString
            if ((index + 1) < eq.size) {
                eq(index + 1) = operators(i).toString
            }
        println(eq.mkString(" "))
    }

    def compute(numbs: Array[Long], operators: Array[Char]): Long = {
        var result: Long = numbs.head
        for
            i <- 1 until numbs.size
        do
            val numb: Long = numbs(i)
            val operator: Char = operators(i - 1)
            result = operator match {
                case '+' => result + numb
                case '*' => result * numb
                case 'C' => (result.toString + numb.toString).toLong
            }
        return result
    }

    def solve(numbs: Array[Long], operators: Array[Char], result: Long, index: Int, validOperators: List[Char]): Boolean = {
        if (index == operators.size) {
            return result == compute(numbs, operators)
        }

        validOperators.foreach(operator =>
            operators(index) = operator
            if (solve(numbs, operators, result, index + 1, validOperators)) {
                return true
            }
        )
        return false
    }

    def main(args: Array[String]): Unit = {
        val filename = "bridge.txt"
        val lines = Source.fromFile(filename).getLines().toList

        val partOneOperators:List[Char] = List('+', '*')
        val partTwoOperators:List[Char] = List('+', '*', 'C') // C for concatenate
        var partOneResultSum: Long = 0
        var partTwoResultSum: Long = 0
        lines.foreach(line =>
            val colonIndex: Int = line.indexOf(':')
            val result: Long = line.slice(0, colonIndex).toLong
            val numbs: Array[Long] = line.substring(colonIndex+2).split(" +").map(_.toLong)
            val operators: Array[Char] = new Array[Char](numbs.size - 1)

            //println("Test result: " + result)
            //println("Numbers: " + numbs.mkString(", "))

            if (solve(numbs, operators, result, 0, partOneOperators)) {
                partOneResultSum = partOneResultSum + result
            }
            if (solve(numbs, operators, result, 0, partTwoOperators)) {
                partTwoResultSum = partTwoResultSum + result
            }
            //println()
        )
        println("Part 1: The sum of results of valid bridge equations is " + partOneResultSum)
        println("Part 2: The sum of results of valid bridge equations is " + partTwoResultSum)
    }
}