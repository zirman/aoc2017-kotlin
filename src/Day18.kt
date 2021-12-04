import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.collections.List

fun main() {
    fun part1(input: List<String>): Long {
        val registers = mutableMapOf<Char, Long>()
        var sounds = 0L

        fun lookup(value: String): Long {
            return value.toLongOrNull() ?: registers[value[0]] ?: 0L
        }

        fun set(register: String, value: Long) {
            registers[register[0]] = value
        }

        var i = 0

        while (i >= 0 && i < input.size) {
            Regex("""snd ([a-z\-\d]+)""").matchEntire(input[i])
                ?.let { matchResult ->
                    sounds = lookup(matchResult.groupValues[1])
                    i++
                }
                ?: Regex("""set (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                    set(matchResult.groupValues[1], lookup(matchResult.groupValues[2]))
                    i++
                }
                ?: Regex("""add (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                    set(
                        matchResult.groupValues[1],
                        lookup(matchResult.groupValues[1]) + lookup(matchResult.groupValues[2])
                    )
                    i++
                }
                ?: Regex("""mul (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                    set(
                        matchResult.groupValues[1],
                        lookup(matchResult.groupValues[1]) * lookup(matchResult.groupValues[2])
                    )
                    i++
                }
                ?: Regex("""mod (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                    set(
                        matchResult.groupValues[1],
                        lookup(matchResult.groupValues[1]).rem(lookup(matchResult.groupValues[2]))
                    )
                    i++
                }
                ?: Regex("""rcv ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                    if (lookup(matchResult.groupValues[1]) != 0L) {
                        return sounds
                    } else {
                        i++
                    }
                }
                ?: Regex("""jgz ([a-z\-\d]+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                    if (lookup(matchResult.groupValues[1]) > 0L) {
                        i += lookup(matchResult.groupValues[2]).toInt()
                    } else {
                        i++
                    }
                }
                ?: throw Exception(input[i])
        }

        throw Exception()
    }

    val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun part2(input: List<String>) {
        runBlocking {
            val channel0 = Channel<Long>(UNLIMITED)
            val channel1 = Channel<Long>(UNLIMITED)

            coroutineScope.launch {
                val registers = mutableMapOf<Char, Long>('p' to 0)

                fun lookup(value: String): Long {
                    return value.toLongOrNull() ?: registers[value[0]] ?: 0L
                }

                fun set(register: String, value: Long) {
                    registers[register[0]] = value
                }

                var i = 0

                while (i >= 0 && i < input.size) {
                    Regex("""snd ([a-z\-\d]+)""").matchEntire(input[i])
                        ?.let { matchResult ->
                            channel1.send(lookup(matchResult.groupValues[1]))
                            i++
                        }
                        ?: Regex("""set (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                            set(matchResult.groupValues[1], lookup(matchResult.groupValues[2]))
                            i++
                        }
                        ?: Regex("""add (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                            set(
                                matchResult.groupValues[1],
                                lookup(matchResult.groupValues[1]) + lookup(matchResult.groupValues[2])
                            )
                            i++
                        }
                        ?: Regex("""mul (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                            set(
                                matchResult.groupValues[1],
                                lookup(matchResult.groupValues[1]) * lookup(matchResult.groupValues[2])
                            )
                            i++
                        }
                        ?: Regex("""mod (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                            set(
                                matchResult.groupValues[1],
                                lookup(matchResult.groupValues[1]).rem(lookup(matchResult.groupValues[2]))
                            )
                            i++
                        }
                        ?: Regex("""rcv ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                            set(
                                matchResult.groupValues[1],
                                channel0.receive()
                            )
                            i++
                        }
                        ?: Regex("""jgz ([a-z\-\d]+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                            if (lookup(matchResult.groupValues[1]) > 0L) {
                                i += lookup(matchResult.groupValues[2]).toInt()
                            } else {
                                i++
                            }
                        }
                        ?: throw Exception(input[i])
                }

                throw Exception()
            }

            val registers = mutableMapOf<Char, Long>('p' to 1)

            fun lookup(value: String): Long {
                return value.toLongOrNull() ?: registers[value[0]] ?: 0L
            }

            fun set(register: String, value: Long) {
                registers[register[0]] = value
            }

            var i = 0
            var count = 0

            while (i >= 0 && i < input.size) {
                Regex("""snd ([a-z\-\d]+)""").matchEntire(input[i])
                    ?.let { matchResult ->
                        channel0.send(lookup(matchResult.groupValues[1]))
                        count++
                        println("$count")
                        i++
                    }
                    ?: Regex("""set (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                        set(matchResult.groupValues[1], lookup(matchResult.groupValues[2]))
                        i++
                    }
                    ?: Regex("""add (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                        set(
                            matchResult.groupValues[1],
                            lookup(matchResult.groupValues[1]) + lookup(matchResult.groupValues[2])
                        )
                        i++
                    }
                    ?: Regex("""mul (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                        set(
                            matchResult.groupValues[1],
                            lookup(matchResult.groupValues[1]) * lookup(matchResult.groupValues[2])
                        )
                        i++
                    }
                    ?: Regex("""mod (\w+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                        set(
                            matchResult.groupValues[1],
                            lookup(matchResult.groupValues[1]).rem(lookup(matchResult.groupValues[2]))
                        )
                        i++
                    }
                    ?: Regex("""rcv ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                        set(
                            matchResult.groupValues[1],
                            channel1.receive()
                        )
                        i++
                    }
                    ?: Regex("""jgz ([a-z\-\d]+) ([a-z\-\d]+)""").matchEntire(input[i])?.let { matchResult ->
                        if (lookup(matchResult.groupValues[1]) > 0L) {
                            i += lookup(matchResult.groupValues[2]).toInt()
                        } else {
                            i++
                        }
                    }
                    ?: throw Exception(input[i])
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 4L)
    //check(part2(readInput("Day18_test2")) == )

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
