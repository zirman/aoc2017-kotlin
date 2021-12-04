import kotlin.collections.List

fun main() {
    fun part1(initial: String, input: List<String>): String {
        val commands = input.flatMap { list -> list.split(Regex(""",\s*""")) }
        val a = initial.toCharArray()
        var i = 0

        commands.forEach { command ->
            Regex("""s(\d+)""")
                .matchEntire(command)
                ?.let {
                    i = (i + a.size - it.groupValues[1].toInt()) % a.size
                }
                ?: Regex("""x(\d+)/(\d+)""").matchEntire(command)?.let {
                    val x = it.groupValues[1].toInt()
                    val y = it.groupValues[2].toInt()
                    val temp = a[(i + x) % a.size]
                    a[(i + x) % a.size] = a[(i + y) % a.size]
                    a[(i + y) % a.size] = temp
                }
                ?: Regex("""p([a-z])/([a-z])""").matchEntire(command)?.let {
                    val x = it.groupValues[1][0]
                    val y = it.groupValues[2][0]
                    val q = a.indexOf(x)
                    val r = a.indexOf(y)
                    a[r] = x
                    a[q] = y
                }
        }

        return a.indices.map { a[(i + it) % a.size] }.joinToString("")
    }

    fun part2(initial: String, input: List<String>, iterations: Int): String {
        val matches = input
            .flatMap { list -> list.split(Regex(""",\s*""")) }
            .map { string ->
                Regex("""(s)(\d+)""")
                    .matchEntire(string)
                    ?: Regex("""(x)(\d+)/(\d+)""").matchEntire(string)
                    ?: Regex("""(p)([a-z])/([a-z])""").matchEntire(string)
                    ?: throw Exception()
            }

        val a = initial.toCharArray()
        val set = mutableMapOf<String, CharArray>()

        repeat(iterations) {
            set.getOrPut(a.joinToString("")) {
                matches.forEach { match ->
                    when (match.groupValues[1]) {
                        "s" -> {
                            a.indices
                                .map { a[(a.size - match.groupValues[2].toInt() + it) % a.size] }
                                .forEachIndexed { index, c -> a[index] = c }
                        }
                        "x" -> {
                            val x = match.groupValues[2].toInt()
                            val y = match.groupValues[3].toInt()
                            val temp = a[x % a.size]
                            a[x % a.size] = a[y % a.size]
                            a[y % a.size] = temp
                        }
                        "p" -> {
                            val x = match.groupValues[2][0]
                            val y = match.groupValues[3][0]
                            val q = a.indexOf(y)
                            val r = a.indexOf(x)
                            a[q] = x
                            a[r] = y
                        }
                    }
                }

                a.copyOf()
            }.forEachIndexed { index, c -> a[index] = c }
        }

        return a.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1("abcde", testInput) == "baedc")
    check(part2("abcde", testInput, 1) == "baedc")

    val input = readInput("Day16")
    println(part1("abcdefghijklmnop", input))
    println(part2("abcdefghijklmnop", input, 1_000_000))
}
