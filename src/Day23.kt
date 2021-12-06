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

    fun part2(input: List<String>): Long {
        val inst = input.map { string ->
            Regex("""(set) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(sub) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(mul) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(jnz) ([a-z\-\d]+) ([a-z\-\d]+)""").matchEntire(string)
                ?: throw Exception()
        }

        val registers = mutableMapOf('a' to 1L)

        fun lookup(value: String): Long {
            return value.toLongOrNull() ?: registers[value[0]] ?: 0L
        }

        fun set(register: String, value: Long) {
            registers[register[0]] = value
        }

        var i = 0

        while (i >= 0 && i < inst.size) {
            val matchResult = inst[i]

            if (i == 9) {
                println("$i $registers")
            }

            when (matchResult.groupValues[1]) {
                "set" -> {
                    set(matchResult.groupValues[2], lookup(matchResult.groupValues[3]))
                    i++
                }
                "sub" -> {
                    set(
                        matchResult.groupValues[2],
                        lookup(matchResult.groupValues[2]) - lookup(matchResult.groupValues[3])
                    )
                    i++
                }
                "mul" -> {
                    set(
                        matchResult.groupValues[2],
                        lookup(matchResult.groupValues[2]) * lookup(matchResult.groupValues[3])
                    )
                    i++
                }
                "jnz" -> {
                    if (lookup(matchResult.groupValues[2]) != 0L) {
                        i += lookup(matchResult.groupValues[3]).toInt()
                    } else {
                        i++
                    }
                }
            }
        }

        return registers['h'] ?: 0
    }

    fun part3(): Int {
        val a = 1
        var b = 0
        var c = 0
        var f = 0
        var d = 0
        var e = 0
        var g = 0
        var h = 0

        //set b 79
        b = 79
        //set c b
        c = b
        //jnz a 2
        //jnz 1 5
        if (a != 0) {
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
//        var count = 0
        while (true) {
            println("i:9 1 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
            //set d 2
            d = 2
            do {
//                count++
//                println("i:10 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
//                if (count > 100) {
//                    throw Exception()
//                }
                //set e 2
                e = 2
                do {
                    //println("i:11 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
                    //set g d
                    g = d
                    //println("4 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
                    //mul g e
                    g *= e
                    //println("5 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
                    //sub g b
                    g -= b
                    //println("6 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
                    //jnz g 2
                    if (g == 0) {
                        //set f 0
                        f = 0
                        //println("7 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
                    }
                    //sub e -1
                    e -= -1
                    //println("8 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
                    //set g e
                    g = e
                    //println("9 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
                    //sub g b
                    g -= b
                    //println("10 a:$a b:$b c:$c f:$f d:$d e:$e g:$g h:$h")
                    //jnz g -8
                } while (g != 0)
                //sub d -1
                d -= -1
                //set g d
                g = d
                //sub g b
                g -= b
                //jnz g -13
            } while (g != 0)
            //jnz f 2
            if (f == 0) {
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
            if (g == 0) {
                return h
            }
            b -= -17
            f = 1
        }
    }

    fun part4(input: List<String>): Long {
        val inst = input.map { string ->
            (Regex("""(set) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(sub) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(mul) (\w+) ([a-z\-\d]+)""").matchEntire(string)
                ?: Regex("""(jnz) ([a-z\-\d]+) ([a-z\-\d]+)""").matchEntire(string)
                ?: throw Exception()).groupValues
        }

        var i = 0

        val registers = mutableMapOf<Char, Long>(
            'a' to 1,
//            'b' to 124900,
//            'c' to 124900,
//            'f' to 1,
//            'd' to 124883,
//            'e' to 124883,
//            'g' to -17,
//            'h' to 929,
        )

        fun lookup(value: String): Long {
            return value.toLongOrNull() ?: registers[value[0]] ?: 0L
        }

        fun set(register: String, value: Long) {
            registers[register[0]] = value
        }

        var count = 0L

        while (i >= 0 && i < inst.size) {
            if (count % 1_000_000L == 0L) {
                println("$i $registers")
            }
            count++

            if (i == 9 && count > 100L) {
                println("$i $registers")
                throw Exception()
            }

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

        return registers['h'] ?: 0
    }

    // test if implementation meets criteria from the description, like:

    val input = readInput("Day23")
    println(part1(input))
    //println(part2(input))
    println(part3())
    //println(part4(input))
}
