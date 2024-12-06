import java.util.*
import java.util.regex.Pattern
import kotlin.math.abs

fun main() {
    val whiteSpacePattern = Pattern.compile("\\s+")

    val list1 = PriorityQueue<Int>()
    val list2 = PriorityQueue<Int>()

    readInput("input1").forEach {
        val nums = it.split(whiteSpacePattern)

        list1 += nums[0].toInt()
        list2 += nums[1].toInt()
    }

    var total = 0

    while (!list1.isEmpty()) {
        total += abs(list1.poll() - list2.poll())
    }

    total.println()
}
