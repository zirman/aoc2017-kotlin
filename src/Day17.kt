fun main() {
    fun part1(input: Int): Int {
        val a = mutableListOf(0)
        var index = 0

        repeat(2017) {
            index = ((index + input) % a.size) + 1
            a.add(index, it + 1)
        }

        return a[(index + 1) % a.size]
    }

    fun part2(input: Int): Int {
        var index = 0
        var last = 0

        repeat(50_000_000) { length ->
            index = ((index + input) % (length + 1)) + 1

            if (index == 1) {
                last = length + 1
            }
        }

        return last
    }

    // test if implementation meets criteria from the description, like:
    check(part1(3) == 638)

    val input = 345
    println(part1(input))
    println(part2(input))
}
