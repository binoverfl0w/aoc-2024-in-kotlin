import java.util.*

fun main1() {
    val lines = readInput("input16")
    val maze = lines.map { it.toCharArray() }
    var start = Vector2D(-1, -1)
    var end = Vector2D(-1, -1)
    maze
        .forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                when (c) {
                    'S' -> start = Vector2D(i, j)
                    'E' -> end = Vector2D(i, j)
                }
            }
        }

    val (dist, _) = dijkstra(maze, Reindeer(start, Direction.E))

    val minCost = Direction
        .entries
        .minOf {
            dist[Reindeer(end, it)]!!
        }

    minCost.println()
}

fun main() {
    val lines = readInput("input16")
    val maze = lines.map { it.toCharArray() }
    var start = Vector2D(-1, -1)
    var end = Vector2D(-1, -1)
    maze
        .forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                when (c) {
                    'S' -> start = Vector2D(i, j)
                    'E' -> end = Vector2D(i, j)
                }
            }
        }

    val (_, prev) = dijkstraModified(maze, Reindeer(start, Direction.E))

    val totalTilesInBestPaths = Direction
        .entries
        .minOf {
            val uniquePositions = mutableSetOf<Vector2D>()

            val stack = Stack<Reindeer>()
            stack += Reindeer(end, it)
            val seen = mutableSetOf<Reindeer>()
            while (stack.isNotEmpty()) {
                val v = stack.pop()
                maze[v.pos.x][v.pos.y] = 'O'

                uniquePositions += v.pos
                if (v in seen) {
                    continue
                }
                seen += v

                for (w in prev[v]!!) {
                    stack += w
                }
            }

            uniquePositions.size
        }

    totalTilesInBestPaths.println()
}

data class Reindeer(
    val pos: Vector2D,
    var dir: Direction,
)

fun dijkstra(grid: List<CharArray>, source: Reindeer): Pair<MutableMap<Reindeer, Int>, MutableMap<Reindeer, Reindeer>> {
    val dist = mutableMapOf<Reindeer, Int>()
    val prev = mutableMapOf<Reindeer, Reindeer>()

    val queue = PriorityQueue<Reindeer>() { a, b -> dist[a]!! - dist[b]!! }
    queue += source

    grid.forEachIndexed { i, row ->
        row.forEachIndexed { j, _ ->
            Direction
                .entries
                .forEach { dir ->
                    val r = Reindeer(Vector2D(i, j), dir)
                    dist[r] = Int.MAX_VALUE
                }
        }
    }

    dist[source] = 0

    fun edge(u: Reindeer, v: Reindeer): Int {
        if (grid[v.pos.x][v.pos.y] == '#'
            || u.pos != v.pos && u.dir != findDirection(u.pos, v.pos))
            return Int.MAX_VALUE

        if (u.dir != v.dir)
            return 1000
        return 1
    }

    while (queue.isNotEmpty()) {
        val u = queue.poll()

        arrayOf(
            Reindeer(u.pos + Vector2D(-1, 0), u.dir),
            Reindeer(u.pos + Vector2D(0, 1), u.dir),
            Reindeer(u.pos + Vector2D(1, 0), u.dir),
            Reindeer(u.pos + Vector2D(0, -1), u.dir),
            Reindeer(u.pos, Direction.N),
            Reindeer(u.pos, Direction.E),
            Reindeer(u.pos, Direction.S),
            Reindeer(u.pos, Direction.W),
        )
            .filter {
                it != u && it in dist
            }
            .forEach { v ->
                val du = dist[u]!!
                val dv = dist[v]!!

                val e = edge(u, v)
                val alt =
                    if (du == Int.MAX_VALUE || e == Int.MAX_VALUE) {
                        Int.MAX_VALUE
                    } else {
                        du + e
                    }

                if (alt < dv) {
                    prev[v] = u
                    dist[v] = alt
                    queue += v
                }
            }
    }

    return Pair(dist, prev)
}

fun dijkstraModified(grid: List<CharArray>, source: Reindeer): Pair<MutableMap<Reindeer, Int>, MutableMap<Reindeer, MutableList<Reindeer>>> {
    val dist = mutableMapOf<Reindeer, Int>()
    val prev = mutableMapOf<Reindeer, MutableList<Reindeer>>()

    val queue = PriorityQueue<Reindeer>() { a, b -> dist[a]!! - dist[b]!! }
    queue += source

    grid.forEachIndexed { i, row ->
        row.forEachIndexed { j, _ ->
            Direction
                .entries
                .forEach { dir ->
                    val r = Reindeer(Vector2D(i, j), dir)
                    dist[r] = Int.MAX_VALUE
                    prev[r] = mutableListOf()
                }
        }
    }

    dist[source] = 0

    fun edge(u: Reindeer, v: Reindeer): Int {
        if (grid[v.pos.x][v.pos.y] == '#'
            || u.pos != v.pos && u.dir != findDirection(u.pos, v.pos))
            return Int.MAX_VALUE

        if (u.dir != v.dir)
            return 1000
        return 1
    }

    while (queue.isNotEmpty()) {
        val u = queue.poll()

        arrayOf(
            Reindeer(u.pos + Vector2D(-1, 0), u.dir),
            Reindeer(u.pos + Vector2D(0, 1), u.dir),
            Reindeer(u.pos + Vector2D(1, 0), u.dir),
            Reindeer(u.pos + Vector2D(0, -1), u.dir),
            Reindeer(u.pos, Direction.N),
            Reindeer(u.pos, Direction.E),
            Reindeer(u.pos, Direction.S),
            Reindeer(u.pos, Direction.W),
        )
            .filter {
                it != u && it in dist
            }
            .forEach { v ->
                val du = dist[u]!!
                val dv = dist[v]!!

                val e = edge(u, v)
                val alt =
                    if (du == Int.MAX_VALUE || e == Int.MAX_VALUE) {
                        Int.MAX_VALUE
                    } else {
                        du + e
                    }

                if (alt < dv) {
                    prev[v]!! += u
                    dist[v] = alt
                    queue += v
                } else if (alt == dv) {
                    prev[v]!! += u
                }
            }
    }

    return Pair(dist, prev)
}

fun findDirection(prev: Vector2D, last: Vector2D): Direction {
    val xDiff = last.x - prev.x
    val yDiff = last.y - prev.y
    return if (xDiff == 1) {
        Direction.S
    } else if (xDiff == -1) {
        Direction.N
    } else if (yDiff == 1) {
        Direction.E
    } else if (yDiff == -1) {
        Direction.W
    } else {
        error("Invalid direction")
    }
}

enum class Direction {
    N, E, S, W
}
