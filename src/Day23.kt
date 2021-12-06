import kotlin.collections.List

fun main() {
    fun part1(input: List<String>): Int {
        val inst = input.map { string ->
            (Regex("""(set) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(sub) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(mul) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(jnz) ([a-z\-\d]+) ([a-z\-\d]+)""").matchEntire(string)
                ?: throw Exception()).groupValues
        }

        val registers = mutableMapOf<Char, Long>()

        fun lookup(value: String): Long {
            return value.toLongOrNull() ?: registers[value[0]] ?: 0L
        }

        fun set(register: String, value: Long) {
            registers[register[0]] = value
        }

        var i = 0
        var count = 0

        while (i >= 0 && i < inst.size) {
            val groupValues = inst[i]

            when (groupValues[1]) {
                "set" -> {
                    set(groupValues[2], lookup(groupValues[3]))
                    i++
                }
                "sub" -> {
                    set(
                        groupValues[2],
                        lookup(groupValues[2]) - lookup(groupValues[3])
                    )
                    i++
                }
                "mul" -> {
                    set(
                        groupValues[2],
                        lookup(groupValues[2]) * lookup(groupValues[3])
                    )
                    i++
                    count++
                }
                "jnz" -> {
                    if (lookup(groupValues[2]) != 0L) {
                        i += lookup(groupValues[3]).toInt()
                    } else {
                        i++
                    }
                }
            }
        }

        return count
    }

    fun part2(): Long {
        val a = 1L
        var b = 0L
        var c = 0L
        var f = 0L
        var d = 0L
        var e = 0L
        var g = 0L
        var h = 0L

        //set b 79
        b = 79
        //set c b
        c = b
        //jnz a 2
        //jnz 1 5
        if (a != 0L) {
            //mul b 100
            b *= 100
            //sub b -100000
            b -= -100000
            //set c b
            c = b
            //sub c -17000
            c -= -17000
            //set f 1
            f = 1
        }
        while (true) {
            //set d 2
            d = 2
            do {
                //set e 2
                e = 2
                do {
                    //set g d
                    g = d
                    //mul g e
                    g *= e
                    //sub g b
                    g -= b
                    //jnz g 2
                    if (g == 0L) {
                        //set f 0
                        f = 0
                    }
                    //sub e -1
                    e -= -1
                    //set g e
                    g = e
                    //sub g b
                    g -= b
                    //jnz g -8
                } while (g != 0L)
                //sub d -1
                d -= -1
                //set g d
                g = d
                //sub g b
                g -= b
                //jnz g -13
            } while (g != 0L)
            //jnz f 2
            if (f == 0L) {
                //sub h -1
                h -= -1
            }
            //set g b
            g = b
            //sub g c
            g -= c
            //jnz g 2
            //jnz 1 3
            //sub b -17
            //jnz 1 -23
            if (g == 0L) {
                return h
            }
            b -= -17
            f = 1
        }
    }

    // test if implementation meets criteria from the description, like:
    val input = readInput("Day23")
    println(part1(input))
    println(part2())
}
