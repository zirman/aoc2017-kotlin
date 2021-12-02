fun main() {
    fun part1(inputA: Long, inputB: Long): Int {
        fun generatorA(x: Long) =
            (x * 16807L).rem(2147483647L)

        fun generatorB(x: Long) =
            (x * 48271L).rem(2147483647L)

        var a = inputA
        var b = inputB
        var count = 0

        for (unused in 1..40_000_000) {
            a = generatorA(a)
            b = generatorB(b)

            if (a.and(0xFFFF) == b.and(0xFFFF)) {
                count++
            }
        }

        return count
    }

    fun part2(inputA: Long, inputB: Long): Int {
        fun generatorA(x: Long): Long {
            var y: Long = x
            do {
                y = (y * 16807L).rem(2147483647L)
            } while (y % 4L != 0L)
            return y
        }

        fun generatorB(x: Long): Long {
            var y: Long = x
            do {
                y = (y * 48271L).rem(2147483647L)
            } while (y % 8L != 0L)
            return y
        }

        var a = inputA
        var b = inputB
        var count = 0

        for (unused in 1..5_000_000) {
            a = generatorA(a)
            b = generatorB(b)

            if (a.and(0xFFFF) == b.and(0xFFFF)) {
                count++
            }
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    check(part1(65, 8921) == 588)
    check(part2(65, 8921) == 309)

    println(part1(618, 814))
    println(part2(618, 814))
}
