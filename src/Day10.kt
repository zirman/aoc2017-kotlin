import kotlin.collections.List

fun main() {
    fun part1(input: List<String>, size: Int): Int {
        val a = IntArray(size)

        for (i in a.indices) {
            a[i] = i
        }

        var index = 0
        var skip = 0

        input
            .flatMap { it.split(Regex(""",\s*""")) }
            .map { it.toInt() }
            .forEach { length ->
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

        return a[0] * a[1]
    }

    fun part2(input: List<String>, size: Int): String {
        val a = IntArray(size)

        for (i in a.indices) {
            a[i] = i
        }

        var index = 0
        var skip = 0

        val lengths =
            listOf(input.flatMap { string -> string.map { it.code } }, listOf(17, 31, 73, 47, 23)).flatten()

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
            .joinToString("") { "%02x".format(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput, 5) == 12)
    check(part2(listOf(""), 256) == "a2582a3a0e66e6e86e3812dcb672a272")
    check(part2(listOf("AoC 2017"), 256) == "33efeb34ea91902bb2f59c9920caa6cd")
    check(part2(listOf("1,2,3"), 256) == "3efbe78a8d82f29979031a4aa0b16a9d")
    check(part2(listOf("1,2,4"), 256) == "63960835bcdc130f0b66d7ff4f6a5a8e")

    val input = readInput("Day10")
    println(part1(input, 256))
    println(part2(input, 256))
}
