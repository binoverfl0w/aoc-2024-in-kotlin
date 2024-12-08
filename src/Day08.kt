fun main() {
    val lines = readInput("input8")
    val grid = Array(lines.size) { CharArray(lines.first().length) }

    val freqMap = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    lines.forEachIndexed { i, row ->
        grid[i] = row.toCharArray()
    }

    grid.forEachIndexed { i, row ->
        row.forEachIndexed loop@{ j, c ->
            if (!c.isLetterOrDigit())
                return@loop
            if (c !in freqMap)
                freqMap[c] = mutableListOf()
            freqMap[c]?.add(Pair(i, j))
        }
    }

    val antinodes = mutableSetOf<Pair<Int, Int>>()

    for (sameFreq in freqMap.values) {
        for (i in 0..<sameFreq.size - 1) {
            for (j in i + 1..<sameFreq.size) {
                var aNode = Pair(
                    sameFreq[i].first + (sameFreq[i].first - sameFreq[j].first),
                    sameFreq[i].second + (sameFreq[i].second - sameFreq[j].second),
                )
                if (aNode.first >= 0 && aNode.first < grid.size && aNode.second >= 0 && aNode.second < grid.first().size) {
                    antinodes += aNode
                }

                aNode = Pair(
                    sameFreq[j].first + (sameFreq[j].first - sameFreq[i].first),
                    sameFreq[j].second + (sameFreq[j].second - sameFreq[i].second),
                )
                if (aNode.first >= 0 && aNode.first < grid.size && aNode.second >= 0 && aNode.second < grid.first().size) {
                    antinodes += aNode
                }
            }
        }
    }

    antinodes.size.println()
}

private fun main2() {
    val lines = readInput("input8")
    val grid = Array(lines.size) { CharArray(lines.first().length) }

    val freqMap = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    lines.forEachIndexed { i, row ->
        grid[i] = row.toCharArray()
    }

    grid.forEachIndexed { i, row ->
        row.forEachIndexed loop@{ j, c ->
            if (!c.isLetterOrDigit())
                return@loop
            if (c !in freqMap)
                freqMap[c] = mutableListOf()
            freqMap[c]?.add(Pair(i, j))
        }
    }

    val antinodes = mutableSetOf<Pair<Int, Int>>()

    for (sameFreq in freqMap.values) {
        for (i in 0..<sameFreq.size - 1) {
            for (j in i + 1..<sameFreq.size) {
                val diff = Pair(
                    sameFreq[i].first - sameFreq[j].first,
                    sameFreq[i].second - sameFreq[j].second,
                )

                var aNode = Pair(sameFreq[i].first - diff.first, sameFreq[i].second - diff.second)
                while (aNode.first >= 0 && aNode.first < grid.size && aNode.second >= 0 && aNode.second < grid.first().size) {
                    antinodes += aNode
                    aNode = Pair(aNode.first - diff.first, aNode.second - diff.second)
                }
                aNode = Pair(sameFreq[j].first + diff.first, sameFreq[j].second + diff.second)
                while (aNode.first >= 0 && aNode.first < grid.size && aNode.second >= 0 && aNode.second < grid.first().size) {
                    antinodes += aNode
                    aNode = Pair(aNode.first + diff.first, aNode.second + diff.second)
                }
            }
        }
    }

    antinodes.size.println()
}