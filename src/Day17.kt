import kotlin.math.min

fun main() {
    val lines = readInput("input17")
    val regA = lines[0].split(": ")[1].toULong()
    val regB = lines[1].split(": ")[1].toULong()
    val regC = lines[2].split(": ")[1].toULong()
    assert(lines[3].isBlank())

    val program = lines[4]
        .split(": ")[1]
        .split(",")
        .map { it.toInt() }

    val output = runProgram(program, regA, regB, regC)

    val outS = output.joinToString(",")

    println("Output: $outS")
}

private fun main2() {
    /*
    // The instructions translated to Kotlin
    fun doProgram(regA: ULong, regB: ULong = 0UL, regC: ULong = 0UL): List<Int> {
        var A = regA
        var B = regB
        var C = regC
        val out = mutableListOf<Int>()


        while (A != 0UL) {
            B = A % 8UL
            B = B xor 0b001UL
            C = A shr B.toInt()
            B = B xor 0b101UL
            B = B xor C
            out += (B % 8UL).toInt()
            A = A shr 3
        }

        return out
    }
     */

    val waysToGetN = generate()

    val out = listOf(2,4,1,1,7,5,1,5,4,0,5,5,0,3,3,0)

    val all = mutableSetOf<ULong>()

    waysToGetN[out[0]]!!
        .forEach loop0@ { a0 ->
            waysToGetN[out[1]]!!
                .forEach loop1@ { a1 ->
                    waysToGetN[out[2]]!!
                        .forEach loop2@ { a2 ->
                            waysToGetN[out[3]]!!
                                .forEach loop3@ { a3 ->
                                    waysToGetN[out[4]]!!
                                        .forEach loop4@ { a4 ->
                                            waysToGetN[out[5]]!!
                                                .forEach loop5@ { a5 ->
                                                    waysToGetN[out[6]]!!
                                                        .forEach loop6@ { a6 ->
                                                            waysToGetN[out[7]]!!
                                                                .forEach loop7@ { a7 ->
                                                                    waysToGetN[out[8]]!!
                                                                        .forEach loop8@ { a8 ->
                                                                            waysToGetN[out[9]]!!
                                                                                .forEach loop9@ { a9 ->
                                                                                    waysToGetN[out[10]]!!
                                                                                        .forEach loop10@ { a10 ->
                                                                                            waysToGetN[out[11]]!!
                                                                                                .forEach loop11@ { a11 ->
                                                                                                    waysToGetN[out[12]]!!
                                                                                                        .forEach loop12@ { a12 ->
                                                                                                            waysToGetN[out[13]]!!
                                                                                                                .forEach loop13@ { a13 ->
                                                                                                                    waysToGetN[out[14]]!!
                                                                                                                        .forEach loop14@ { a14 ->
                                                                                                                            waysToGetN[out[15]]!!
                                                                                                                                .forEach loop15@ { a15 ->

                                                                                                                                    var regA: ULong = a0.toULong()
                                                                                                                                    regA = regA or (a1.toULong() shl 3)
                                                                                                                                    regA = regA or (a2.toULong() shl 6)
                                                                                                                                    regA = regA or (a3.toULong() shl 9)
                                                                                                                                    regA = regA or (a4.toULong() shl 12)
                                                                                                                                    regA = regA or (a5.toULong() shl 15)
                                                                                                                                    regA = regA or (a6.toULong() shl 18)
                                                                                                                                    regA = regA or (a7.toULong() shl 21)
                                                                                                                                    regA = regA or (a8.toULong() shl 24)
                                                                                                                                    regA = regA or (a9.toULong() shl 27)
                                                                                                                                    regA = regA or (a10.toULong() shl 30)
                                                                                                                                    regA = regA or (a11.toULong() shl 33)
                                                                                                                                    regA = regA or (a12.toULong() shl 36)
                                                                                                                                    regA = regA or (a13.toULong() shl 39)
                                                                                                                                    regA = regA or (a14.toULong() shl 42)
                                                                                                                                    regA = regA or (a15.toULong() shl 45)

                                                                                                                                    val result = runProgram(out, regA)

                                                                                                                                    if (result[0] != 2) {
                                                                                                                                        return@loop2
                                                                                                                                    }
                                                                                                                                    if (result[1] != 4) {
                                                                                                                                        return@loop3
                                                                                                                                    }
                                                                                                                                    if (result[2] != 1) {
                                                                                                                                        return@loop4
                                                                                                                                    }
                                                                                                                                    if (result[3] != 1) {
                                                                                                                                        return@loop5
                                                                                                                                    }
                                                                                                                                    if (result[4] != 7) {
                                                                                                                                        return@loop6
                                                                                                                                    }
                                                                                                                                    if (result[5] != 5) {
                                                                                                                                        return@loop7
                                                                                                                                    }
                                                                                                                                    if (result[6] != 1) {
                                                                                                                                        return@loop8
                                                                                                                                    }
                                                                                                                                    if (result[7] != 5) {
                                                                                                                                        return@loop9
                                                                                                                                    }
                                                                                                                                    if (result[8] != 4) {
                                                                                                                                        return@loop10
                                                                                                                                    }
                                                                                                                                    if (result[9] != 0) {
                                                                                                                                        return@loop11
                                                                                                                                    }
                                                                                                                                    if (result[10] != 5) {
                                                                                                                                        return@loop12
                                                                                                                                    }
                                                                                                                                    if (result[11] != 5) {
                                                                                                                                        return@loop13
                                                                                                                                    }
                                                                                                                                    if (result[12] != 0) {
                                                                                                                                        return@loop14
                                                                                                                                    }

                                                                                                                                    if (out == result && regA !in all) {
                                                                                                                                        all += regA
                                                                                                                                        "MIN: ${all.min()}".println()
                                                                                                                                    }
                                                                                                                                }
                                                                                                                        }
                                                                                                                }
                                                                                                        }
                                                                                                }
                                                                                        }
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }

    all.println()
}

fun runProgram(program: List<Int>, regA: ULong = 0UL, regB: ULong = 0UL, regC: ULong = 0UL): List<Int> {
    val computer = Computer(
        regA = regA,
        regB = regB,
        regC = regC,
    )

    while (computer.ip < program.size) {
        val opcode = program[computer.ip]
        val operand = program[computer.ip + 1]

        val ins = findIns(opcode)
        ins(computer, operand)

        if (ins != ::jnz || computer.regA == 0UL) {
            computer.ip += 2
        }
    }

    return computer.outBuffer
}

data class Computer(
    var ip: Int = 0,
    var regA: ULong = 0UL,
    var regB: ULong = 0UL,
    var regC: ULong = 0UL,

    var outBuffer: List<Int> = mutableListOf()
)

fun findIns(opcode: Int): (computer: Computer, operand: Int) -> Unit {
    return when (opcode) {
        0 -> ::adv
        1 -> ::bxl
        2 -> ::bst
        3 -> ::jnz
        4 -> ::bxc
        5 -> ::out
        6 -> ::bdv
        7 -> ::cdv
        else -> error("Unknown opcode")
    }
}

fun adv(computer: Computer, operand: Int) {
    computer.regA = computer.regA shr min(Int.MAX_VALUE.toULong(), comboOperand(computer, operand)).toInt()
}

fun bxl(computer: Computer, operand: Int) {
    computer.regB = computer.regB xor literalOperand(operand).toULong()
}

fun bst(computer: Computer, operand: Int) {
    computer.regB = comboOperand(computer, operand) % 8UL
}

fun jnz(computer: Computer, operand: Int) {
    if (computer.regA != 0UL) {
        computer.ip = literalOperand(operand)
    }
}

fun bxc(computer: Computer, operand: Int) {
    computer.regB = computer.regB xor computer.regC
}

fun out(computer: Computer, operand: Int) {
    computer.outBuffer += (comboOperand(computer, operand) % 8UL).toInt()
}

fun bdv(computer: Computer, operand: Int) {
    computer.regB = computer.regA shr min(Int.MAX_VALUE.toULong(), comboOperand(computer, operand)).toInt()
}

fun cdv(computer: Computer, operand: Int) {
    computer.regC = computer.regA shr min(Int.MAX_VALUE.toULong(), comboOperand(computer, operand)).toInt()
}

fun literalOperand(value: Int): Int = value
fun comboOperand(computer: Computer, value: Int): ULong =
    when(value) {
        0,1,2,3 -> value.toULong()
        4 -> computer.regA
        5 -> computer.regB
        6 -> computer.regC
        else -> error("Invalid program")
    }


val fMap = mutableMapOf(
    0b000 to 0b001,
    0b001 to 0b000,
    0b010 to 0b011,
    0b011 to 0b010,
    0b100 to 0b101,
    0b101 to 0b100,
    0b110 to 0b111,
    0b111 to 0b110,
)

val sMap = mutableMapOf(
    0b000 to 0b100,
    0b001 to 0b101,
    0b010 to 0b110,
    0b011 to 0b111,
    0b100 to 0b000,
    0b101 to 0b001,
    0b110 to 0b010,
    0b111 to 0b011,
)

fun generate(): Map<Int, Set<Int>> {
    val waysToGetN = mutableMapOf<Int, Set<Int>>()

    for (n in 0..7) {
        val wayToGetN = mutableSetOf<Int>()
        fMap
            .keys
            .forEach {
                var seq = it
                seq = seq shr fMap[it]!!
                seq = seq or (sMap[it]!! xor n)
                seq = seq shl fMap[it]!!
                seq = seq or it
                if (seq % 8 == it) {
                    wayToGetN += seq
                    if (fMap[it]!! > 3) {
                        val upper = 1 shl (fMap[it]!! - 1)
                        for (b in 8..upper step 8) {
                            wayToGetN += seq or b
                        }
                    }
                }
            }
        waysToGetN[n] = wayToGetN
    }

    return waysToGetN
}
