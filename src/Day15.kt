fun main() {
    val lines = readInput("input15")
    val whLines = lines
        .takeWhile { it.isNotBlank() }

    val warehouse = toWarehouse(whLines)
    val moves = lines
        .asReversed()
        .takeWhile { it.isNotBlank() }
        .map { toMoves(it) }
        .asReversed()
        .flatten()

    moves
        .forEach {
            warehouse.doMove(it)
        }

    warehouse
        .sumOfAllGPS()
        .println()
}

private fun main2() {
    val lines = readInput("input15")
    val whLines = lines
        .takeWhile { it.isNotBlank() }

    val moves = lines
        .asReversed()
        .takeWhile { it.isNotBlank() }
        .map { toMoves(it) }
        .asReversed()
        .flatten()

    val wideWarehouse = toWideWarehouse(whLines)

    moves
        .forEach {
            wideWarehouse.doMove(it)
        }

    wideWarehouse
        .sumOfAllGPS()
        .println()
}

data class Warehouse(
    val m: Int,
    val n: Int,
    val grid: List<CharArray>
) {
    private lateinit var robotPos: Vector2D
    private val moves = mutableMapOf(
        Move.UP to Vector2D(-1, 0),
        Move.RIGHT to Vector2D(0, 1),
        Move.DOWN to Vector2D(1, 0),
        Move.LEFT to Vector2D(0, -1),
    )

    init {
        loop@ for ((i, row) in grid.withIndex()) {
            for ((j, c) in row.withIndex()) {
                if (c == '@') {
                    robotPos = Vector2D(i, j)
                    break@loop
                }
            }
        }
    }

    fun doMove(move: Move) {
        advanceRobot(moves[move]!!)
    }

    fun sumOfAllGPS(): Int {
        return grid
            .foldRightIndexed(0) { i, row, iAcc ->
                iAcc + row.foldRightIndexed(0) { j, c, jAcc ->
                    jAcc + when (c) {
                        'O' -> 100 * i + j
                        else -> 0
                    }
                }
            }
    }

    private fun advanceRobot(step: Vector2D) {
        val nextPos = robotPos + step
        val nextCh = grid[nextPos.x][nextPos.y]
        when (nextCh) {
            '#' -> {} // nothing
            'O' -> {
                if (moveBoxes(nextPos, step)) {
                    advanceRobot(step)
                }
            }
            '.' -> {
                grid[robotPos.x][robotPos.y] = '.'
                robotPos = nextPos
                grid[robotPos.x][robotPos.y] = '@'
            }
        }
    }

    private fun moveBoxes(firstBox: Vector2D, step: Vector2D): Boolean {
        var current = firstBox
        while (grid[current.x][current.y] == 'O') {
            current += step
        }
        when (grid[current.x][current.y]) {
            '#' -> return false
            '.' -> {
                grid[firstBox.x][firstBox.y] = '.'
                grid[current.x][current.y] = 'O'
                return true
            }
            else -> error("Invalid obstacle")
        }
    }

    override fun toString(): String {
        return grid.joinToString("\n") { String(it) }
    }
}


