import kotlin.collections.List

sealed class Dir {
    object Down : Dir()
    object Up : Dir()
    object Left : Dir()
    object Right : Dir()
}

fun main() {
    fun part1(input: List<String>): String {
        val a = input.map { string -> string.map { it } }

        var x = a[0].indexOf('|')
        var y = 0

        val nodes = mutableListOf<Char>()
        var dir: Dir = Dir.Down

        fun get(x: Int, y: Int): Char =
            a.getOrNull(y)?.getOrNull(x) ?: ' '

        while (true) {
            when (dir) {
                is Dir.Down -> {
                    y++
                }
                is Dir.Up -> {
                    y--
                }
                is Dir.Left -> {
                    x--
                }
                is Dir.Right -> {
                    x++
                }
            }

            val c = get(x, y)

            when {
                c == ' ' -> {
                    break
                }
                c.isLetter() -> {
                    nodes.add(c)
                }
                c == '+' -> {
                    dir = when (dir) {
                        Dir.Down, Dir.Up -> {
                            if (get(x - 1, y) == '-') {
                                Dir.Left
                            } else if (get(x + 1, y) == '-') {
                                Dir.Right
                            } else if (get(x - 1, y).isLetter()) {
                                Dir.Left
                            } else if (get(x + 1, y).isLetter()) {
                                Dir.Right
                            } else {
                                throw Exception("$x $y")
                            }
                        }
                        Dir.Right, Dir.Left -> {
                            if (get(x, y - 1) == '|') {
                                Dir.Up
                            } else if (get(x, y + 1) == '|') {
                                Dir.Down
                            } else if (get(x, y - 1).isLetter()) {
                                Dir.Up
                            } else if (get(x, y + 1).isLetter()) {
                                Dir.Down
                            } else {
                                throw Exception()
                            }
                        }
                    }
                }
                c == '|' || c == '-' -> {
                }
                else -> {
                    throw Exception()
                }
            }
        }

        return nodes.joinToString("")
    }

    fun part2(input: List<String>): Long {
        val a = input.map { string -> string.map { it } }

        var x = a[0].indexOf('|')
        var y = 0

        val nodes = mutableListOf<Char>()
        var dir: Dir = Dir.Down

        fun get(x: Int, y: Int): Char =
            a.getOrNull(y)?.getOrNull(x) ?: ' '

        var steps = 0L

        while (true) {
            steps++
            when (dir) {
                is Dir.Down -> {
                    y++
                }
                is Dir.Up -> {
                    y--
                }
                is Dir.Left -> {
                    x--
                }
                is Dir.Right -> {
                    x++
                }
            }

            val c = get(x, y)

            when {
                c == ' ' -> {
                    break
                }
                c.isLetter() -> {
                    nodes.add(c)
                }
                c == '+' -> {
                    dir = when (dir) {
                        Dir.Down, Dir.Up -> {
                            if (get(x - 1, y) == '-') {
                                Dir.Left
                            } else if (get(x + 1, y) == '-') {
                                Dir.Right
                            } else if (get(x - 1, y).isLetter()) {
                                Dir.Left
                            } else if (get(x + 1, y).isLetter()) {
                                Dir.Right
                            } else {
                                throw Exception("$x $y")
                            }
                        }
                        Dir.Right, Dir.Left -> {
                            if (get(x, y - 1) == '|') {
                                Dir.Up
                            } else if (get(x, y + 1) == '|') {
                                Dir.Down
                            } else if (get(x, y - 1).isLetter()) {
                                Dir.Up
                            } else if (get(x, y + 1).isLetter()) {
                                Dir.Down
                            } else {
                                throw Exception()
                            }
                        }
                    }
                }
                c == '|' || c == '-' -> {
                }
                else -> {
                    throw Exception()
                }
            }
        }

        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")
    check(part1(testInput) == "ABCDEF")
    check(part2(testInput) == 38L)

    val input = readInput("Day19")
    println(part1(input))
    println(part2(input))
}
