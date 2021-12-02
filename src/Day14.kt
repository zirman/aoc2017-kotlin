import kotlin.collections.List

fun main() {
    fun part1(input: String): Int {
        fun knotHash(input: String): List<Int> {
            val size = 256
            val a = IntArray(size)

            for (i in a.indices) {
                a[i] = i
            }

            var index = 0
            var skip = 0

            val lengths =
                listOf(input.map { it.code }, listOf(17, 31, 73, 47, 23)).flatten()

            for (x in 1..64) {
                lengths.forEach { length ->
                    if (length <= size) {
                        for (y in 0 until length / 2) {
                            val temp = a[(index + y) % a.size]
                            a[(index + y) % a.size] = a[(index + (length - (y + 1))) % a.size]
                            a[(index + (length - (y + 1))) % a.size] = temp
                        }
                    }

                    index = (index + length + skip) % a.size
                    skip++
                }
            }

            return (0 until 16)
                .map { i -> a.slice(i * 16 until (i + 1) * 16).reduce { x, y -> x.xor(y) } }
        }

        return (0 until 128).asSequence()
            .map { "$input-$it" }
            .map { knotHash(it) }
            .fold(0) { a, b ->
                a + b.asSequence().map { string -> string.toString(2).map { it.toString().toInt() }.sum() }.sum()
            }
    }

    fun part2(input: String): Int {
        fun knotHash(input: String): List<Int> {
            val size = 256
            val a = IntArray(size)

            for (i in a.indices) {
                a[i] = i
            }

            var index = 0
            var skip = 0

            val lengths =
                listOf(input.map { it.code }, listOf(17, 31, 73, 47, 23)).flatten()

            for (x in 1..64) {
                lengths.forEach { length ->
                    if (length <= size) {
                        for (y in 0 until length / 2) {
                            val temp = a[(index + y) % a.size]
                            a[(index + y) % a.size] = a[(index + (length - (y + 1))) % a.size]
                            a[(index + (length - (y + 1))) % a.size] = temp
                        }
                    }

                    index = (index + length + skip) % a.size
                    skip++
                }
            }

            return (0 until 16)
                .map { i -> a.slice(i * 16 until (i + 1) * 16).reduce { x, y -> x.xor(y) } }
        }

        val a = List(128) {
            IntArray(128)
        }

        (0 until 128).asSequence()
            .map { "$input-$it" }
            .map { knotHash(it) }
            .forEachIndexed { row, list ->
                list.forEachIndexed { index1, bits ->
                    (0 until 8).forEach {
                        a[row][(index1 * 8) + (7 - it)] =
                            bits.shr(it).and(1)
                    }
                }
            }

        fun flood(row: Int, col: Int) {
            if (row < 0 || row > 127 || col < 0 || col > 127 || a[row][col] != 1) {
                return
            }

            a[row][col] = 0

            flood(row - 1, col)
            flood(row + 1, col)
            flood(row, col - 1)
            flood(row, col + 1)
        }

        var regions = 0

        for (row in 0 until 128) {
            for (col in 0 until 128) {
                if (a[row][col] == 1) {
                    flood(row, col)
                    regions++
                }
            }
        }

        return regions
    }

    // test if implementation meets criteria from the description, like:
    val testInput = "flqrgnkx"
    check(part1(testInput) == 8108)
    check(part2(testInput) == 1242)

    val input = "ffayrhll"
    println(part1(input))
    println(part2(input))
}
