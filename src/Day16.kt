import kotlin.collections.List

sealed class Foo {
    data class S(val i: Int) : Foo()
    data class X(val x: Int, val y: Int) : Foo()
    data class P(val x: Char, val y: Char) : Foo()
}

fun main() {
    fun part1(initial: String, input: List<String>): String {
        val commands = input.flatMap { list -> list.split(Regex(""",\s*""")) }
        val a = initial.toCharArray()
        var i = 0

        commands.forEach { command ->
            Regex("""s(\d+)""")
                .matchEntire(command)
                ?.let {
                    i = (i + a.size - it.groups[1]!!.value.toInt()) % a.size
                }
                ?: Regex("""x(\d+)/(\d+)""").matchEntire(command)?.let {
                    val x = it.groups[1]!!.value.toInt()
                    val y = it.groups[2]!!.value.toInt()
                    val temp = a[(i + x) % a.size]
                    a[(i + x) % a.size] = a[(i + y) % a.size]
                    a[(i + y) % a.size] = temp
                }
                ?: Regex("""p([a-z])/([a-z])""").matchEntire(command)?.let {
                    val x = it.groups[1]!!.value[0]
                    val y = it.groups[2]!!.value[0]
                    val q = a.indexOf(x)
                    val r = a.indexOf(y)
                    a[r] = x
                    a[q] = y
                }
        }

        return a.indices.map { a[(i + it) % a.size] }.joinToString("")
    }

    fun part2(initial: String, input: List<String>, iterations: Int): String {
        val commands = input
            .flatMap { list -> list.split(Regex(""",\s*""")) }
            .map { command ->
                Regex("""s(\d+)""")
                    .matchEntire(command)
                    ?.let {
                        Foo.S(it.groups[1]!!.value.toInt())
                    }
                    ?: Regex("""x(\d+)/(\d+)""").matchEntire(command)?.let {
                        Foo.X(it.groups[1]!!.value.toInt(), it.groups[2]!!.value.toInt())
                    }
                    ?: Regex("""p([a-z])/([a-z])""").matchEntire(command)?.let {
                        Foo.P(it.groups[1]!!.value[0], it.groups[2]!!.value[0])
                    }
                    ?: throw Exception()
            }

        val a = initial.toCharArray()
        val set = mutableMapOf<String, CharArray>()

        repeat(iterations) {
            set.getOrPut(a.joinToString("")) {
                commands.forEach { command ->
                    when (command) {
                        is Foo.S -> {
                            a.indices
                                .map { a[(a.size - command.i + it) % a.size] }
                                .forEachIndexed { index, c -> a[index] = c }
                        }
                        is Foo.X -> {
                            val temp = a[command.x % a.size]
                            a[command.x % a.size] = a[command.y % a.size]
                            a[command.y % a.size] = temp
                        }
                        is Foo.P -> {
                            val q = a.indexOf(command.y)
                            val r = a.indexOf(command.x)
                            a[q] = command.x
                            a[r] = command.y
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
