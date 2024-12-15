import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet

object Main {
    val TOLERANCE: Double = 0

    // Just to clarify each row of roof has different x starting with 0 (top/first line)
    // Each column has y starting with 0 leftmost
    case class Coordinate(x: Int, y: Int) {
        // override methods so can use hashset
        override def hashCode(): Int = (x, y).hashCode()

        override def equals(obj: Any): Boolean = obj match {
            case other: Coordinate => this.x == other.x && this.y == other.y
            case _ => false
        }
    }

    def square(a: Double): Double = {
        return a * a
    }

    def getDistance(a: Coordinate, b: Coordinate): Double = {
        return math.sqrt(square(b.x - a.x) + square(b.y - a.y))
    }

    def inLineAndDoubleDist(origin: Coordinate, a: Coordinate, b: Coordinate): Boolean = {
        val aHorizontalDist: Int = origin.x - a.x
        val bHorizontalDist: Int = origin.x - b.x
        val aVerticalDist: Int = origin.y - a.y
        val bVerticalDist: Int = origin.y - b.y
        // Verify that b is double as far left/right and double as far up/down as a from origin
        return bHorizontalDist == (aHorizontalDist * 2) && bVerticalDist == (aVerticalDist * 2)
    }

    def inLine(origin: Coordinate, a: Coordinate, b: Coordinate): Boolean = {
        // To be line need same slope relative to the origin point so they are on the same line or
        // have one of the point be the same as origin so the line is just connecting the two dots
        // Recall: slope = rise over run = vertical diff / horizontal diff
        val aHorizontalDist: Int = origin.x - a.x
        val bHorizontalDist: Int = origin.x - b.x
        val aVerticalDist: Int = origin.y - a.y
        val bVerticalDist: Int = origin.y - b.y
        val aSlope: Double = aVerticalDist.toDouble / aHorizontalDist
        val bSlope: Double = bVerticalDist.toDouble / bHorizontalDist
        //println("(" + a.x + ", " + a.y + ") and (" + b.x + ", " + b.y + ") have slopes " + aSlope + " and " + bSlope + " respectively.")
        return aSlope == bSlope || (origin.x == a.x && origin.y == a.y) || (origin.x == b.x && origin.y == b.y)
    }

    def printRoof(roof: ArrayBuffer[Array[Char]]): Unit = {
        println("Current state of roof:")
        roof.foreach(line =>
            println(line.mkString)
        )
    }

    def main(args: Array[String]): Unit = {
        val filename = "roof.txt"
        val lines = Source.fromFile(filename).getLines().toList

        // Read in input
        var roof = ArrayBuffer[Array[Char]]()
        lines.foreach(line =>
            roof.append(line.toCharArray)
        )

        printRoof(roof)

        // Get locations of antennas
        // Maps frequencies to antenna locations
        val antennas = new HashMap[Char, List[Coordinate]]()
        for (x <- roof.indices) {
            val row: Array[Char] = roof(x)
            for (y <- row.indices) {
                val antenna: Char = row(y)
                if (antenna != '.') {
                    if (!antennas.contains(antenna)) {
                        antennas(antenna) = List.empty[Coordinate]
                    }
                    antennas(antenna) = antennas(antenna).prepended(Coordinate(x, y))
                }
            }
        }

        //println(getDistance(Coordinate(-2, 4), Coordinate(10, 1))) // should be ~12.369

        // Iterate over every location on roof and check if any two antennas of same frequency have one
        // that is twice as far from current location as the other
        var antinodes = HashSet[Coordinate]()
        var harmonicAntinodes = HashSet[Coordinate]()
        for (x <- roof.indices) {
            val row: Array[Char] = roof(x)
            for (y <- row.indices) {
                val loc: Coordinate = Coordinate(x, y)
                // Iterate over antennas
                antennas.foreach { case (antenna, locs) =>
                    // Compare every distance against every other distance to see if any are double others
                    locs.foreach(locA =>
                        locs.foreach(locB =>
                            if (!(locA.x == locB.x && locA.y == locB.y)) {
                                if (inLineAndDoubleDist(loc, locA, locB)) {
                                    antinodes += loc
                                }
                                if (inLine(loc, locA, locB)) {
                                    harmonicAntinodes += loc
                                }
                            }
                        )
                    )

                    // Solution if question was just asking if antennas had one that was double distance from
                    // the starting location than the other not accounting for being in line
                    /*// Get distances from current loc to each antenna of this frequency
                    val dists: List[Double] = locs.map(ant => getDistance(loc, ant))
                    dists.foreach(distA =>
                        dists.foreach(distB =>
                            val diff: Double = math.abs(distA - (distB * 2))
                            if (diff <= TOLERANCE && distA != 0) {
                                antinodes += loc
                                println("Found an antinode at (" + loc.x + ", " + loc.y + ") (diff was " + diff + ")")
                                println("There is a antenna of freq: " + antenna + " distances " + distA + " and " + distB + " away!")
                            }
                        )
                    )*/
                }
            }
        }
        println("There are " + antinodes.size + " locations with antinodes perfectly in line!")
        println("There are " + harmonicAntinodes.size + " locations with antinodes perfectly in line when accounting for harmonic resonations!")
    }
}