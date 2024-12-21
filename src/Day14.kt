import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JTextArea
import kotlin.math.max

fun main() {
    val m = 103
    val n = 101

    val constraint = SpaceConstraint(0, 0, n - 1, m - 1)

    val robots = readInput("input14")
        .map { toRobotWithConstraint(it, constraint) }

    val bathroom = Bathroom(
        m = m,
        n = n,
        robots = robots
    )

    bathroom.computeStateAfterNSeconds(100)
    bathroom.safetyFactor()
        .println()

}

private fun main2() {
    val m = 103
    val n = 101

    val constraint = SpaceConstraint(0, 0, n - 1, m - 1)

    val robots = readInput("input14")
        .map { toRobotWithConstraint(it, constraint) }

    val bathroom = Bathroom(
        m = m,
        n = n,
        robots = robots
    )

    var i = 0
    while (!bathroom.isChristmasTreeFormation()) {
        bathroom.computeNextState()
        i++
    }
    i.println()
}

fun toRobotWithConstraint(s: String, constraint: SpaceConstraint): Robot {
    val tmp = s.split(" v=")
    val p = tmp[0].split("p=")[1].trim().extractIntArray(",")
    val v = tmp[1].extractIntArray(",")
    return Robot(
        pos = Vector2D(p[0], p[1]),
        vel = Vector2D(v[0], v[1]),
        constraint = constraint
    )
}

data class Vector2D(var x: Int, var y: Int)
data class SpaceConstraint(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

data class Robot(
    val pos: Vector2D,
    val vel: Vector2D,
    val constraint: SpaceConstraint,
) {
    fun advance() {
        pos.x += vel.x
        pos.y += vel.y
        if (pos.x < constraint.x1 || pos.x > constraint.x2) {
            pos.x = teleport(pos.x, constraint.x1, constraint.x2)
        }
        if (pos.y < constraint.y1 || pos.y > constraint.y2) {
            pos.y = teleport(pos.y, constraint.y1, constraint.y2)
        }
    }

    private fun teleport(n: Int, min: Int, max: Int): Int {
        var pos = n
        if (pos < min) {
            pos += max + 1
        }
        return pos % (max - min + 1) + min
    }
}

data class Cell(
    val robots: MutableList<Robot> = mutableListOf(),
)
data class Bathroom(
    val m: Int,
    val n: Int,
    val robots: List<Robot>,
) {
    private val grid = MutableList(m) { MutableList(n) { Cell() } }

    /*
    private val frame = JFrame("Where is my christmas tree?!")
    private val iterationTextArea = JTextArea()
    private val gridPanel = GridPanel(grid)
    */

    init {
        for (robot in robots) {
            grid[robot.pos.y][robot.pos.x].robots += robot
        }

        /*
        iterationTextArea.font = Font("Arial", Font.PLAIN, 20)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(1000, 1500)
        frame.layout = BorderLayout()
        frame.add(gridPanel)
        frame.add(iterationTextArea, BorderLayout.SOUTH)
        frame.isVisible = true
         */
    }

    fun computeNextState() {
        for (robot in robots) {
            grid[robot.pos.y][robot.pos.x].robots -= robot
            robot.advance()
            grid[robot.pos.y][robot.pos.x].robots += robot
        }
    }


    fun computeStateAfterNSeconds(n: Int) {
        for (i in 1..n) {
            computeNextState()

            // I had no idea what to expect, so I literally decided to print each image
            // due to I/O operations, you can see each image for a few ms, I stopped the program once I saw the tree
            /*
            iterationTextArea.text = i.toString()
            gridPanel.repaint()

            val outputFile = File("src/res/$i.jpg")
            if (!outputFile.exists()) {
                ImageIO.write(createImage(frame), "jpg", outputFile)
            }
             */
        }
    }

    fun safetyFactor(): Int {
        return arrayOf(1, 2, 3, 4)
            .foldRight(1) { n, acc ->
                acc * grid
                    .quadrant(n)
                    .foldRight(0) { c, sum -> sum + c.robots.size }
            }
    }

    // This was added after actually seeing the tree
    fun isChristmasTreeFormation(): Boolean {
        for ((i, row) in grid.withIndex()) {
            jLoop@ for ((j, cell) in row.withIndex()) {
                val sides = arrayOf(0, 0, 0, 0)

                var sqStartJ = j
                while (sqStartJ < row.size && row[sqStartJ].robots.isNotEmpty()) {
                    sqStartJ++
                }
                sqStartJ -= 1
                sides[0] = sqStartJ - j + 1
                // This is an arbitrary number as the idea here is that
                // if a big enough rectangle is present, it is assumed to be a Christmas tree
                if (sides[0] < 30) { continue }

                var sqStartI = i
                while (sqStartI < grid.size - 1 && grid[sqStartI][j].robots.isNotEmpty()) {
                    sqStartI++
                }
                sqStartI -= 1
                sides[1] = sqStartI - i + 1
                if (sides[1] == 0) { continue }

                val oppositeCell = grid[sqStartI][sqStartJ]
                if (oppositeCell.robots.isEmpty()) {
                    continue
                }

                for (tmpJ in j..sqStartJ) {
                    if (grid[sqStartI][tmpJ].robots.isEmpty()) {
                        continue@jLoop
                    }
                }

                for (tmpI in i..sqStartI) {
                    if (grid[tmpI][sqStartJ].robots.isEmpty()) {
                        continue@jLoop
                    }
                }

                return true
            }
        }
        return false
    }
}

fun String.extractIntArray(separator: String): List<Int> {
    return this.trim().split(separator).map { it.toInt() }
}

fun List<List<Cell>>.quadrant(n: Int): List<Cell> {
    require(n in 1..4) { "Invalid quadrant" }

    val xMiddle = this.first().size / 2
    val yMiddle = this.size / 2

    val xRange = if (n == 1 || n == 4) xMiddle + 1..<this.first().size else 0..<xMiddle
    val yRange = if (n == 3 || n == 4) yMiddle + 1..<this.size else 0..<yMiddle

    val quadrantCells = mutableListOf<Cell>()
    for (x in xRange) {
        for (y in yRange) {
            quadrantCells += this[y][x]
        }
    }
    return quadrantCells
}

fun createImage(frame: JFrame): BufferedImage {
    val w = frame.width
    val h = frame.height
    val bi = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
    val g = bi.createGraphics()
    frame.paint(g)
    g.dispose()
    return bi
}

class GridPanel(private val grid: List<List<Cell>>) : JPanel() {
    private val boxSize = 10

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                val opacity = grid[i][j].robots.size
                val color = Color(0, 0, 0, opacity * 50)
                g.color = color

                g.fillRect(j * boxSize, i * boxSize, boxSize, boxSize)
            }
        }
    }
}

