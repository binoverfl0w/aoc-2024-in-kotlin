fun main() {
    val input = readInput("input9")[0]
    val sb = StringBuilder()
    input.forEachIndexed { i, c ->
        val charToAdd = if (i % 2 == 0) { (i / 2).toChar() } else { 0xFFFF.toChar() }
        for (x in 1..c.digitToInt()) {
            sb.append(charToAdd)
        }
    }

    var l = 0
    var r = sb.length - 1
    while (l < r) {
        while (l < sb.length && sb[l] != 0xFFFF.toChar()) {
            l++
            continue
        }

        while (r >= 0 && sb[r] == 0xFFFF.toChar()) {
            r--
            continue
        }

        if (l < sb.length && r >= 0) {
            val tmp = sb[l]
            sb[l] = sb[r]
            sb[r] = tmp

            l++
            r--
        }
    }

    val checkSum: ULong = sb
        .filter { it != 0xFFFF.toChar() }
        .mapIndexed { i, c -> i.toULong() * (c - 0.toChar()).toULong() }
        .reduce { a, b -> a + b }

    checkSum.println()
}

private fun main2() {
    val input = readInput("input9")[0]
    val sb = StringBuilder()
    input.forEachIndexed { i, c ->
        val charToAdd = if (i % 2 == 0) { (i / 2).toChar() } else { 0xFFFF.toChar() }
        for (x in 1..c.digitToInt()) {
            sb.append(charToAdd)
        }
    }

    loop@ for ((i, c) in sb.withIndex().reversed()) {
        while (c == 0xFFFF.toChar() || (i < sb.length - 1 && sb[i + 1] == c)) {
            continue@loop
        }

        var end = i
        while (end > 0 && sb[end - 1] == c) {
            end--
        }

        var start = 0
        var space = 0
        for ((j, c2) in sb.withIndex()) {
            if (j >= i) {
                continue@loop
            }

            if (c2 == 0xFFFF.toChar()) {
                space++
            } else {
                start = j
                space = 0
            }

            if (space == i - end + 1) {
                for (x in end..i) {
                    val tmp = sb[x]
                    sb[x] = sb[start + (x - end) + 1]
                    sb[start + (x - end) + 1] = tmp
                }
                continue@loop
            }
        }
    }

    val checkSum: ULong = sb
        .mapIndexed { i, c -> if (c == 0xFFFF.toChar()) { 0.toULong() } else { i.toULong() * (c - 0.toChar()).toULong()} }
        .reduce { a, b -> a + b }

    checkSum.println()
}