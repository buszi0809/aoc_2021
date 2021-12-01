import java.io.File

fun day1a() {
    val measurements = File("data/1.txt").readLines().map(String::toInt)

    val largerMeasurementsCount = measurements
        .zipWithNext()
        .fold(0) { acc, (first, second) ->
            if (second > first) acc + 1 else acc
        }

    println("Day 1a - larger measurements count = $largerMeasurementsCount")
}

fun day1b() {
    val measurements = File("data/1.txt").readLines().map(String::toInt)

    val largerWindowedMeasurementsCount = measurements
        .windowed(3)
        .map { it.sum() }
        .zipWithNext()
        .fold(0) { acc, (first, second) ->
            if (second > first) acc + 1 else acc
        }

    println("Day 1b - larger windowed measurements count = $largerWindowedMeasurementsCount")
}
