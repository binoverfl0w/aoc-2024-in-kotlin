import java.util.*
import kotlin.math.abs

fun main() {
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

private fun main2() {
    val occurrences = mutableMapOf<Int, Int>()
    val list1 = mutableListOf<Int>()

    readInput("input1").forEach {
        val nums = it.split(whiteSpacePattern)

        list1 += nums[0].toInt()
        occurrences.merge(nums[1].toInt(), 1) { a, b -> a + b }
    }

    var total = 0

    for (n in list1) {
        total += n * occurrences.getOrDefault(n, 0)
    }

    total.println()
}
