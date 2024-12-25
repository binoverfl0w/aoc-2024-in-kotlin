fun main() {
    val lines = readInput("input19")

    val patterns = lines[0].split(", ")

    var total = 0UL

    for (i in 2..<lines.size) {
        val design = lines[i]
        if (isPossible(design, patterns)) {
            total++
        }
    }

    println("Possible designs: $total")
}

private fun main2() {
    val lines = readInput("input19")

    val patterns = lines[0].split(", ")

    var total = 0UL

    for (i in 2..<lines.size) {
        val design = lines[i]
        total += possibleDesigns(design, patterns)
    }

    println("All possible designs: $total")
}

fun isPossible(design: String, patterns: List<String>): Boolean {
    if (design.isEmpty()) {
        return true
    }

    var possible = false
    for (pattern in patterns) {
        if (design.startsWith(pattern)) {
            possible = possible || isPossible(design.substring(pattern.length), patterns)
        }
    }
    return possible
}

val occurrences = mutableMapOf<String, ULong>()
fun possibleDesigns(design: String, patterns: List<String>): ULong {
    if (design in occurrences) {
        return occurrences[design]!!
    }
    if (design.isEmpty()) {
        return 1UL
    }

    var possible = 0UL
    for (pattern in patterns) {
        if (design.startsWith(pattern)) {
            possible += possibleDesigns(design.substring(pattern.length), patterns)
        }
    }
    occurrences[design] = possible
    return possible
}