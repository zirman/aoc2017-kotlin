import kotlin.collections.List

fun main() {
    fun part1(input: List<String>, iterations: Int): Int {
        fun rotate(list: List<List<Char>>): List<List<Char>> {
            return list.indices.reversed().map { col ->
                list.indices.map { row ->
                    list[row][col]
                }
            }
        }

        fun flipVertical(list: List<List<Char>>): List<List<Char>> {
            return list.indices.reversed().map { row ->
                list[row]
            }
        }

        fun flipHorizontal(list: List<List<Char>>): List<List<Char>> {
            return list.indices.map { row ->
                list.indices.reversed().map { col ->
                    list[row][col]
                }
            }
        }

        val rules = input
            .flatMap { line ->
                line.split(" => ")
                    .let { rule ->
                        val from = rule[0].split("/").map { string -> string.map { it } }
                        val fh = flipHorizontal(from)
                        val fv = flipVertical(from)
                        val r1 = rotate(from)
                        val r2 = rotate(r1)
                        val r3 = rotate(r2)
                        val r4 = rotate(fh)
                        val r5 = rotate(r4)
                        val r6 = rotate(r5)
                        val r7 = rotate(fv)
                        val r8 = rotate(r7)
                        val r9 = rotate(r8)

                        setOf(from, fh, fv, r1, r2, r3, r4, r5, r6, r7, r8, r9).map { a ->
                            Pair(a, rule[1].split("/").map { string -> string.map { it } })
                        }
                    }
            }
            .toMap()

        fun List<List<Char>>.view(col: Int, row: Int, size: Int): List<List<Char>> =
            (row until row + size).map { this[it].subList(col, col + size) }

        return (1..iterations).fold(
            ".#.\n..#\n###".split('\n').map { string -> string.map { it } }
        ) { g, _ ->
            (if (g[0].size % 2 == 0) {
                (0 until g[0].size step 2).flatMap { row ->
                    val gri = (0 until g[0].size step 2)
                        .map { col -> rules[g.view(col, row, 2)]!! }

                    gri[0].indices.map { y -> gri.map { it[y] }.flatten() }
                }
            } else if (g[0].size % 3 == 0) {
                (0 until g[0].size step 3).flatMap { row ->
                    val gri = (0 until g[0].size step 3)
                        .map { col -> rules[g.view(col, row, 3)]!! }

                    gri[0].indices.map { y -> gri.map { it[y] }.flatten() }
                }
            } else {
                throw Exception()
            })
        }.sumOf { row -> row.count { it == '#' } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21_test")
    check(part1(testInput, 2) == 12)

    val input = readInput("Day21")
    println(part1(input, 5))
    println(part1(input, 18))
}
