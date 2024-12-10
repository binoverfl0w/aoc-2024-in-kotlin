import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List

fun main() {
    val grid = MutableList(0) { IntArray(0) }
    readInput("input10").forEach { line ->
        grid += line
            .toCharArray()
            .map { it.digitToInt() }
            .toIntArray()
    }

    var total = 0
    grid.forEachIndexed { i, row ->
        row.forEachIndexed { j, c ->
            if (grid[i][j] == 0) {
                total += grid.trailheadScore(i, j)
            }
        }
    }

    total.println()
}

fun MutableList<IntArray>.trailheadScore(i: Int, j: Int): Int {
    val path = Stack<Int>()
    val uniqueNines = mutableSetOf<Pair<Int, Int>>()


    fun backtrack(i: Int, j: Int) {
        if (i < 0 || i >= this.size || j < 0 || j >= this.first().size) {
            return
        }
        if (!path.isEmpty() && this[i][j] - path.peek() != 1) {
            return
        }

        if (this[i][j] == 9) {
            uniqueNines += Pair(i, j)
            return
        }

        path += this[i][j]
        backtrack(i + 1, j)
        backtrack(i - 1, j)
        backtrack(i, j + 1)
        backtrack(i, j - 1)
        path.pop()
    }

    backtrack(i, j)

    return uniqueNines.size
}

private fun main2() {
    val grid = MutableList(0) { IntArray(0) }
    readInput("input10").forEach { line ->
        grid += line
            .toCharArray()
            .map { it.digitToInt() }
            .toIntArray()
    }

    var total = 0
    grid.forEachIndexed { i, row ->
        row.forEachIndexed { j, c ->
            if (grid[i][j] == 0) {
                total += grid.trailheadRating(i, j)
            }
        }
    }

    total.println()
}

fun MutableList<IntArray>.trailheadRating(i: Int, j: Int): Int {
    val path = Stack<Int>()
    val distinctTrails = mutableListOf<List<Pair<Int, Int>>>()


    val trail = mutableListOf<Pair<Int, Int>>()
    fun backtrack(i: Int, j: Int) {
        if (i < 0 || i >= this.size || j < 0 || j >= this.first().size) {
            return
        }
        if (!path.isEmpty() && this[i][j] - path.peek() != 1) {
            return
        }

        if (this[i][j] == 9) {
            distinctTrails += ArrayList(trail)
            return
        }

        path += this[i][j]
        trail += Pair(i, j)

        backtrack(i + 1, j)
        backtrack(i - 1, j)
        backtrack(i, j + 1)
        backtrack(i, j - 1)

        path.pop()
        trail.removeLast()
    }

    backtrack(i, j)

    return distinctTrails.size
}
