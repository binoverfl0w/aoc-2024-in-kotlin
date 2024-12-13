fun main() {
    var state = mutableListOf<ULong>()

    readInput("input11")[0]
        .split(whiteSpacePattern)
        .mapTo(state) { it.toULong() }


    fun MutableList<ULong>.nextIteration(): MutableList<ULong> {
        val nextState = mutableListOf<ULong>()

        this.forEach {
            if (it == 0UL) {
                nextState += 1UL
            } else if (it.toString().length % 2 == 0) {
                val len = it.toString().length
                nextState += it.toString().substring(0, len / 2).toULong()
                nextState += it.toString().substring(len / 2, len).toULong()
            } else {
                nextState += it * 2024UL
            }
        }

        return nextState
    }

    for (i in 1..75) {
        state = state.nextIteration()
        println("$i ${state.size}")
    }

    state.size.println()
}

private fun main2() {
    val nums = mutableListOf<ULong>()

    readInput("input11")[0]
        .split(whiteSpacePattern)
        .mapTo(nums) { it.toULong() }

    val cached = mutableMapOf<Pair<ULong, Int>, ULong>()

    fun stones(n: ULong, blinks: Int): ULong {
        val key = Pair(n, blinks)
        if (key in cached) {
            return cached[key]!!
        }

        if (blinks == 0) {
            return 1UL
        }

        if (n == 0UL) {
            val nextStone = Pair(1UL, blinks - 1)
            val created = stones(nextStone.first, nextStone.second)
            cached[nextStone] = created
            return created
        } else if (n.toString().length % 2 == 0) {
            val len = n.toString().length
            val fHalfStone = Pair(n.toString().substring(0, len / 2).toULong(), blinks - 1)
            val sHalfStone = Pair(n.toString().substring(len / 2, len).toULong(), blinks - 1)
            val fHalfCreated = stones(fHalfStone.first, fHalfStone.second)
            val sHalfCreated = stones(sHalfStone.first, sHalfStone.second)
            cached[fHalfStone] = fHalfCreated
            cached[sHalfStone] = sHalfCreated
            return fHalfCreated + sHalfCreated
        } else {
            val nextStone = Pair(n * 2024UL, blinks - 1)
            val created = stones(nextStone.first, nextStone.second)
            cached[nextStone] = created
            return created
        }
    }

    var total = 0UL

    nums.forEach {
        total += stones(it, 75)
    }

    total.println()
}
