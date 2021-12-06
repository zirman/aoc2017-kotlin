import kotlin.collections.List

sealed class State {
    object A : State()
    object B : State()
    object C : State()
    object D : State()
    object E : State()
    object F : State()
}

fun main() {
    fun part1(): Int {
        val tape = mutableSetOf<Int>()
        var state: State = State.A
        var p = 0

        repeat(12_302_209) {
            when (state) {
                State.A -> {
                    if (tape.contains(p).not()) {
                        tape.add(p)
                        p++
                        state = State.B
                    } else {
                        tape.remove(p)
                        p--
                        state = State.D
                    }
                }
                State.B -> {
                    if (tape.contains(p).not()) {
                        tape.add(p)
                        p++
                        state = State.C
                    } else {
                        tape.remove(p)
                        p++
                        state = State.F
                    }
                }
                State.C -> {
                    if (tape.contains(p).not()) {
                        tape.add(p)
                        p--
                        state = State.C
                    } else {
                        tape.add(p)
                        p--
                        state = State.A
                    }
                }
                State.D -> {
                    if (tape.contains(p).not()) {
                        tape.remove(p)
                        p--
                        state = State.E
                    } else {
                        tape.add(p)
                        p++
                        state = State.A
                    }
                }
                State.E -> {
                    if (tape.contains(p).not()) {
                        tape.add(p)
                        p--
                        state = State.A
                    } else {
                        tape.remove(p)
                        p++
                        state = State.B
                    }
                }
                State.F -> {
                    if (tape.contains(p).not()) {
                        tape.remove(p)
                        p++
                        state = State.C
                    } else {
                        tape.remove(p)
                        p++
                        state = State.E
                    }
                }
            }
            //println("a")
        }

        return tape.count()
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day25_test.txt")
//    check(part1(testInput) == 3)
//    check(part2(testInput) == TODO())
//
//    val input = readInput("Day25")
    println(part1())
//    println(part2(input))
}
