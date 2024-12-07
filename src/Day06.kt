import kotlin.math.abs

fun main() {
    val lines = readInput("input6")

    val grid = Array(lines.size) { CharArray(0) }

    lines.forEachIndexed { i, row ->
        grid[i] = row.toCharArray()
    }

    val dirs = listOf(
        { x: Int, y: Int -> x - 1 to y },
        { x: Int, y: Int -> x to y + 1 },
        { x: Int, y: Int -> x + 1 to y },
        { x: Int, y: Int -> x to y - 1 },
    )
    class Player(
        var x: Int,
        var y: Int,
    ) {
        var dirIndex = 0
        val path = mutableSetOf<Pair<Int, Int>>()

        init {
            path.add(Pair(x, y))
        }

        fun canMove(): Boolean {
            val (newX, newY) = dirs[dirIndex](x, y)
            return newX >= 0 && newX < grid.size && newY >= 0 && newY < grid[0].size
        }

        fun move() {
            val (newX, newY) = dirs[dirIndex](x, y)
            if (grid[newX][newY] == '#') {
                dirIndex = (dirIndex + 1) % 4
                move()
            } else {
                x = newX
                y = newY
                path += Pair(x, y)
            }
        }
    }

    var pos = Pair(0, 0)
    grid.forEachIndexed { i, row ->
        row.forEachIndexed { j, c ->
            if (c == '^') {
                pos = Pair(i, j)
            }
        }
    }
    val player = Player(pos.first, pos.second)
    while (player.canMove()) {
        player.move()
    }

    player.path.size.println()
}

private fun main2() {
    val lines = readInput("input6")

    val grid = Array(lines.size) { CharArray(0) }

    lines.forEachIndexed { i, row ->
        grid[i] = row.toCharArray()
    }

    val obstructions = mutableSetOf<Pair<Int, Int>>()
    var pos = Pair(0, 0)

    grid.forEachIndexed { i, row ->
        row.forEachIndexed { j, c ->
            if (c == '#') {
                obstructions += Pair(i, j)
            } else if (c == '^') {
                pos = Pair(i, j)
            }
        }
    }

    val dirs = listOf(
        { x: Int, y: Int -> x - 1 to y },
        { x: Int, y: Int -> x to y + 1 },
        { x: Int, y: Int -> x + 1 to y },
        { x: Int, y: Int -> x to y - 1 },
    )
    class Player(
        var x: Int,
        var y: Int,
    ) {
        var dirIndex = 0
        init {
            findNextDir()
        }

        fun findNextDir() {
            if (canMove()) {
                var (newX, newY) = dirs[dirIndex](x, y)
                while (grid[newX][newY] == '#') {
                    dirIndex = (dirIndex + 1) % 4
                    newX = dirs[dirIndex](x, y).first
                    newY = dirs[dirIndex](x, y).second
                }
            }
        }

        fun canMove(): Boolean {
            val (newX, newY) = dirs[dirIndex](x, y)
            return (newX >= 0 && newX < grid.size && newY >= 0 && newY < grid[0].size)
        }

        fun move() {
            val (newX, newY) = dirs[dirIndex](x, y)
            x = newX
            y = newY

            findNextDir()
        }
    }

    val newObstructions = mutableSetOf<Pair<Int, Int>>()
    for (i in grid.indices) {
        for (j in grid[0].indices) {
            if (grid[i][j] == '#' || (pos.first == i && pos.second == j))
                continue

            grid[i][j] = '#'
            val player = Player(pos.first, pos.second)
            val fastPlayer = Player(pos.first, pos.second)
            while (player.canMove() && fastPlayer.canMove()) {
                player.move()
                fastPlayer.move()
                if (fastPlayer.canMove()) {
                    fastPlayer.move()
                    if (fastPlayer.x == player.x && fastPlayer.y == player.y && fastPlayer.dirIndex == player.dirIndex) {
                        newObstructions += Pair(i, j)
                        break
                    }
                }
            }
            grid[i][j] = '.'
        }
    }

    newObstructions.size.println()
}
