import kotlin.math.abs

val DIRS = arrayOf(
    Vector2D(-1, 0),
    Vector2D(0, 1),
    Vector2D(1, 0),
    Vector2D(0, -1),
)

fun main() {
    val grid = readInput("input20")
        .map { it.toCharArray() }

    var start = Vector2D(-1, -1)
    var end = Vector2D(-1, -1)
    grid.forEachIndexed { i, row ->
        row.forEachIndexed { j, c ->
            when (c) {
                'S' -> start = Vector2D(i, j)
                'E' -> end = Vector2D(i, j)
                else -> {}
            }
        }
    }

    val path = mutableListOf<Pair<Vector2D, Int>>()
    var cost = 0
    var cur = start
    while (true) {
        path += Pair(cur, cost)

        if (cur == end)
            break

        grid[cur.x][cur.y] = '#'

        cost += 1

        for (dir in DIRS) {
            val newPosition = cur + dir
            if (grid[newPosition.x][newPosition.y] != '#') {
                cur = newPosition
                break
            }
        }
    }

    val allowedCheats = 2
    // part 2
    // val allowedCheats = 20

    val savedPicoseconds = 100

    var total = 0
    for (i in 0..<path.size) {
        val (iPos, iCost) = path[i]
        var j = i + savedPicoseconds
        while (j < path.size) {
            val (jPos, jCost) = path[j]

            val dist = abs(jPos.x - iPos.x) + abs(jPos.y - iPos.y)

            if (dist <= allowedCheats && jCost - iCost - dist >= savedPicoseconds) {
                total++
            } else if (dist > allowedCheats) {
                j += dist - allowedCheats
                continue
            }
            j++
        }
    }

    total.println()
}