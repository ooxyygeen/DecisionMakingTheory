package lab5

fun bord(input: Map<Int, List<String>>) {
    val values = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)

    for (entry in input.entries.iterator()) {
        for (candidate in entry.value) {
            values[candidate] = values[candidate]!! + (entry.key * (entry.value.size - 1 - entry.value.indexOf(candidate)))
        }
    }

    println("Score: $values")
    println("Winner: ${values.maxBy { it.value }}")
}

fun copeland(input: Map<Int, List<String>>) {
    val values = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
    val iter = values.keys.toList()

    for (i in iter.indices) {
        for (j in i + 1 ..< iter.size) {
            var x = 0
            var y = 0
            for (entry in input.entries.iterator()) {
                if (entry.value.indexOf(iter[i]) < entry.value.indexOf(iter[j])) {
                    x += entry.key
                } else {
                    y += entry.key
                }
            }
            if (x >= y) {
                values[iter[i]] = values[iter[i]]!! + 1
                values[iter[j]] = values[iter[j]]!! - 1
            } else {
                values[iter[i]] = values[iter[i]]!! - 1
                values[iter[j]] = values[iter[j]]!! + 1
            }
        }
    }

    println("Score: $values")
    println("Winner: ${values.maxBy { it.value }}")
}

fun parallelExclusion(input: Map<Int, List<String>>) {
    val values = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
    val iter = values.keys.toList()
    val final: Pair<String, String>
    val finalPair = mutableListOf<String>()

    println("Semi-final")
    for (i in iter.indices step 2) {
        val j = i + 1
        var x = 0
        var y = 0

        for (entry in input.entries.iterator()) {
            if (entry.value.indexOf(iter[i]) < entry.value.indexOf(iter[j])) {
                x += entry.key
            } else {
                y += entry.key
            }
        }
        if (x >= y) {
            finalPair.add(iter[i])
        } else {
            finalPair.add(iter[j])
        }
        println("${iter[i]}=$x VS ${iter[j]}=$y")
    }

    final = Pair(finalPair[0], finalPair[1])

    var x = 0
    var y = 0
    println("Final")
    for (entry in input.entries.iterator()) {
        if (entry.value.indexOf(final.first) < entry.value.indexOf(final.second)) {
            x += entry.key
        } else {
            y += entry.key
        }
    }

    println("${final.first}=$x VS ${final.second}=$y")
    if (x >= y) {
        println("Winner: ${final.first}=$x")
    } else {
        println("Winner: ${final.second}=$y")
    }
}

fun main() {
    val input = mapOf(5 to listOf("a", "b", "c", "d"),
                      4 to listOf("c", "a", "b", "d"),
                      2 to listOf("b", "a", "c", "d"))

    println("Bord's method")
    bord(input)

    println("\nCopeland's method")
    copeland(input)

    println("\nParallel exclusion method")
    parallelExclusion(input)
}