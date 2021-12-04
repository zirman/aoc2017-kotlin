import kotlin.collections.List
import kotlin.math.absoluteValue

data class Particle(val p: Vector, val v: Vector, val a: Vector)
data class Vector(var x: Long, var y: Long, var z: Long)

fun Particle.distance(): Long {
    return p.x.absoluteValue + p.y.absoluteValue + p.z.absoluteValue
}

fun main() {
    fun part1(input: List<String>): Int {
        val particles =
            input.map { string ->
                val matchResult =
                    Regex("""p=<(-?\d+),(-?\d+),(-?\d+)>, v=<(-?\d+),(-?\d+),(-?\d+)>, a=<(-?\d+),(-?\d+),(-?\d+)>""")
                        .matchEntire(string)!!

                Particle(
                    Vector(
                        matchResult.groupValues[1].toLong(),
                        matchResult.groupValues[2].toLong(),
                        matchResult.groupValues[3].toLong()
                    ),
                    Vector(
                        matchResult.groupValues[4].toLong(),
                        matchResult.groupValues[5].toLong(),
                        matchResult.groupValues[6].toLong()
                    ),
                    Vector(
                        matchResult.groupValues[7].toLong(),
                        matchResult.groupValues[8].toLong(),
                        matchResult.groupValues[9].toLong()
                    )
                )
            }

        var i = particles.mapIndexed { index, particle -> Pair(index, particle.distance()) }
            .minByOrNull { it.second }!!.first

        var iters = 0

        while (iters < 1000) {
            particles.forEach { particle ->
                particle.v.x += particle.a.x
                particle.v.y += particle.a.y
                particle.v.z += particle.a.z

                particle.p.x += particle.v.x
                particle.p.y += particle.v.y
                particle.p.z += particle.v.z
            }

            val q = particles
                .mapIndexed { index, particle ->
                    //println(particle.distance())
                    Pair(index, particle.distance())
                }
                .minByOrNull { it.second }!!.first

            if (i != q) {
                //println(particles[q].distance())
                i = q
                iters = 0
            }
            iters++
        }

//        var i = 0
//        var v = Double.MAX_VALUE
//
//        particles
//            .map { sqrt(((it.a.x * it.a.x) + (it.a.y * it.a.y) + (it.a.z * it.a.z)).toDouble()) }
//            .also {
//                println(it.joinToString())
//            }
//            .forEachIndexed { index, v2 ->
//                if (v2 < v) {
//                    i = index
//                    v = v2
//                }
//            }

        return i
    }

    fun part2(input: List<String>): Int {
        val particles =
            input.map { string ->
                val matchResult =
                    Regex("""p=<(-?\d+),(-?\d+),(-?\d+)>, v=<(-?\d+),(-?\d+),(-?\d+)>, a=<(-?\d+),(-?\d+),(-?\d+)>""")
                        .matchEntire(string)!!

                Particle(
                    Vector(
                        matchResult.groupValues[1].toLong(),
                        matchResult.groupValues[2].toLong(),
                        matchResult.groupValues[3].toLong()
                    ),
                    Vector(
                        matchResult.groupValues[4].toLong(),
                        matchResult.groupValues[5].toLong(),
                        matchResult.groupValues[6].toLong()
                    ),
                    Vector(
                        matchResult.groupValues[7].toLong(),
                        matchResult.groupValues[8].toLong(),
                        matchResult.groupValues[9].toLong()
                    )
                )
            }.toMutableList()

        var iters = 0

        while (iters < 10_000) {
            var col = false

            var i = 0
            foo@ while (i < particles.size) {
                val p = particles[i].p

                if (particles.subList(i + 1, particles.size).any { it.p == p }) {
                    particles.removeAll { it.p == p }
                    col = true
                    continue
                } else {
                    i++
                }
            }

            if (col) {
                iters = 0
            }

            particles.forEach { particle ->
                particle.v.x += particle.a.x
                particle.v.y += particle.a.y
                particle.v.z += particle.a.z

                particle.p.x += particle.v.x
                particle.p.y += particle.v.y
                particle.p.z += particle.v.z
            }

            iters++
        }

        return particles.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    check(part1(testInput) == 0)
    println(part2(readInput("Day20_test2")))
    //check( == )

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}

