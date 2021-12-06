import kotlin.collections.List

sealed class NodeState {
    object Weakened : NodeState()
    object Infected : NodeState()
    object Flagged : NodeState()
}

fun main() {
    fun part1(input: List<String>, iterations: Int): Int {
        val infections: MutableSet<Pair<Int, Int>> =
            mutableSetOf()

        input.forEachIndexed { row, rows ->
            rows.forEachIndexed { col, c ->
                if (c == '#') {
                    infections.add(Pair(row, col))
                }
            }
        }

        var y = input.size / 2
        var x = input[0].length / 2
        var dir: Dir = Dir.Up
        var infected = 0

        repeat(iterations) {
            dir =
                if (infections.contains(Pair(y, x))) {
                    when (dir) {
                        Dir.Up ->
                            Dir.Right
                        Dir.Down ->
                            Dir.Left
                        Dir.Left ->
                            Dir.Up
                        Dir.Right ->
                            Dir.Down
                    }
                } else {
                    when (dir) {
                        Dir.Up ->
                            Dir.Left
                        Dir.Down ->
                            Dir.Right
                        Dir.Left ->
                            Dir.Down
                        Dir.Right ->
                            Dir.Up
                    }
                }

            if (infections.contains(Pair(y, x))) {
                infections.remove(Pair(y, x))
            } else {
                infections.add(Pair(y, x))
                infected++
            }

            when (dir) {
                Dir.Up ->
                    y--
                Dir.Down ->
                    y++
                Dir.Left ->
                    x--
                Dir.Right ->
                    x++
            }
        }

        return infected
    }

    fun part2(input: List<String>, iterations: Int): Int {
        val infections: MutableMap<Pair<Int, Int>, NodeState> =
            mutableMapOf()

        input.forEachIndexed { row, rows ->
            rows.forEachIndexed { col, c ->
                if (c == '#') {
                    infections[Pair(row, col)] = NodeState.Infected
                }
            }
        }

        var y = input.size / 2
        var x = input[0].length / 2
        var dir: Dir = Dir.Up
        var infected = 0

        fun turnLeft() {
            dir = when (dir) {
                Dir.Up ->
                    Dir.Left
                Dir.Down ->
                    Dir.Right
                Dir.Left ->
                    Dir.Down
                Dir.Right ->
                    Dir.Up
            }
        }

        fun turnRight() {
            dir = when (dir) {
                Dir.Up ->
                    Dir.Right
                Dir.Down ->
                    Dir.Left
                Dir.Left ->
                    Dir.Up
                Dir.Right ->
                    Dir.Down
            }
        }

        fun reverseDirection() {
            dir = when (dir) {
                Dir.Up ->
                    Dir.Down
                Dir.Down ->
                    Dir.Up
                Dir.Left ->
                    Dir.Right
                Dir.Right ->
                    Dir.Left
            }
        }

        repeat(iterations) {
            when (infections[Pair(y, x)]) {
                NodeState.Weakened -> {
                }
                NodeState.Infected -> {
                    turnRight()
                }
                NodeState.Flagged -> {
                    reverseDirection()
                }
                null -> { // clean
                    turnLeft()
                }
            }

            when (infections[Pair(y, x)]) {
                NodeState.Flagged -> {
                    infections.remove(Pair(y, x))
                }
                NodeState.Infected -> {
                    infections[Pair(y, x)] = NodeState.Flagged
                }
                NodeState.Weakened -> {
                    infected++
                    infections[Pair(y, x)] = NodeState.Infected
                }
                null -> {
                    infections[Pair(y, x)] = NodeState.Weakened
                }
            }

            when (dir) {
                Dir.Up ->
                    y--
                Dir.Down ->
                    y++
                Dir.Left ->
                    x--
                Dir.Right ->
                    x++
            }
        }

        return infected
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day22_test")
    check(part1(testInput, 10_000) == 5587)
    check(part2(testInput, 10_000_000) == 2511944)

    val input = readInput("Day22")
    println(part1(input, 10_000))
    println(part2(input, 10_000_000))
}
