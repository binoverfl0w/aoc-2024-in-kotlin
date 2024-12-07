fun main() {
    val it = readInput("input5").iterator()

    val rules = mutableMapOf<Int, MutableSet<Int>>()
    for (line in it) {
        if (line.isBlank())
            break

        val nums = line.split("|").map { it.toInt() }
        if (nums[0] !in rules) {
            rules[nums[0]] = mutableSetOf()
        }
        rules[nums[0]]?.add(nums[1])
    }

    var total = 0

    it.forEachRemaining { line ->
        val update = line.split(",").map { it.toInt() }
        val seen = mutableSetOf<Int>()

        var correct = true
        run loop@ {
            update.forEach {
                seen += it

                val after = rules[it]
                after?.forEach { n ->
                    if (seen.contains(n)) {
                        correct = false
                        return@loop
                    }
                }
            }
        }

        if (correct)
            total += update[update.size / 2]
    }

    total.println()
}

private fun main2() {
    val it = readInput("input5").iterator()

    val rules = mutableMapOf<Int, MutableSet<Int>>()
    for (line in it) {
        if (line.isBlank())
            break

        val nums = line.split("|").map { it.toInt() }
        if (nums[0] !in rules) {
            rules[nums[0]] = mutableSetOf()
        }
        rules[nums[0]]?.add(nums[1])
    }

    var total = 0

    it.forEachRemaining { line ->
        var update = line.split(",").map { it.toInt() }
        var seen = mutableSetOf<Int>()

        var newUpdate = mutableListOf<Int>()

        var toCount = true
        while (true) {
            var correct = true
            update.forEach {
                seen += it
                newUpdate += it

                val after = rules[it]
                after?.forEach { n ->
                    if (seen.contains(n)) {
                        newUpdate -= n
                        seen -= n
                        newUpdate += n
                        correct = false
                        toCount = false
                    }
                }
            }

            if (!correct) {
                update = ArrayList(newUpdate)
                newUpdate = mutableListOf()
                seen = mutableSetOf()
            } else {
                break
            }
        }

        if (!toCount)
            total += newUpdate[newUpdate.size / 2]
    }

    total.println()
}