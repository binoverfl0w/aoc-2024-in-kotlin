typealias Point = Pair<Int, Int>

fun main() {

    val grid = readInput("input12")
        .map { it.toCharArray() }
        .toTypedArray()

    data class Region(val ch: Char, val area: Int, val perimeter: Int)

    fun Array<CharArray>.findRegion(ch: Char, i: Int, j: Int, seen: MutableSet<Point>): Region {
        var area = 0
        var perimeter = 0

        val queue = ArrayDeque<Point>()
        queue += Point(i, j)
        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()

            if (point.first !in this.indices
                || point.second !in this.first().indices
                || this[point.first][point.second] != ch) {
                perimeter++
                continue
            }

            if (point in seen) {
                continue
            }
            seen += point

            area++

            point
                .neighbors()
                .forEach { queue += it }
        }

        return Region(ch, area, perimeter)
    }

    grid
        .findRegions(grid::findRegion)
        .foldRight(0) { r, acc -> acc + (r.perimeter * r.area) }
        .println()
}

private fun main2() {

    val grid = readInput("input12")
        .map { it.toCharArray() }
        .toTypedArray()

    data class Region(val ch: Char, val area: Int, val perimeter: Int, val sides: Int)

    fun Array<CharArray>.findRegion(ch: Char, i: Int, j: Int, seen: MutableSet<Point>): Region {
        var area = 0
        var perimeter = 0
        var corners = 0

        val queue = ArrayDeque<Point>()
        queue += Point(i, j)
        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()

            if (point !in this || this[point.first][point.second] != ch) {
                perimeter++
                continue
            }

            if (point in seen) {
                continue
            }
            seen += point

            area++

            val neighbors = point.neighbors()
            queue += neighbors

            listOf(neighbors, listOf(neighbors[0]))
                .flatten()
                .windowed(2)
                .forEach {
                    val p1 = it.first()
                    val p2 = it.last()
                    val p3 = Point(p2.first + p1.first - point.first, p2.second + p1.second - point.second)

                    if (p3 !in this || this[p3.first][p3.second] != ch) {
                        if ((p1 in this && p2 in this && this[p1.first][p1.second] == ch && this[p2.first][p2.second] == ch)
                            || (p1 !in this && p2 !in this)
                            || (p1 in this && this[p1.first][p1.second] != ch && p2 !in this)
                            || (p1 !in this && p2 in this && this[p2.first][p2.second] != ch)
                            || (p1 in this && p2 in this && this[p1.first][p1.second] != ch && this[p2.first][p2.second] != ch)) {
                            corners++
                        }
                    } else if (p3 in this && this[p3.first][p3.second] == ch) {
                        if (p1 in this && p2 in this && this[p1.first][p1.second] != ch && this[p2.first][p2.second] != ch) {
                            corners++
                        }
                    }
                }
        }

        return Region(ch, area, perimeter, corners)
    }


    grid
        .findRegions(grid::findRegion)
        .foldRight(0) { r, acc -> acc + (r.sides * r.area) }
        .println()
}



fun <T> Array<CharArray>.findRegions(regionFinder: (ch: Char, i: Int, j: Int, seen: MutableSet<Point>) -> T): List<T> {
    val regions = mutableListOf<T>()
    val seen = mutableSetOf<Point>()
    this.forEachIndexed { i, row ->
        row.forEachIndexed { j, ch ->
            if (Point(i, j) !in seen) {
                regions += regionFinder(ch, i, j, seen)
            }
        }
    }
    return regions
}

fun Point.neighbors(): List<Point> {
    val dirs = listOf(
        { x: Int, y: Int -> x - 1 to y },
        { x: Int, y: Int -> x to y + 1},
        { x: Int, y: Int -> x + 1 to y },
        { x: Int, y: Int -> x to y - 1},
    )

    return dirs
        .map { it(this.first, this.second) }
}

operator fun Array<out CharArray>.contains(p: Point): Boolean {
    return p.first in this.indices && p.second in this.first().indices
}