data class WideWarehouse(
    val m: Int,
    val n: Int,
    val grid: List<CharArray>
) {
    private lateinit var robotPos: Vector2D
    private val moves = mutableMapOf(
        Move.UP to Vector2D(-1, 0),
        Move.RIGHT to Vector2D(0, 1),
        Move.DOWN to Vector2D(1, 0),
        Move.LEFT to Vector2D(0, -1),
    )

    init {
        loop@ for ((i, row) in grid.withIndex()) {
            for ((j, c) in row.withIndex()) {
                if (c == '@') {
                    robotPos = Vector2D(i, j)
                    break@loop
                }
            }
        }
    }

    fun doMove(move: Move) {
        advanceRobot(moves[move]!!)
    }

    fun sumOfAllGPS(): Int {
        return grid
            .foldRightIndexed(0) { i, row, iAcc ->
                iAcc + row.foldRightIndexed(0) { j, c, jAcc ->
                    jAcc + when (c) {
                        '[' -> 100 * i + j
                        else -> 0
                    }
                }
            }
    }

    private fun advanceRobot(step: Vector2D) {
        val nextPos = robotPos + step
        val nextCh = grid[nextPos.x][nextPos.y]
        when (nextCh) {
            '#' -> {} // nothing
            '[', ']' -> {
                if (moveBoxes(nextPos, step)) {
                    advanceRobot(step)
                }
            }
            '.' -> {
                grid[robotPos.x][robotPos.y] = '.'
                robotPos = nextPos
                grid[robotPos.x][robotPos.y] = '@'
            }
        }
    }

    private fun moveBoxes(firstBox: Vector2D, step: Vector2D): Boolean {
        val initialBox = mutableListOf<Vector2D>()
        initialBox += firstBox
        initialBox += when (grid[firstBox.x][firstBox.y]) {
            '[' -> Vector2D(firstBox.x, firstBox.y + 1)
            ']' -> Vector2D(firstBox.x, firstBox.y - 1)
            else -> error("Not a box")
        }

        val boxes = mutableSetOf<Vector2D>()
        val queue = ArrayDeque<Vector2D>()
        queue += initialBox
        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()
            if (v in boxes) {
                continue
            }
            boxes += v

            val next = v + step
            if (grid[next.x][next.y] == '[' || grid[next.x][next.y] == ']') {
                queue += next
                // UP and DOWN movement can move more than 2 boxes
                if (step.x != 0 && grid[next.x][next.y] != grid[v.x][v.y]) {
                    queue += when (grid[next.x][next.y]) {
                        '[' -> Vector2D(next.x, next.y + 1)
                        ']' -> Vector2D(next.x, next.y - 1)
                        else -> error("Not a box")
                    }
                }
            } else if (grid[next.x][next.y] != '.') {
                return false
            }
        }

        val oldGrid = boxes.associateWith { grid[it.x][it.y] }
        boxes
            .forEach {
                grid[it.x][it.y] = '.'
            }
        oldGrid
            .entries
            .forEach { (v, c) ->
                val next = v + step
                grid[next.x][next.y] = c
            }

        return true
    }

    override fun toString(): String {
        return grid.joinToString("\n") { String(it) }
    }
}

enum class Move {
    UP, RIGHT, DOWN, LEFT
}

fun toWarehouse(whLines: List<String>): Warehouse {
    val m = whLines.size
    val n = whLines.first().length
    val grid = MutableList(m) { CharArray(n) }
    whLines
        .forEachIndexed { i, row ->
            grid[i] = row.toCharArray()
        }
    return Warehouse(m, n, grid)
}

fun toWideWarehouse(whLines: List<String>): WideWarehouse {
    val m = whLines.size
    val n = whLines.first().length * 2
    val grid = mutableListOf<CharArray>()
    whLines
        .forEachIndexed { i, row ->
            grid += row
                .map {
                    when(it) {
                        '#' -> "##"
                        'O' -> "[]"
                        '@' -> "@."
                        '.' -> ".."
                        else -> error("Invalid character")
                    }
                }
                .joinToString("")
                .toCharArray()
        }
    return WideWarehouse(m, n, grid)
}

fun toMoves(s: String): List<Move> {
    return s
        .map {
           when (it) {
               '^' -> Move.UP
               '>' -> Move.RIGHT
               'v' -> Move.DOWN
               '<' -> Move.LEFT
               else -> error("Invalid move")
           }
        }
}

operator fun Vector2D.plus(o: Vector2D): Vector2D {
    return Vector2D(this.x + o.x, this.y + o.y)
}


operator fun Vector2D.minus(o: Vector2D): Vector2D {
    return Vector2D(this.x - o.x, this.y - o.y)
}
