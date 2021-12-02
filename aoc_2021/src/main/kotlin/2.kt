import java.io.File

private class Submarine(
    var horizontalPosition: Int = 0,
    var depthPosition: Int = 0,
    var aimPosition: Int = 0,
)

private class Operation(input: String) {
    private val axis: Axis
    private val value: Int

    init {
        val (operation, value) = input.split(" ")
        val (axis, multiplier) = Axis.findWithMultiplier(operation)
        this.axis = axis
        this.value = value.toInt() * multiplier
    }

    fun calculateA(submarine: Submarine) {
        axis.calculateA(submarine, value)
    }

    fun calculateB(submarine: Submarine) {
        axis.calculateB(submarine, value)
    }

    private enum class Axis(
        val positiveOperation: String?,
        val negativeOperation: String?,
    ) {
        Horizontal(
            positiveOperation = "forward",
            negativeOperation = null,
        ) {
            override fun calculateA(submarine: Submarine, value: Int) {
                submarine.horizontalPosition += value
            }

            override fun calculateB(submarine: Submarine, value: Int) {
                submarine.horizontalPosition += value
                submarine.depthPosition += submarine.aimPosition * value
            }
        },
        Depth(
            positiveOperation = "down",
            negativeOperation = "up",
        ) {
            override fun calculateA(submarine: Submarine, value: Int) {
                submarine.depthPosition += value
            }

            override fun calculateB(submarine: Submarine, value: Int) {
                submarine.aimPosition += value
            }
        };

        abstract fun calculateA(submarine: Submarine, value: Int)
        abstract fun calculateB(submarine: Submarine, value: Int)

        companion object {
            fun findWithMultiplier(operation: String): Pair<Axis, Int> =
                values().find { it.positiveOperation == operation }?.let { it to 1 }
                    ?: values().find { it.negativeOperation == operation }?.let { it to -1 }
                    ?: throw IllegalArgumentException("Unknown operation")
        }
    }
}

fun day2a() {
    val inputs = File("data/2.txt").readLines()

    val submarine = Submarine()

    inputs.map(::Operation)
        .forEach {
            it.calculateA(submarine)
        }

    println("Day 2a - positions multiplied = ${submarine.horizontalPosition * submarine.depthPosition}")
}

fun day2b() {
    val inputs = File("data/2.txt").readLines()

    val submarine = Submarine()

    inputs.map(::Operation)
        .forEach {
            it.calculateB(submarine)
        }

    println("Day 2b - positions multiplied = ${submarine.horizontalPosition * submarine.depthPosition}")
}
