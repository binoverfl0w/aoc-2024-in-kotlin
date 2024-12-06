import java.util.Stack
import kotlin.math.abs

fun main() {
    var safeReports = 0

    fun isSafe(level: String): Boolean {
        val arr = level.toIntArray(whiteSpacePattern)
        if (arr.size == 1)
            return true

        val dir = if (arr[0] > arr[1]) { -1 } else { 1 }

        for (i in 1..< arr.size) {
            val diff = arr[i - 1] - arr[i]
            if (abs(diff) < 1 || abs(diff) > 3) {
                return false
            }

            val currentDir = if (diff > 0) { -1 } else { 1 }
            if (currentDir != dir) {
                return false
            }
        }
        return true
    }

    readInput("input2").forEach {
        if (isSafe(it)) {
            safeReports++
        }
    }

    safeReports.println()
}

private fun main2() {
    var safeReports = 0

    fun isSafe(arr: IntArray): Boolean {
        if (arr.size <= 1)
            return true

        val dir = if (arr[0] > arr[1]) { -1 } else { 1 }

        for (i in 1..< arr.size) {
            val diff = arr[i - 1] - arr[i]
            val currentDir = if (diff > 0) { -1 } else { 1 }

            if (abs(diff) < 1 || abs(diff) > 3 || currentDir != dir) {
                return false
            }
        }
        return true
    }

    readInput("input2").forEach {
        val arr = it.toIntArray(whiteSpacePattern)

        if (isSafe(arr)) {
            safeReports++
        } else {
            for (i in arr.indices) {
                val newArr = IntArray(arr.size - 1)
                System.arraycopy(arr, 0, newArr, 0, i)
                System.arraycopy(arr, i + 1, newArr, i, arr.size - i - 1)
                if (isSafe(newArr)) {
                    safeReports++
                    break
                }
            }
        }
    }

    safeReports.println()
}