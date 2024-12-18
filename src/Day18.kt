import kotlin.collections.ArrayDeque
import kotlin.collections.List

fun main() {
    val n = 70 + 1
    val grid = Array(n) { IntArray(n) }

    readInput("input18")
        .filterIndexed { i, _ -> i < 1024 }
        .forEach { s ->
            val (x, y) = s.split(",")
            grid[y.toInt()][x.toInt()] = 1
        }

    val queue = ArrayDeque<List<Pair<Int, Int>>>()
    queue += listOf(Pair(0, 0))
    val seen = mutableSetOf<Pair<Int, Int>>()
    while (queue.isNotEmpty()) {
        val path = queue.removeFirst()
        val coords = path.last()

        if (coords == Pair(n - 1, n - 1)) {
            (path.size - 1).println()
            break
        }

        if (coords in seen || coords.first < 0 || coords.first >= n || coords.second < 0 || coords.second >= n || grid[coords.first][coords.second] == 1) {
            continue
        }
        seen += coords

        queue += path + Pair(coords.first - 1, coords.second)
        queue += path + Pair(coords.first + 1, coords.second)
        queue += path + Pair(coords.first, coords.second - 1)
        queue += path + Pair(coords.first, coords.second + 1)
    }
}

private fun main2() {
    fun bfs(grid: Array<IntArray>): Int {
        var shortestPath = listOf<Pair<Int, Int>>()
        val queue = ArrayDeque<List<Pair<Int, Int>>>()
        queue += listOf(Pair(0, 0))
        val seen = mutableSetOf<Pair<Int, Int>>()
        while (queue.isNotEmpty()) {
            val path = queue.removeFirst()
            val coords = path.last()

            if (coords == Pair(grid.size - 1, grid.size - 1)) {
                shortestPath = path
                break
            }

            if (coords in seen || coords.first < 0 || coords.first >= grid.size || coords.second < 0 || coords.second >= grid.size || grid[coords.first][coords.second] == 1) {
                continue
            }
            seen += coords

            queue += path + Pair(coords.first - 1, coords.second)
            queue += path + Pair(coords.first + 1, coords.second)
            queue += path + Pair(coords.first, coords.second - 1)
            queue += path + Pair(coords.first, coords.second + 1)
        }

        return shortestPath.size
    }

    val n = 70 + 1
    val grid = Array(n) { IntArray(n) }

    readInput("input18")
        .forEach { s ->
            val (x, y) = s.split(",")
            grid[y.toInt()][x.toInt()] = 1

            if (bfs(grid) == 0) {
                Pair(x, y).println()
                return
            }
        }
}
