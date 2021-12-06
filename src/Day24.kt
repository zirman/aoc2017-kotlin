import kotlin.collections.List

fun main() {
    fun part1(input: List<String>): Int {
        val c = input.map { string ->
            val groupValues = Regex("""(\d+)/(\d+)""").matchEntire(string)!!.groupValues
            Pair(groupValues[1].toInt(), groupValues[2].toInt())
        }

        fun recur(components: List<Pair<Int, Int>>, pins: Int): Sequence<List<Pair<Int, Int>>> =
            sequenceOf(
                components.asSequence().flatMapIndexed { index, component ->
                    val (a, b) = component
                    if (a == pins) {
                        recur(components.toMutableList().also { it.removeAt(index) }, b).map {
                            listOf(
                                listOf(component),
                                it
                            ).flatten()
                        }
                    } else if (b == pins) {
                        recur(components.toMutableList().also { it.removeAt(index) }, a).map {
                            listOf(
                                listOf(component),
                                it
                            ).flatten()
                        }
                    } else {
                        sequenceOf()
                    }
                },
                sequenceOf(emptyList())
            ).flatten()

        return recur(c, 0).maxOf { it.sumOf { (a, b) -> a + b } }
    }

    fun part2(input: List<String>): Int {
        val c = input.map { string ->
            val groupValues = Regex("""(\d+)/(\d+)""").matchEntire(string)!!.groupValues
            Pair(groupValues[1].toInt(), groupValues[2].toInt())
        }

        fun recur(components: List<Pair<Int, Int>>, pins: Int): Sequence<List<Pair<Int, Int>>> =
            sequenceOf(
                components.asSequence().flatMapIndexed { index, component ->
                    val (a, b) = component
                    if (a == pins) {
                        recur(components.toMutableList().also { it.removeAt(index) }, b).map {
                            listOf(
                                listOf(component),
                                it
                            ).flatten()
                        }
                    } else if (b == pins) {
                        recur(components.toMutableList().also { it.removeAt(index) }, a).map {
                            listOf(
                                listOf(component),
                                it
                            ).flatten()
                        }
                    } else {
                        sequenceOf()
                    }
                },
                sequenceOf(emptyList())
            ).flatten()

        return recur(c, 0)
            .map { Pair(it.sumOf { (a, b) -> a + b }, it.size) }
            .fold(Pair(0, 0)) { acc, (strength, length) ->
                val (strengthAcc, lengthAcc) = acc
                if (length > lengthAcc ||
                    length == lengthAcc && strength > strengthAcc
                ) {
                    Pair(strength, length)
                } else {
                    acc
                }
            }
            .first
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day24_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 19)

    val input = readInput("Day24")
    println(part1(input))
    println(part2(input))
}
