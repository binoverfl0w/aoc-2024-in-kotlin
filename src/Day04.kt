fun main() {
    val lines = readInput("input4")

    val grid = Array(lines.size) { CharArray(lines.size) }

    lines.forEachIndexed { i, s ->
        s.forEachIndexed { j, c ->
            grid[i][j] = c
        }
    }

    val dirs = listOf(
        { x: Int, y: Int -> x - 1 to y },
        { x: Int, y: Int -> x + 1 to y },
        { x: Int, y: Int -> x to y - 1 },
        { x: Int, y: Int -> x to y + 1 },
        { x: Int, y: Int -> x - 1 to y - 1 },
        { x: Int, y: Int -> x + 1 to y - 1},
        { x: Int, y: Int -> x - 1 to y + 1},
        { x: Int, y: Int -> x + 1 to y + 1},
    )

    var total = 0

    for (i in grid.indices) {
        for (j in grid[0].indices) {
            if (grid[i][j] != 'X')
                continue

            for (dir in dirs) {
                var s = "X"
                var pair = Pair(i, j)
                for (z in 0..<3) {
                    pair = dir(pair.first, pair.second)
                    val (x, y) = pair

                    if (x < 0 || x >= grid.size || y < 0 || y >= grid[0].size)
                        break
                    s += grid[x][y]
                }
                if (s == "XMAS") {
                    total++
                }
            }
        }
    }

    total.println()
}

private fun main2() {
    val lines = readInput("input4")

    val grid = Array(lines.size) { CharArray(lines[0].length) }

    lines.forEachIndexed { i, s ->
        s.forEachIndexed { j, c ->
            grid[i][j] = c
        }
    }

    fun xMAS(i: Int, j: Int): Boolean {
        if (i <= 0 || i >= grid.size - 1 || j <= 0 || j >= grid[0].size - 1) {
            return false
        }

        var s = ""
        for ((x, y) in listOf(-1 to -1, -1 to 1, 1 to 1, 1 to -1)) {
            s += grid[i + x][j + y]
        }

        return s in setOf("MMSS", "MSSM", "SSMM", "SMMS")
    }

    var total = 0
    for (i in grid.indices) {
        for (j in grid[0].indices) {
            if (grid[i][j] != 'A') {
                continue
            }

            if (xMAS(i, j)) {
                total++
            }
        }
    }

    total.println()
}