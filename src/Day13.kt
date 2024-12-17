import java.util.Stack
import java.util.regex.Pattern
import kotlin.math.min

val buttonXRegex = Pattern.compile("X\\+(\\d+)").toRegex()
val buttonYRegex = Pattern.compile("Y\\+(\\d+)").toRegex()
val prizeXRegex = Pattern.compile("X=(\\d+)").toRegex()
val prizeYRegex = Pattern.compile("Y=(\\d+)").toRegex()

fun main() {
    readInput("input13")
        .windowed(3, 4)
        .map {
            it
                .mapIndexed { i, s ->
                    when (i) {
                        0, 1 -> Vector(buttonXRegex.firstInt(s).toULong(), buttonYRegex.firstInt(s).toULong())
                        2 -> Vector(prizeXRegex.firstInt(s).toULong(), prizeYRegex.firstInt(s).toULong())
                        else -> throw IllegalStateException()
                    }
                }
        }.foldRight(0UL) { system, acc ->
            acc + system.minimumCost()
        }.println()
}


private fun main2() {
    readInput("input13")
        .windowed(3, 4)
        .map {
            it
                .mapIndexed { i, s ->
                    when (i) {
                        0, 1 -> Vector(buttonXRegex.firstInt(s).toULong(), buttonYRegex.firstInt(s).toULong())
                        2 -> Vector(prizeXRegex.firstInt(s).toULong() + 10000000000000UL, prizeYRegex.firstInt(s).toULong() + 10000000000000UL)
                        else -> throw IllegalStateException()
                    }
                }
        }.foldRight(0UL) { system, acc ->
            acc + system.cost()
        }.println()
}

data class Vector(val a: ULong, val b: ULong)

fun List<Vector>.minimumCost(): ULong {
    val M = this[0]
    val N = this[1]
    val X = this[2]

    var minV = Vector(ULong.MAX_VALUE, ULong.MAX_VALUE)

    val stack = Stack<Vector>()
    stack += Vector(0UL, 0UL)
    val seen = mutableSetOf<Vector>()

    while (stack.isNotEmpty()) {
        val v = stack.pop()

        if (v in seen) {
            continue
        }
        seen += v

        val sum = v.a * M + v.b * N
        if (v.a > 100UL || v.b > 100UL || sum.a > X.a || sum.b > X.b) {
            continue
        }

        if (sum == X) {
            if (v.a * 3UL + v.b < minV.a + minV.b) {
                minV = v
            }
            continue
        }

        stack += Vector(v.a + 1UL, v.b)
        stack += Vector(v.a, v.b + 1UL)
    }

    if (minV == Vector(ULong.MAX_VALUE, ULong.MAX_VALUE))
        return 0UL

    minV.println()

    return minV.a * 3UL + minV.b
}

fun List<Vector>.cost(): ULong {
    val M = this[0]
    val N = this[1]
    val X = this[2]

    val det = M.a.toLong() * N.b.toLong() - N.a.toLong() * M.b.toLong()
    require(det != 0L)

    val a = ((N.b * X.a - N.a * X.b).toLong() / det).toULong()
    val b = ((M.a * X.b - M.b * X.a).toLong() / det).toULong()

    if (a * M + b * N != X)
        return 0UL

    return a * 3UL + b
}

fun List<Vector>.det(): Long {
    val M = this[0]
    val N = this[1]
    return M.a.toLong() * N.b.toLong() - N.a.toLong() * M.b.toLong()
 }

fun Regex.firstInt(s: String): Int = this.find(s)!!.groups[1]!!.value.toInt()

operator fun ULong.times(v: Vector): Vector = Vector(this * v.a, this * v.b)

operator fun Vector.plus(v: Vector): Vector = Vector(this.a + v.a, this.b + v.b)