import java.util.regex.Pattern

fun main() {
    var total = 0L

    val mulRegex = Pattern.compile("mul\\((\\d+),(\\d+)\\)").toRegex()
    readInput("input3").forEach {
        mulRegex.findAll(it).forEach {
            total += it.groupValues[1].toInt() * it.groupValues[2].toInt()
        }
    }

    total.println()
}

private fun main2() {
    abstract class Command

    class None: Command()
    class Do(val enable: Boolean): Command()
    class Mul(val prod: Int): Command()

    fun doCommand(s: String, i: Int): Command {
        if (s.regionMatches(i, "do()", 0, 4)) {
            return Do(true)
        } else if (s.regionMatches(i, "don't()", 0, 7)) {
            return Do(false)
        }
        return None()
    }

    fun mulCommand(s: String, i: Int): Command {
        var index = i
        if (!s.regionMatches(i, "mul(", 0, 4)) {
            return None()
        }
        index += 4
        var num1 = ""
        while (s.elementAt(index).isDigit()) {
            num1 += s.elementAt(index++)
        }
        if (num1.isEmpty() || s.elementAt(index) != ',') {
            return None()
        }
        index++
        var num2 = ""
        while (s.elementAt(index).isDigit()) {
            num2 += s.elementAt(index++)
        }
        if (num2.isEmpty() || s.elementAt(index) != ')') {
            return None()
        }
        return Mul(num1.toInt() * num2.toInt())
    }


    fun toCommands(s: String): List<Command> {
        val commands = mutableListOf<Command>()
        for (i in s.indices) {
            val command  = when (s.elementAt(i)) {
                'd' -> doCommand(s, i)
                'm' -> mulCommand(s, i)
                else -> None()
            }

            if (command !is None)
                commands += command
        }
        return commands
    }

    var total = 0L
    var add = true
    readInput("input3").forEach {
        toCommands(it).forEach {
            when (it) {
                is Mul -> if (add) { total += it.prod }
                is Do -> add = it.enable
            }
        }
    }

    total.println()
}