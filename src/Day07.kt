fun main() {

    fun checkCombinations(num: ULong, currentTotal: ULong = 0UL, start: Int = 0, list: List<ULong>): Boolean {
        if (start >= list.size) {
            return currentTotal == num
        }
        return checkCombinations(num, currentTotal + list[start], start + 1, list)
                || checkCombinations(num, if (start == 0) { list[start] } else { currentTotal * list[start] }, start + 1, list)
    }

    var total = 0UL
    readInput("input7").forEach {
        val tmp = it.split(":")
        val num = tmp[0].toULong()
        val list = tmp[1].trim().split(whiteSpacePattern).map { it.toULong() }

        if (checkCombinations(num, list = list)) {
            total += num
        }
    }

    total.println()
}

private fun main2() {

    fun checkCombinations(num: ULong, currentTotal: ULong = 0UL, start: Int = 0, list: List<ULong>): Boolean {
        if (start >= list.size) {
            return currentTotal == num
        }
        return checkCombinations(num, currentTotal + list[start], start + 1, list)
                || checkCombinations(num, if (start == 0) { list[start] } else { currentTotal * list[start]}, start + 1, list)
                || checkCombinations(num, if (start == 0) { list[start] } else { "$currentTotal${list[start]}".toULong() }, start + 1, list)
    }

    var total = 0UL
    readInput("input7").forEach {
        val tmp = it.split(":")
        val num = tmp[0].toULong()
        val list = tmp[1].trim().split(whiteSpacePattern).map { it.toULong() }

        if (checkCombinations(num, list = list)) {
            total += num
        }
    }

    total.println()
}
