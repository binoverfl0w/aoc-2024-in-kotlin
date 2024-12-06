import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern
import kotlin.io.path.Path
import kotlin.io.path.readText

val whiteSpacePattern: Pattern = Pattern.compile("\\s+")

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/input/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.toIntArray(delimiter: Pattern): IntArray {
    return this.split(delimiter)
        .stream()
        .mapToInt { it.toInt() }
        .toArray()
}